package domain.controllers;

import domain.entities.Mascotas.CaracterisiticaDeMascotaRequerida;
import domain.entities.Organizacion.Organizacion;
import domain.entities.Organizacion.PreguntasAdopcion;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import domain.entities.Organizacion.Administrador;
import domain.entities.Usuario;

public class PreguntasAdopcionController {

    private Repositorio<Organizacion> organizacionRepositorio;
    private Repositorio<Usuario> usuarioRepositorio;
    private Repositorio<Administrador> administradorRepositorio;
    private Repositorio<CaracterisiticaDeMascotaRequerida> caracteristicaDeMascotaRequeridaRepositorio;
    private Repositorio<PreguntasAdopcion> preguntasAdopcionRepositorio;

    public PreguntasAdopcionController() {
        this.organizacionRepositorio = FactoryRepositorio.get(Organizacion.class);
        this.usuarioRepositorio = FactoryRepositorio.get(Usuario.class);
        this.administradorRepositorio = FactoryRepositorio.get(Administrador.class);
        this.caracteristicaDeMascotaRequeridaRepositorio = FactoryRepositorio.get(CaracterisiticaDeMascotaRequerida.class);
        this.preguntasAdopcionRepositorio = FactoryRepositorio.get(PreguntasAdopcion.class);
    }

    public ModelAndView mostrarTodo(Request request, Response response) {
        HashMap<String, Object> parameters = new HashMap<>();

        try {
            LoginController.cargarPerfiles(parameters, request);
            Usuario usuario = usuarioRepositorio.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parameters.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        if (parameters.get("administrador") == null) {
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        Administrador admin = (Administrador) parameters.get("administrador");
        Organizacion organizacion = organizacionRepositorio.buscar(admin.getOrganizacionPerteneciente().getId());
        List<PreguntasAdopcion> preguntas = new ArrayList<>();
        organizacion.getPreguntasRequeridasAdopcion().forEach(pregunta -> {
            PreguntasAdopcion preguntaAAgregar = preguntasAdopcionRepositorio.buscar(pregunta.getId());
            preguntas.add(preguntaAAgregar);
        });
        parameters.put("preguntas", preguntas);
        return new ModelAndView(parameters, "administrar-preguntas.hbs");
    }

    public ModelAndView nueva(Request request, Response response) {
        HashMap<String, Object> parameters = new HashMap<>();

        try {
            LoginController.cargarPerfiles(parameters, request);
            Usuario usuario = usuarioRepositorio.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parameters.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        if (parameters.get("administrador") == null) {
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        return new ModelAndView(parameters, "detalle-pregunta.hbs");
    }

    public Response agregar(Request request, Response response){
        HashMap<String, Object> parameters = new HashMap<>();

        try {
            LoginController.cargarPerfiles(parameters, request);
            Usuario usuario = usuarioRepositorio.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parameters.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return response;
        }

        if (parameters.get("administrador") == null) {
            response.redirect("/login");
            return response;
        }

        Administrador admin = administradorRepositorio.buscar(request.session().attribute("id"));

        String descripcion = request.queryParams("descripcion");
        List<String> valores = Arrays.asList(request.queryParams("valores").split(";"));

        PreguntasAdopcion preguntasAdopcion = new PreguntasAdopcion();
        preguntasAdopcion.setDescripcion(descripcion);
        preguntasAdopcion.setValor(valores);

        Organizacion organizacion = admin.getOrganizacionPerteneciente();
        organizacion.addPreguntasRequeridasDeAdopcion(preguntasAdopcion);

        this.preguntasAdopcionRepositorio.agregar(preguntasAdopcion);
        this.organizacionRepositorio.modificar(organizacion);

        return response;
    }

    public ModelAndView mostrar(Request request, Response response) {
        HashMap<String, Object> parameters = new HashMap<>();

        try {
            LoginController.cargarPerfiles(parameters, request);
            Usuario usuario = usuarioRepositorio.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parameters.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        if (parameters.get("administrador") == null) {
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        Administrador admin = administradorRepositorio.buscar(request.session().attribute("id"));
        PreguntasAdopcion pregunta = this.preguntasAdopcionRepositorio.buscar(Integer.parseInt(request.params("id")));

        if(!admin.getOrganizacionPerteneciente().getPreguntasRequeridasAdopcion().contains(pregunta)) {
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        parameters.put("pregunta", pregunta);
        parameters.put("mostrar", "si");
        return new ModelAndView(parameters, "detalle-pregunta.hbs");
    }

    public ModelAndView editar(Request request, Response response) {
        HashMap<String, Object> parameters = new HashMap<>();

        try {
            LoginController.cargarPerfiles(parameters, request);
            Usuario usuario = usuarioRepositorio.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parameters.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        if (parameters.get("administrador") == null) {
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        Administrador admin = administradorRepositorio.buscar(request.session().attribute("id"));
        PreguntasAdopcion pregunta = this.preguntasAdopcionRepositorio.buscar(Integer.parseInt(request.params("id")));

        if(!admin.getOrganizacionPerteneciente().getPreguntasRequeridasAdopcion().contains(pregunta)) {
            // Si la caracteristica no pertenece a la organización del administrador, deberiamos mostrar un mensaje de error, un 404 o algo
            // por ahora lo mando al login de nuevo
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        parameters.put("pregunta", pregunta);
        parameters.put("editar", "si");
        return new ModelAndView(parameters, "detalle-pregunta.hbs");
    }

    public Response modificar(Request request, Response response) {
        HashMap<String, Object> parameters = new HashMap<>();

        try {
            LoginController.cargarPerfiles(parameters, request);
            Usuario usuario = usuarioRepositorio.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parameters.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return response;
        }

        if (parameters.get("administrador") == null) {
            response.redirect("/login");
            return response;
        }

        Administrador admin = administradorRepositorio.buscar(request.session().attribute("id"));
        PreguntasAdopcion pregunta = this.preguntasAdopcionRepositorio.buscar(Integer.parseInt(request.params("id")));

        if(!admin.getOrganizacionPerteneciente().getPreguntasRequeridasAdopcion().contains(pregunta)) {
            response.redirect("/login");
            return response;
        }

        String descripcion = request.queryParams("descripcion");
        List<String> valores = Arrays.asList(request.queryParams("valores").split(";"));

        Organizacion organizacion = admin.getOrganizacionPerteneciente();

        List<String> nuevaListaDeValores = new ArrayList<>();
        pregunta.setValor(nuevaListaDeValores);
        nuevaListaDeValores.addAll(valores);

        this.preguntasAdopcionRepositorio.modificar(pregunta);
        this.organizacionRepositorio.modificar(organizacion);
        this.administradorRepositorio.modificar(admin);

        return response;
    }

    public Response eliminar(Request request, Response response) {
        HashMap<String, Object> parameters = new HashMap<>();

        try {
            LoginController.cargarPerfiles(parameters, request);
            Usuario usuario = usuarioRepositorio.buscar(request.session().attribute("id"));
            if (usuario != null) {
                parameters.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/login");
            return response;
        }

        if (parameters.get("administrador") == null) {
            response.redirect("/login");
            return response;
        }

        Administrador admin = administradorRepositorio.buscar(request.session().attribute("id"));
        Organizacion organizacion = admin.getOrganizacionPerteneciente();

        PreguntasAdopcion pregunta = this.preguntasAdopcionRepositorio.buscar(Integer.parseInt(request.params("id")));
        organizacion.removePreguntasRequeridasDeAdopcion(pregunta);


        this.preguntasAdopcionRepositorio.eliminar(pregunta);
        this.organizacionRepositorio.modificar(organizacion);

        return response;
    }
}