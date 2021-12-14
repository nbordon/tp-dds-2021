package domain.controllers;


import domain.entities.Mascotas.*;
import domain.entities.Organizacion.Organizacion;
import domain.entities.Persona;
import domain.entities.Usuario;
import domain.entities.UsuarioVoluntario;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class ControllersMascota {
    private Repositorio<Mascota> repositorioMascotas;
    private Repositorio<Persona> repoPersonas;
    private Repositorio<Usuario> repoUsuarios;
    private Repositorio<Organizacion> repoOrganizaciones;

    public ControllersMascota(){
        this.repositorioMascotas = FactoryRepositorio.get(Mascota.class);
        this.repoPersonas= FactoryRepositorio.get(Persona.class);
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
        this.repoUsuarios = FactoryRepositorio.get(Usuario.class);
    }

    public ModelAndView mostrarTodos(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        Usuario usuario = this.repoUsuarios.buscar(request.session().attribute("id"));
        parametros.put("usuario",usuario);
        LoginController.cargarPerfiles(parametros,request);
        List<Mascota> mascotas = this.repoPersonas.buscar(request.session().attribute("id")).getMascotas();
        parametros.put("mascotas", mascotas);
        return new ModelAndView(parametros,"listado-mascotas.hbs");
    }

    public ModelAndView mostrar(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        Mascota mascota = this.repositorioMascotas.buscar(new Integer(request.params("id")));
        LoginController.cargarUsuario(parametros,request);
        if(parametros.get("usuario")==null){
            response.redirect("/login");
        }
        Persona duenioMascota = this.obtenerPersona(request);
        Organizacion organizacionAsociada = duenioMascota.getOrganizacion();
        parametros.put("caracteristicas",organizacionAsociada.getCaracteristicasDeMascotasRequeridas());
        if(mascota != null){
            parametros.put("mascota",mascota);
            parametros.put("esMacho",mascota.getSexo().equals("Macho"));
            parametros.put("esHembra",mascota.getSexo().equals("Hembra"));
        }
        parametros.put("segundoNivel",true);
        return new ModelAndView(parametros, "detalle-mascota.hbs");

    }

    public ModelAndView registrar(Request request,Response response){

        Map<String, Object> parametros = new HashMap<>();
        Persona duenioMascota = this.obtenerPersona(request);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String fechaActual = dateFormat.format(date);
        parametros.put("fechaActual",fechaActual);
        Organizacion organizacionAsociada = duenioMascota.getOrganizacion();
        parametros.put("caracteristicas",organizacionAsociada.getCaracteristicasDeMascotasRequeridas());
        return new ModelAndView(parametros,"registrarMascota.hbs");
    }

    private Persona obtenerPersona(Request request){
        HashMap<String,Object> perfiles = new HashMap<>();
        LoginController.cargarPerfiles(perfiles,request);
        Persona duenioMascota;
        UsuarioVoluntario usuarioVoluntario;
        if(perfiles.get("usuarioVoluntario") != null){
            usuarioVoluntario = (UsuarioVoluntario) perfiles.get("usuarioVoluntario");
            duenioMascota = usuarioVoluntario.getPersonaVoluntaria();
        }else {
            duenioMascota = (Persona) perfiles.get("persona");
        }
        return duenioMascota;
    }

    private void asignarParametrosAMascota(Mascota mascota, Request request) {
        Persona duenioMascota = this.obtenerPersona(request);
        mascota.setDuenio(duenioMascota);
        Organizacion org = duenioMascota.getOrganizacion();
        mascota.setNombre(request.queryParams("nombreMascota"));
        mascota.setApodo(request.queryParams("apodoMascota"));
        mascota.setDescripcionFisica(request.queryParams("descripcionFisica"));
        mascota.setSexo(request.queryParams("radiosSexo"));
        if(request.queryParams("tipoMascota") != null){
            if(request.queryParams("tipoMascota").equals("PERRO")){
                mascota.setTipoMascota(TipoMascota.PERRO);
            }else{
                mascota.setTipoMascota(TipoMascota.GATO);
            }
        }
        LocalDate fechaNacimiento = LocalDate.parse(request.queryParams("fechaDeNacimiento"));
        mascota.setFechaNacimiento(fechaNacimiento);
        mascota.setEstado(EstadoMascota.NO_PERDIDA);
        List<CaracterisiticaDeMascotaRequerida > caracteristicasRequerida = org.getCaracteristicasDeMascotasRequeridas();
        List<CaracteristicaDeMascota> listaCaracteristicasMascotaNueva = new ArrayList<>();
        mascota.setCaracteristicas(listaCaracteristicasMascotaNueva);
        for(CaracterisiticaDeMascotaRequerida caracteristica:caracteristicasRequerida){
            CaracteristicaDeMascota caracteristicaDeMascota = new CaracteristicaDeMascota();
            String valor = request.queryParams(new Integer(caracteristica.getId()).toString());
            caracteristicaDeMascota.setValor(valor);
            caracteristicaDeMascota.setPreguntaALaQuePertenece(caracteristica);
            listaCaracteristicasMascotaNueva.add(caracteristicaDeMascota);
        }
        mascota.setCaracteristicas(listaCaracteristicasMascotaNueva);
    }

    public Response guardar(Request request, Response response){
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                new MultipartConfigElement("/tmp", 100000000, 100000000, 1024));
        Mascota mascota = new Mascota();
        asignarParametrosAMascota(mascota, request);
        this.repositorioMascotas.agregar(mascota);
        List<String> fotos = MascotaEncontradaController.guardarImagenes(request, mascota.getId(), "mascotas");
        mascota.setFotosUrl(fotos);
        repositorioMascotas.modificar(mascota);
        response.redirect("/listado-mascotas");
        return response;
    }

    public ModelAndView editar(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        try {
            LoginController.cargarPerfiles(parametros, request);
        } catch (Exception e) {
            System.out.println("no hay perfiles");
        }
        Mascota mascotaBuscada = this.repositorioMascotas.buscar(new Integer(request.params("id")));
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String fechaActual = dateFormat.format(date);
        if(mascotaBuscada.getSexo().equals("Macho")){
            parametros.put("esMacho",true);
            parametros.put("esHembra",false);
        }else {
            parametros.put("esMacho",false);
            parametros.put("esHembra",true);
        }
        parametros.put("segundoNivel",true);
        parametros.put("fechaActual",fechaActual);
        parametros.put("mascota",mascotaBuscada);
        Persona duenioMascota = this.obtenerPersona(request);
        Organizacion organizacionAsociada = duenioMascota.getOrganizacion();
        parametros.put("caracteristicas",organizacionAsociada.getCaracteristicasDeMascotasRequeridas());
        return new ModelAndView(parametros, "registrarMascota.hbs");
    }

    public Response editarMascota(Request request, Response response){
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                new MultipartConfigElement("/tmp", 100000000, 100000000, 1024));
        Mascota mascotaBuscada = this.repositorioMascotas.buscar(new Integer(request.params("id")));
        asignarParametrosAMascota(mascotaBuscada,request);
        this.repositorioMascotas.modificar(mascotaBuscada);
        response.redirect("/listado-mascotas");
        return  response;
    }

    public Response eliminar(Request request, Response response){
        Mascota mascota = this.repositorioMascotas.buscar(new Integer(request.params("id")));
        this.repositorioMascotas.eliminar(mascota);
        return response;
    }
}