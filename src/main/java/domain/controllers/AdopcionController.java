package domain.controllers;

import domain.entities.Contacto;
import domain.entities.InformacionPersonal;
import domain.entities.Mascotas.CaracterisiticaDeMascotaRequerida;
import domain.entities.Mascotas.CaracteristicaDeMascota;
import domain.entities.Mascotas.Mascota;
import domain.entities.Mascotas.TipoMascota;
import domain.entities.Organizacion.Organizacion;
import domain.entities.Organizacion.PreguntasAdopcion;
import domain.entities.Organizacion.Respuesta;
import domain.entities.Persona;
import domain.entities.Usuario;
import domain.entities.publicaciones.EstadoPublicacion;
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
    private Repositorio<PublicacionMascotaEnAdopcion> repoPublicacionesAdopcion;
    private Repositorio<CaracterisiticaDeMascotaRequerida> repocaracteristicas;
    private Repositorio<InformacionPersonal> repoInfoPers;
    private Repositorio<CaracteristicaDeMascota> repocarac;
    private Repositorio<PublicacionIntencionDeAdopcion> repointencion;
    private Repositorio<Respuesta> repoComodidades;
    private Repositorio<Usuario> repoUsuarios;
    private Repositorio<Persona> repoPersonas;
    private Repositorio<Mascota>repoMascotas;


    public Response enviar(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Persona persona = new Persona();
        Organizacion organizacionAsociada = this.repoOrganizaciones.buscar(1);
        parametros.put("caracteristicas", organizacionAsociada.getCaracteristicasDeMascotasRequeridas());
        response.redirect("/home");
        return response;
    }


    public Response guardarIntencion(Request request, Response response) {
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

        if (parametros.get("persona") == null) {
            response.redirect("/login");
            return response;
        }


        Persona persona = repoPersonas.buscar(request.session().attribute("id"));
        Organizacion organizacionAsociada = persona.getOrganizacion();
        List<CaracterisiticaDeMascotaRequerida> caracteristicas = organizacionAsociada.getCaracteristicasDeMascotasRequeridas();
        List<CaracteristicaDeMascota> caracteristicaDeMascotaList =
                caracteristicas.stream().map(caracteristica -> {
                    String valor = request.queryParams(String.valueOf(caracteristica.getId()));
                    CaracteristicaDeMascota caracteristicaDeMascota = new CaracteristicaDeMascota();
                    caracteristicaDeMascota.setPreguntaALaQuePertenece(caracteristica);
                    caracteristicaDeMascota.setValor(valor);
                    return caracteristicaDeMascota;
                }).collect(Collectors.toList());


        List<PreguntasAdopcion> preguntas = organizacionAsociada.getPreguntasRequeridasAdopcion();
        List<Respuesta> comodidades =
                preguntas.stream().map(pregunta -> {
                    String valor = request.queryParams(String.valueOf(pregunta.getId()));
                    Respuesta respuestaComodidad = new Respuesta();
                    respuestaComodidad.setPreguntaALaQuePertenece(pregunta);
                    respuestaComodidad.setValor(valor);
                    return respuestaComodidad;
                }).collect(Collectors.toList());

        String linkBaja = "";
        PublicacionIntencionDeAdopcion publicacionIntencion = new PublicacionIntencionDeAdopcion(persona, comodidades, caracteristicaDeMascotaList, linkBaja);
        publicacionIntencion.setOrganizacion(organizacionAsociada);
        publicacionIntencion.setTitulo("Busco mascota");
        publicacionIntencion.setPersonaInteresada((Persona)parametros.get("persona"));
        String tipoMascota = request.queryParams("tipoMascota");
        if(tipoMascota.equals("PERRO")){
            publicacionIntencion.setTipoMascota(TipoMascota.PERRO);
        }else {
            publicacionIntencion.setTipoMascota(TipoMascota.GATO);
        }

        publicacionIntencion.setSexoMascota(request.queryParams("radiosSexo"));

        caracteristicaDeMascotaList.forEach(caracteristicaDeMascota -> {
            this.repocarac.agregar(caracteristicaDeMascota);
        });
        comodidades.forEach(comodidad -> {
            this.repoComodidades.agregar(comodidad);
        });

        this.repointencion.agregar(publicacionIntencion);

        linkBaja = "/dar-de-baja/" + String.valueOf(publicacionIntencion.getId());
        publicacionIntencion.setLinkBaja(linkBaja);
        this.repointencion.modificar(publicacionIntencion);
        publicacionIntencion.notificarLinkDeBaja();

        response.redirect("/listado-mascotas");
        return response;
    }

    public Response guardarAdopcion(Request request, Response response) {
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

        if (parametros.get("persona") == null) {
            response.redirect("/login");
            return response;
        }


        Persona persona = repoPersonas.buscar(request.session().attribute("id"));
        Mascota mascotaEnAdopcion = persona.getMascotas().stream().filter(mascota -> String.valueOf(mascota.getId()).equals(request.params("id"))).collect(Collectors.toList()).get(0);
        Organizacion organizacionAsociada = repoOrganizaciones.buscar(persona.getOrganizacion().getId());

        List<PreguntasAdopcion> preguntas = organizacionAsociada.getPreguntasRequeridasAdopcion();
        List<Respuesta> comodidades =
                preguntas.stream().map(pregunta -> {
                    String valor = request.queryParams(String.valueOf(pregunta.getId()));
                    Respuesta respuestaComodidad = new Respuesta();
                    respuestaComodidad.setPreguntaALaQuePertenece(pregunta);
                    respuestaComodidad.setValor(valor);
                    return respuestaComodidad;
                }).collect(Collectors.toList());


        PublicacionMascotaEnAdopcion publicacionAdopcion = new PublicacionMascotaEnAdopcion(mascotaEnAdopcion, comodidades);
        publicacionAdopcion.setMascotaEnAdopcion(mascotaEnAdopcion);
        publicacionAdopcion.setRespuestasPreguntas(comodidades);
        publicacionAdopcion.setOrganizacion(organizacionAsociada);
        publicacionAdopcion.setEstado(EstadoPublicacion.PENDIENTE);
        publicacionAdopcion.setTitulo(mascotaEnAdopcion.getApodo() + " esta buscando un hogar");


        comodidades.forEach(comodidad -> {
            this.repoComodidades.agregar(comodidad);
        });

        this.repoPublicacionesAdopcion.agregar(publicacionAdopcion);




        response.redirect("/listado-mascotas");
        return response;


    }

    public AdopcionController() {
        this.repositorio = FactoryRepositorio.get(Mascota.class);
        this.repositorioEnAdopcion = FactoryRepositorio.get(PreguntasAdopcion.class);
        this.repocaracteristicas = FactoryRepositorio.get(CaracterisiticaDeMascotaRequerida.class);
        this.repocarac = FactoryRepositorio.get(CaracteristicaDeMascota.class);
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
        this.repoInfoPers = FactoryRepositorio.get(InformacionPersonal.class);
        this.repointencion = FactoryRepositorio.get(PublicacionIntencionDeAdopcion.class);
        this.repoPublicacionesAdopcion = FactoryRepositorio.get(PublicacionMascotaEnAdopcion.class);
        this.repoComodidades = FactoryRepositorio.get(Respuesta.class);
        this.repoUsuarios = FactoryRepositorio.get(Usuario.class);
        this.repoPersonas = FactoryRepositorio.get(Persona.class);
        this.repoMascotas = FactoryRepositorio.get(Mascota.class);
    }

    public ModelAndView mostrarAdoptarDuenio(Request request, Response response) {
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
        if (parametros.get("persona") == null) {
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }
        Persona persona = (Persona) parametros.get("persona");

        Organizacion organizacionAsociada = persona.getOrganizacion();
        parametros.put("idMascota", request.params("id"));
        parametros.put("preguntas", organizacionAsociada.getPreguntasRequeridasAdopcion());
        return new ModelAndView(parametros, "AdopcionPorDuenio.hbs");
    }

    public ModelAndView mostrarAdoptar(Request request, Response response) {
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

        if (parametros.get("persona") == null) {
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }
        Persona persona = (Persona) parametros.get("persona");

        Organizacion organizacionAsociada = persona.getOrganizacion();
        parametros.put("preguntas", organizacionAsociada.getPreguntasRequeridasAdopcion());
        parametros.put("idMascota", request.params("id"));

        return new ModelAndView(parametros, "adopcion.hbs");
    }

    public ModelAndView mostrarIntencion(Request request, Response response) {
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

        if (parametros.get("persona") == null) {
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }
        Persona persona = (Persona) parametros.get("persona");


        Organizacion organizacionAsociada = persona.getOrganizacion();
        parametros.put("caracteristicas", organizacionAsociada.getCaracteristicasDeMascotasRequeridas());
        parametros.put("preguntas", organizacionAsociada.getPreguntasRequeridasAdopcion());
        return new ModelAndView(parametros, "intencion-adopcion.hbs");
    }

    public Object guardarAdopcionPorDuenio(Request request, Response response) {
        Mascota mascotaEnAdopcion = repoMascotas.buscar(new Integer(request.params("id")));
        Organizacion organizacionAsociada = repoOrganizaciones.buscar(mascotaEnAdopcion.getDuenio().getOrganizacion().getId());

        List<PreguntasAdopcion> preguntas = organizacionAsociada.getPreguntasRequeridasAdopcion();
        List<Respuesta> comodidades =
                preguntas.stream().map(pregunta -> {
                    String valor = request.queryParams(String.valueOf(pregunta.getId()));
                    Respuesta respuestaComodidad = new Respuesta();
                    respuestaComodidad.setPreguntaALaQuePertenece(pregunta);
                    respuestaComodidad.setValor(valor);
                    return respuestaComodidad;
                }).collect(Collectors.toList());

        PublicacionMascotaEnAdopcion publicacionAdopcion = new PublicacionMascotaEnAdopcion(mascotaEnAdopcion, comodidades);
        publicacionAdopcion.setMascotaEnAdopcion(mascotaEnAdopcion);
        publicacionAdopcion.setRespuestasPreguntas(comodidades);
        publicacionAdopcion.setOrganizacion(organizacionAsociada);
        publicacionAdopcion.notificarAdoptanteEncontrado(mascotaEnAdopcion.getDuenio());

        comodidades.forEach(comodidad -> {
            this.repoComodidades.agregar(comodidad);
        });

        this.repoPublicacionesAdopcion.agregar(publicacionAdopcion);

        response.redirect("/listado-mascotas");
        return response;
    }
}
