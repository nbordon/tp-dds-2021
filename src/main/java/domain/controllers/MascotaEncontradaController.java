package domain.controllers;

import Api.services.entities.Hogar;
import Api.services.entities.Ubicacion;

import domain.entities.Contacto;
import domain.entities.EstrategiasNotificacion.EstrategiaDeNotificacion;
import domain.entities.EstrategiasNotificacion.EstrategiaEmail.EstrategiaEmail;
import domain.entities.EstrategiasNotificacion.EstrategiaSms.EstrategiaDeSms;
import domain.entities.EstrategiasNotificacion.EstrategiaWhatsApp.EstrategiaDeWhatsApp;
import domain.entities.InformacionPersonal;
import domain.entities.Mascotas.*;

import domain.entities.Organizacion.Organizacion;
import domain.entities.Rescatista;
import domain.entities.TipoDeDocumento;
import domain.entities.publicaciones.EstadoPublicacion;

import domain.entities.publicaciones.PublicacionMascotaEncontradaSinChapita;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.localizador.LocalizadorDeOrganizacion;


import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MascotaEncontradaController {
    private Repositorio<Rescatista> repoRescatista;
    private Repositorio<InformacionPersonal> repoInfoPers;
    private Repositorio<MascotaEncontradaSinChapita> repoMascotaSinChapita;
    private Repositorio<Mascota> repoMascota;
    private Repositorio<Ubicacion> repoUbicacion;
    private Repositorio<MascotaEncontradaConChapita> repoMascotEncontConChapita;
    private Repositorio<Contacto> repoContactos;
    private Repositorio<MascotaEncontrada> repoMascotaEncontrada;
    private Repositorio<PublicacionMascotaEncontradaSinChapita> repoPublicacionSinChapita;
    private Repositorio<Hogar> repoHogares;
    private Repositorio<Foto> repoFotos;

    public MascotaEncontradaController(){
        this.repoRescatista = FactoryRepositorio.get(Rescatista.class);
        this.repoInfoPers = FactoryRepositorio.get(InformacionPersonal.class);
        this.repoMascotaSinChapita = FactoryRepositorio.get(MascotaEncontradaSinChapita.class);
        this.repoMascota = FactoryRepositorio.get(Mascota.class);
        this.repoUbicacion = FactoryRepositorio.get(Ubicacion.class);
        this.repoMascotEncontConChapita = FactoryRepositorio.get(MascotaEncontradaConChapita.class);
        this.repoContactos = FactoryRepositorio.get(Contacto.class);
        this.repoMascotaEncontrada = FactoryRepositorio.get(MascotaEncontrada.class);
        this.repoPublicacionSinChapita = FactoryRepositorio.get(PublicacionMascotaEncontradaSinChapita.class);
        this.repoHogares = FactoryRepositorio.get(Hogar.class);
        this.repoFotos = FactoryRepositorio.get(Foto.class);
    }

    public ModelAndView ubicacion(Request request, Response response) {
        return new ModelAndView(null, "MascotaEncontrada.hbs");
    }

    public ModelAndView infoRescatista(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Rescatista rescatista = new Rescatista();
        InformacionPersonal infoPersonal = new InformacionPersonal();
        infoPersonal.setApellido(request.queryParams("apellido"));
        infoPersonal.setEmail(request.queryParams("email"));
        infoPersonal.setNombre(request.queryParams("nombre"));
        infoPersonal.setFechaNacimiento(LocalDate.parse(request.queryParams("fechaNac")));
        infoPersonal.setNroDocumento(new Integer(request.queryParams("nroDoc")));

        String te = request.queryParams("tipoDoc");

        switch(request.queryParams("tipoDoc")){
            case "1":
                infoPersonal.setTipoDoc(TipoDeDocumento.DNI);
                break;
            case "2":
                infoPersonal.setTipoDoc(TipoDeDocumento.PASAPORTE);
                break;
            case "3":
                infoPersonal.setTipoDoc(TipoDeDocumento.LC);
                break;
            default:
                infoPersonal.setTipoDoc(TipoDeDocumento.DNI);
                break;

        }
        String mensajeDuenioComunicacion = "";
        List<EstrategiaDeNotificacion> estrategias = new ArrayList<EstrategiaDeNotificacion>();
        if(request.queryParams("checkSms") != null){
            EstrategiaDeNotificacion sms = new EstrategiaDeSms();
            estrategias.add(sms);
            mensajeDuenioComunicacion = mensajeDuenioComunicacion + " Por SMS.";

        }

        if(request.queryParams("checkWsp")!= null){
            EstrategiaDeNotificacion wsp = new EstrategiaDeWhatsApp();
            estrategias.add(wsp);
            mensajeDuenioComunicacion = mensajeDuenioComunicacion + " Por WhatsApp.";

        }

        if(request.queryParams("checkEmail")!= null){
            EstrategiaDeNotificacion email = new EstrategiaEmail();
            estrategias.add(email);
            mensajeDuenioComunicacion = mensajeDuenioComunicacion + " Por EMAIL.";


        }

        Contacto contacto = new Contacto();
        contacto.setEsPrincipal(true);
        contacto.setEmail(infoPersonal.getEmail());
        contacto.setNumeroDeTelefono(request.queryParams("telefono"));
        contacto.setNombre(infoPersonal.getNombre());
        contacto.setApellido(infoPersonal.getApellido());
        List<Contacto> contactos = new ArrayList<Contacto>();
        contactos.add(contacto);
        infoPersonal.setContactos(contactos);
        infoPersonal.setFormaComunicacion(estrategias);

        rescatista.setInformacionPersonal(infoPersonal);

        MascotaEncontrada mascota = repoMascotaEncontrada.buscar(new Integer(request.params("idMascotaEncontrada")));
        rescatista.setMascotaEncontrada(mascota);

        repoContactos.agregar(contacto);
        repoInfoPers.agregar(infoPersonal);
        rescatista.setInformacionPersonal(infoPersonal);
        repoRescatista.agregar(rescatista);
        mascota.setRescatista(rescatista);
        repoMascotaEncontrada.modificar(mascota);

        if(rescatista.getMascotaEncontrada().getMascota() == null){
            //Mascota sin chapita. Generar publicacion
            System.out.println("GENERAR PUBLICACION DE MASCOTA");
            PublicacionMascotaEncontradaSinChapita publicacion = new PublicacionMascotaEncontradaSinChapita();
            publicacion.setEstado(EstadoPublicacion.PENDIENTE);
            MascotaEncontradaSinChapita mascotaEncontradaSinChapita = repoMascotaSinChapita.buscar(rescatista.getMascotaEncontrada().getId());
            publicacion.setMascotaEncontradaSinChapita(mascotaEncontradaSinChapita);
            publicacion.setTitulo(mascotaEncontradaSinChapita.getTipoMascota()+ " " + "Encontrado");
            repoPublicacionSinChapita.agregar(publicacion);

            LocalizadorDeOrganizacion localizadorDeOrganizacion = new LocalizadorDeOrganizacion();
            Organizacion org = localizadorDeOrganizacion.obtenerOrganizacionMasCercana(mascotaEncontradaSinChapita.getUbicacion());

            publicacion.setOrganizacion(org);
            repoPublicacionSinChapita.agregar(publicacion);

            parametros.put("publicacion",publicacion);
        } else {
            mascota.notificar(mensajeDuenioComunicacion);
            Mascota masc = (Mascota) mascota.getMascota();
            parametros.put("telefonoDuenio", masc.getDuenio().getInformacionPersonal().getContactoDuenio().getNumeroDeTelefono());
        }

        parametros.put("hogar", mascota.getHogarDeTransito());
        parametros.put("mascotaEncontrada", mascota.getMascota());



        return new ModelAndView(parametros, "mascotaEncontradaExito.hbs");
    }

    public ModelAndView mascotaEncontrada(Request request, Response response) {

        if(request.params("idMascota") != null){
            Integer idMascota = new Integer(request.params("idMascota"));
            //mascota con chapita
            Mascota mascota = repoMascota.buscar(new Integer(request.params("idMascota")));
            if(mascota == null) {
                String mensaje = "OOOOopss mascota no encontrada!";
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("mensaje", mensaje);
                return new ModelAndView(parametros, "notFoundPage.hbs");
            }
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("mascota", mascota);
            return new ModelAndView(parametros, "MascotaEncontrada.hbs");
        }
        return new ModelAndView(null, "MascotaEncontrada.hbs");
    }

    public ModelAndView infoMascotaEncontradaConChapita(Request request, Response response) {
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                new MultipartConfigElement("/tmp", 100000000, 100000000, 1024));

        MascotaEncontradaConChapita mascotaEncontrada = new MascotaEncontradaConChapita();
        Mascota mascota = repoMascota.buscar(new Integer(request.params("idMascota")));
        mascotaEncontrada.setMascota(mascota);
        mascotaEncontrada.setDescripcionEstadoEncotrado(request.queryParams("descEncontrada"));
        Ubicacion ubicacion = guardarUbicacion(request);
        mascotaEncontrada.setUbicacion(ubicacion);
        if(request.queryParams("solicitaTransito") != null){
            System.out.println("Esta solicitando transito");
            List<Hogar> listaHogares = repoHogares.buscarTodos();
            mascotaEncontrada.setHogarDeTransito(listaHogares.get(0));
        }
        repoUbicacion.agregar(ubicacion);
        repoMascotEncontConChapita.agregar(mascotaEncontrada);
        List<String> fotos = guardarImagenes(request, mascotaEncontrada.getId(), "mascotasEncontradasConChapita");
        mascotaEncontrada.setFotos(fotos);
        repoMascotEncontConChapita.modificar(mascotaEncontrada);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("mascotaEncontrada", mascotaEncontrada);
        return new ModelAndView(parametros, "infoPersonal.hbs");
    }

    public ModelAndView infoMascotaEncontradaSinChapita(Request request, Response response) throws ServletException, IOException {
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                new MultipartConfigElement("/tmp", 100000000, 100000000, 1024));

        MascotaEncontradaSinChapita mascotaEncontrada = new MascotaEncontradaSinChapita();
        mascotaEncontrada.setDescripcionEstadoEncotrado(request.queryParams("descEncontrada"));
        if(request.queryParams("tipoMascota").equals("PERRO")){
            mascotaEncontrada.setTipoMascota(TipoMascota.PERRO);
        }else{
            mascotaEncontrada.setTipoMascota(TipoMascota.GATO);
        }
        mascotaEncontrada.setSexo(request.queryParams("sexoMascota"));
        Ubicacion ubicacion = guardarUbicacion(request);
        mascotaEncontrada.setUbicacion(ubicacion);
        if(request.queryParams("solicitaTransito") != null){
            System.out.println("Esta solicitando transito");
            List<Hogar> listaHogares = repoHogares.buscarTodos();
            mascotaEncontrada.setHogarDeTransito(listaHogares.get(0));
        }

        repoUbicacion.agregar(ubicacion);

        List<String> fotos = guardarImagenes(request, mascotaEncontrada.getId(), "mascotasEncontradasSinChapita");
        mascotaEncontrada.setFotos(fotos);
        repoMascotaSinChapita.agregar(mascotaEncontrada);
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("mascotaEncontrada", mascotaEncontrada);
        parametros.put("segundoNivel",true);

        return new ModelAndView(parametros, "infoPersonal.hbs");
    }


    public static List<String> guardarImagenes(Request request, Integer id, String folder){

        List<String> listaFotos = new ArrayList<>();
        AtomicReference<Integer> counter = new AtomicReference<>(0);
        try {
            request.raw().getParts().forEach(part -> {
                if(part.getName().equals("formFileMultiple")){
                    String filename = part.getSubmittedFileName();
                    Part uploadedFile = part;
                    try (final InputStream in = uploadedFile.getInputStream()) {
                        String fileName =folder+"/"+ id.toString()+"_"+ counter.toString();
                        Files.copy(in, Paths.get(System.getProperty("user.dir")+"/src/main/resources/public/fotos/"+fileName));
                        uploadedFile.delete();
                        counter.getAndSet(counter.get() + 1);
                        listaFotos.add(fileName);
                    } catch(Exception e){
                        throw new RuntimeException(e);
                    }

                }
            });

        } catch (Exception e) {

        }
        return listaFotos;
    }

    private Ubicacion guardarUbicacion(Request request) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion(request.queryParams("direccion"));
        ubicacion.setLatitud(Double.valueOf(request.queryParams("latitud")));
        ubicacion.setLongitud(Double.valueOf(request.queryParams("longitud")));

        return ubicacion;
    }

    public ModelAndView notFound(Request request, Response response) {
        String mensaje = "OOOOopss pagina no encontrada!";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("mensaje", mensaje);
        return new ModelAndView(null, "notFoundPage.hbs");
    }
}

