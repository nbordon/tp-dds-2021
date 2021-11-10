package domain.controllers;

import domain.entities.Contacto;
import domain.entities.InformacionPersonal;
import domain.entities.Mascotas.CaracterisiticaDeMascotaRequerida;
import domain.entities.Mascotas.CaracteristicaDeMascota;
import domain.entities.Mascotas.Mascota;
import domain.entities.Organizacion.Organizacion;
import domain.entities.Organizacion.PreguntasAdopcion;
import domain.entities.Organizacion.Respuesta;
import domain.entities.Persona;
import domain.entities.Usuario;
import domain.entities.publicaciones.PublicacionIntencionDeAdopcion;
import domain.entities.publicaciones.PublicacionMascotaEnAdopcion;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdopcionController {
    private Repositorio<Mascota> repositorio;
    private Repositorio<Organizacion> repoOrganizaciones;
    private Repositorio<PreguntasAdopcion> repositorioEnAdopcion;
    private Repositorio<CaracterisiticaDeMascotaRequerida>repocaracteristicas;
    private Repositorio<InformacionPersonal> repoInfoPers;
    private Repositorio<CaracteristicaDeMascota>repocarac;
    private Repositorio<PublicacionIntencionDeAdopcion>repointencion;
    private Repositorio<Respuesta>repoComodidades;
    private Repositorio<Usuario>repoUsuarios;
    private Repositorio<Persona>repoPersonas;




    public Response enviar(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        Persona persona=new Persona();
        Organizacion organizacionAsociada = this.repoOrganizaciones.buscar(1);
        parametros.put("caracteristicas",organizacionAsociada.getCaracteristicasDeMascotasRequeridas());
        response.redirect("/home");
        return response;
    }


    public Response guardarPrueba(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        try {
            LoginController.cargarPerfiles(parametros, request);
            Usuario usuario = repoUsuarios.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parametros.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return response;
        }

        if(parametros.get("persona") == null) {
            response.redirect("/login");
            return response;
        }


        Persona persona = repoPersonas.buscar(request.session().attribute("id"));
        Organizacion organizacionAsociada = persona.getOrganizacion();
        List<CaracterisiticaDeMascotaRequerida> caracteristicas=organizacionAsociada.getCaracteristicasDeMascotasRequeridas();
        List<CaracteristicaDeMascota>caracteristicaDeMascotaList=
        caracteristicas.stream().map(caracteristica -> {
            String valor=request.queryParams(String.valueOf(caracteristica.getId()));
            CaracteristicaDeMascota caracteristicaDeMascota=new CaracteristicaDeMascota();
            caracteristicaDeMascota.setPreguntaALaQuePertenece(caracteristica);
            caracteristicaDeMascota.setValor(valor);
            return caracteristicaDeMascota;
        }).collect(Collectors.toList());


        List<PreguntasAdopcion>preguntas=organizacionAsociada.getPreguntasRequeridasAdopcion();
        List<Respuesta>comodidades=
                preguntas.stream().map(pregunta -> {
                    String valor=request.queryParams(String.valueOf(pregunta.getId()));
                    Respuesta respuestaComodidad=new Respuesta();
                    respuestaComodidad.setPreguntaALaQuePertenece(pregunta);
                    respuestaComodidad.setValor(valor);
                    return respuestaComodidad;
                }).collect(Collectors.toList());

        String linkBaja="";
        PublicacionIntencionDeAdopcion publicacionIntencion=new PublicacionIntencionDeAdopcion(persona,comodidades,caracteristicaDeMascotaList,linkBaja);
       /* Organizacion organizacion=this.repoOrganizaciones.buscar(persona.getOrganizacion().getId());
        persona.setOrganizacion(organizacion);*/
        caracteristicaDeMascotaList.forEach(caracteristicaDeMascota -> {this.repocarac.agregar(caracteristicaDeMascota);});
        comodidades.forEach(comodidad->{this.repoComodidades.agregar(comodidad);});

        this.repointencion.agregar(publicacionIntencion);

        linkBaja="/dar-de-baja/"+String.valueOf(publicacionIntencion.getId());
        publicacionIntencion.setLinkBaja(linkBaja);
        this.repointencion.modificar(publicacionIntencion);
       // publicacionIntencion.notificarLinkDeBaja();
       /* this.repoOrganizaciones.modificar(persona.getOrganizacion());*/

        response.redirect("/listado-mascotas");
        return response;
    }
    public Response guardarAdopcion(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        try {
            LoginController.cargarPerfiles(parametros, request);
            Usuario usuario = repoUsuarios.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parametros.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return response;
        }

        if(parametros.get("persona") == null) {
            response.redirect("/login");
            return response;
        }


        Persona persona = repoPersonas.buscar(request.session().attribute("id"));
        Organizacion organizacionAsociada = persona.getOrganizacion();

        List<PreguntasAdopcion>preguntas=organizacionAsociada.getPreguntasRequeridasAdopcion();
        List<Respuesta>comodidades=
                preguntas.stream().map(pregunta -> {
                    String valor=request.queryParams(String.valueOf(pregunta.getId()));
                    Respuesta respuestaComodidad=new Respuesta();
                    respuestaComodidad.setPreguntaALaQuePertenece(pregunta);
                    respuestaComodidad.setValor(valor);
                    return respuestaComodidad;
                }).collect(Collectors.toList());



        comodidades.forEach(comodidad->{this.repoComodidades.agregar(comodidad);});

        response.redirect("/listado-mascotas");
        return response;
    }
    public Response guardarAdopcionPorDuenio(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();

        try {
            LoginController.cargarPerfiles(parametros, request);
            Usuario usuario = repoUsuarios.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parametros.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return response;
        }

        if(parametros.get("persona") == null) {
            response.redirect("/login");
            return response;
        }

        PublicacionMascotaEnAdopcion pubHard=new PublicacionMascotaEnAdopcion();
        Contacto contacto= new Contacto();
        pubHard.notificarAdoptanteEncontrado(contacto);
        Persona persona = repoPersonas.buscar(request.session().attribute("id"));
        Organizacion organizacionAsociada = persona.getOrganizacion();

        List<PreguntasAdopcion>preguntas=organizacionAsociada.getPreguntasRequeridasAdopcion();
        List<Respuesta>comodidades=
                preguntas.stream().map(pregunta -> {
                    String valor=request.queryParams(String.valueOf(pregunta.getId()));
                    Respuesta respuestaComodidad=new Respuesta();
                    respuestaComodidad.setPreguntaALaQuePertenece(pregunta);
                    respuestaComodidad.setValor(valor);
                    return respuestaComodidad;
                }).collect(Collectors.toList());



        comodidades.forEach(comodidad->{this.repoComodidades.agregar(comodidad);});



        response.redirect("/listado-mascotas");
        return response;
    }
    public AdopcionController(){
        this.repositorio = FactoryRepositorio.get(Mascota.class);
        this.repositorioEnAdopcion = FactoryRepositorio.get(PreguntasAdopcion.class);
        this.repocaracteristicas= FactoryRepositorio.get(CaracterisiticaDeMascotaRequerida.class);
        this.repocarac= FactoryRepositorio.get(CaracteristicaDeMascota.class);
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
        this.repoInfoPers = FactoryRepositorio.get(InformacionPersonal.class);
        this.repointencion=FactoryRepositorio.get(PublicacionIntencionDeAdopcion.class);
        this.repoComodidades=FactoryRepositorio.get(Respuesta.class);
        this.repoUsuarios=FactoryRepositorio.get(Usuario.class);
        this.repoPersonas=FactoryRepositorio.get(Persona.class);
    }


    public ModelAndView mostrarAdoptarDuenio(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();

        try {
            LoginController.cargarPerfiles(parametros, request);
            Usuario usuario = repoUsuarios.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parametros.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }
        if(parametros.get("persona") == null) {
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }
        Persona persona = (Persona) parametros.get("persona");

        Organizacion organizacionAsociada = persona.getOrganizacion();
        parametros.put("preguntas",organizacionAsociada.getPreguntasRequeridasAdopcion());
        return new ModelAndView(parametros, "AdopcionPorDuenio.hbs");
    }

    public ModelAndView mostrarAdoptar(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();

        try {
            LoginController.cargarPerfiles(parametros, request);
            Usuario usuario = repoUsuarios.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parametros.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        if(parametros.get("persona") == null) {
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }
        Persona persona = (Persona) parametros.get("persona");

        Organizacion organizacionAsociada = persona.getOrganizacion();
        parametros.put("preguntas",organizacionAsociada.getPreguntasRequeridasAdopcion());
        return new ModelAndView(parametros, "Adopcion.hbs");
    }

    public ModelAndView mostrarPrueba(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();

        try {
            LoginController.cargarPerfiles(parametros, request);
            Usuario usuario = repoUsuarios.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parametros.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        if(parametros.get("persona") == null) {
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }
        Persona persona = (Persona) parametros.get("persona");


        Organizacion organizacionAsociada = persona.getOrganizacion();
        parametros.put("caracteristicas",organizacionAsociada.getCaracteristicasDeMascotasRequeridas());
        parametros.put("preguntas",organizacionAsociada.getPreguntasRequeridasAdopcion());
        return new ModelAndView(parametros, "prueba.hbs");
    }
}
