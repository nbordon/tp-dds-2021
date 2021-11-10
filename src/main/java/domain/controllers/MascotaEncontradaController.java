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
import javassist.expr.Cast;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.localizador.LocalizadorDeOrganizacion;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        repoRescatista.agregar(rescatista);
        mascota.setRescatista(rescatista);
        repoMascotaEncontrada.modificar(mascota);

        //
        if(rescatista.getMascotaEncontrada().getMascota() == null){
            //Mascota sin chapita. Generar publicacion
            System.out.println("GENERAR PUBLICACION DE MASCOTA");
            PublicacionMascotaEncontradaSinChapita publicacion = new PublicacionMascotaEncontradaSinChapita();
            publicacion.setEstado(EstadoPublicacion.PENDIENTE);
            MascotaEncontradaSinChapita mascotaEncontradaSinChapita = repoMascotaSinChapita.buscar(rescatista.getMascotaEncontrada().getId());
            publicacion.setMascotaEncontradaSinChapita(mascotaEncontradaSinChapita);
            publicacion.setTitulo(mascotaEncontradaSinChapita.getTipoMascota()+ " " + "Encontrado");
            repoPublicacionSinChapita.agregar(publicacion);
            //publicacion.setOrganizacion();

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
        MascotaEncontradaConChapita mascotaEncontrada = new MascotaEncontradaConChapita();
        Mascota mascota = repoMascota.buscar(new Integer(request.params("idMascota")));
        mascotaEncontrada.setMascota(mascota);
        mascotaEncontrada.setDescripcionEstadoEncotrado(request.queryParams("descEncontrada"));
        //TODO: agregar fecha en el form?
        //mascotaEncontrada.setFechaEnLaQueSeEncontro();
        Ubicacion ubicacion = guardarUbicacion(request);
        mascotaEncontrada.setUbicacion(ubicacion);
        if(request.queryParams("solicitaTransito") != null){
            System.out.println("Esta solicitando transito");
            //Buscar hogar de transito segun ubicacion.
            //TODO: buscar hogar de transito por proximidad
            List<Hogar> listaHogares = repoHogares.buscarTodos();
            mascotaEncontrada.setHogarDeTransito(listaHogares.get(0));
        }
        repoUbicacion.agregar(ubicacion);
        repoMascotEncontConChapita.agregar(mascotaEncontrada);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("mascotaEncontrada", mascotaEncontrada);
        return new ModelAndView(parametros, "infoPersonal.hbs");
    }

    public ModelAndView infoMascotaEncontradaSinChapita(Request request, Response response) {
        MascotaEncontradaSinChapita mascotaEncontrada = new MascotaEncontradaSinChapita();
        mascotaEncontrada.setDescripcionEstadoEncotrado(request.queryParams("descEncontrada"));
        if(request.queryParams("tipoMascota").equals("PERRO")){
            mascotaEncontrada.setTipoMascota(TipoMascota.PERRO);
        }else{
            mascotaEncontrada.setTipoMascota(TipoMascota.GATO);
        }
        mascotaEncontrada.setSexo(request.queryParams("sexoMascota"));
        //TODO: agregar fecha en el form?
        //mascotaEncontrada.setFechaEnLaQueSeEncontro();
        Ubicacion ubicacion = guardarUbicacion(request);
        mascotaEncontrada.setUbicacion(ubicacion);
        //TODO: donde o como guardamos si tiene hogar de transito
        if(request.queryParams("solicitaTransito") != null){
            System.out.println("Esta solicitando transito");
            //Buscar hogar de transito segun ubicacion.
            List<Hogar> listaHogares = repoHogares.buscarTodos();
            mascotaEncontrada.setHogarDeTransito(listaHogares.get(0));
        }

        repoUbicacion.agregar(ubicacion);
        repoMascotaSinChapita.agregar(mascotaEncontrada);
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("mascotaEncontrada", mascotaEncontrada);

        return new ModelAndView(parametros, "infoPersonal.hbs");
    }

    private Ubicacion guardarUbicacion(Request request) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion(request.queryParams("direccion"));
        ubicacion.setLatitud(new Double(request.queryParams("latitud")));
        ubicacion.setLongitud(new Double(request.queryParams("longitud")));

        return ubicacion;
    }

    public ModelAndView notFound(Request request, Response response) {
        String mensaje = "OOOOopss pagina no encontrada!";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("mensaje", mensaje);
        return new ModelAndView(null, "notFoundPage.hbs");
    }
}

