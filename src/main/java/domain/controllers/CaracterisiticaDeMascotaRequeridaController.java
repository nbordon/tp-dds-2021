package domain.controllers;

import domain.entities.Mascotas.CaracterisiticaDeMascotaRequerida;
import domain.entities.Organizacion.Administrador;
import domain.entities.Organizacion.Organizacion;
import domain.entities.Usuario;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CaracterisiticaDeMascotaRequeridaController {

    private Repositorio<Organizacion> organizacionRepositorio;
    private Repositorio<Usuario> usuarioRepositorio;
    private Repositorio<Administrador> administradorRepositorio;
    private Repositorio<CaracterisiticaDeMascotaRequerida> caracteristicaDeMascotaRequeridaRepositorio;

    public CaracterisiticaDeMascotaRequeridaController() {
        this.organizacionRepositorio = FactoryRepositorio.get(Organizacion.class);
        this.usuarioRepositorio = FactoryRepositorio.get(Usuario.class);
        this.administradorRepositorio = FactoryRepositorio.get(Administrador.class);
        this.caracteristicaDeMascotaRequeridaRepositorio = FactoryRepositorio.get(CaracterisiticaDeMascotaRequerida.class);
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
        parameters.put("caracteristicas", admin.getOrganizacionPerteneciente().getCaracteristicasDeMascotasRequeridas());
        return new ModelAndView(parameters, "administrar-caracteristicas.hbs");
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

        return new ModelAndView(parameters, "detalle-caracteristica.hbs");
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

        CaracterisiticaDeMascotaRequerida nuevaCaracteristica = new CaracterisiticaDeMascotaRequerida();
        nuevaCaracteristica.setDescripcion(descripcion);
        nuevaCaracteristica.setValor(valores);

        Organizacion organizacion = admin.getOrganizacionPerteneciente();
        organizacion.addCaracteristicaDeMascotasRequerida(nuevaCaracteristica);

        this.caracteristicaDeMascotaRequeridaRepositorio.agregar(nuevaCaracteristica);
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
        CaracterisiticaDeMascotaRequerida caracteristica = this.caracteristicaDeMascotaRequeridaRepositorio.buscar(Integer.parseInt(request.params("id")));

        if(!admin.getOrganizacionPerteneciente().getCaracteristicasDeMascotasRequeridas().contains(caracteristica)) {
            // Si la caracteristica no pertenece a la organización del administrador, deberiamos mostrar un mensaje de error, un 404 o algo
            // por ahora lo mando al login de nuevo
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        parameters.put("caracteristica", caracteristica);
        parameters.put("mostrar", "si");
        return new ModelAndView(parameters, "detalle-caracteristica.hbs");
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
        CaracterisiticaDeMascotaRequerida caracteristica = this.caracteristicaDeMascotaRequeridaRepositorio.buscar(Integer.parseInt(request.params("id")));

        if(!admin.getOrganizacionPerteneciente().getCaracteristicasDeMascotasRequeridas().contains(caracteristica)) {
            // Si la caracteristica no pertenece a la organización del administrador, deberiamos mostrar un mensaje de error, un 404 o algo
            // por ahora lo mando al login de nuevo
            response.redirect("/login");
            return new ModelAndView(null, "login.hbs");
        }

        parameters.put("caracteristica", caracteristica);
        parameters.put("editar", "si");
        return new ModelAndView(parameters, "detalle-caracteristica.hbs");
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
        CaracterisiticaDeMascotaRequerida caracteristica = this.caracteristicaDeMascotaRequeridaRepositorio.buscar(Integer.parseInt(request.params("id")));

        if(!admin.getOrganizacionPerteneciente().getCaracteristicasDeMascotasRequeridas().contains(caracteristica)) {
            // Si la caracteristica no pertenece a la organización del administrador, deberiamos mostrar un mensaje de error, un 404 o algo
            // por ahora lo mando al login de nuevo
            response.redirect("/login");
            return response;
        }

        String descripcion = request.queryParams("descripcion");
        List<String> valores = Arrays.asList(request.queryParams("valores").split(";"));

        Organizacion organizacion = admin.getOrganizacionPerteneciente();

        List<String> nuevaListaDeValores = new ArrayList<>();
        caracteristica.setValor(nuevaListaDeValores);
        nuevaListaDeValores.addAll(valores);

        this.caracteristicaDeMascotaRequeridaRepositorio.modificar(caracteristica);
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

        CaracterisiticaDeMascotaRequerida caracteristicaAEliminar = this.caracteristicaDeMascotaRequeridaRepositorio.buscar(Integer.parseInt(request.params("id")));
        organizacion.removeCaracteristicaDeMascotasRequerida(caracteristicaAEliminar);


        this.caracteristicaDeMascotaRequeridaRepositorio.eliminar(caracteristicaAEliminar);
        this.organizacionRepositorio.modificar(organizacion);

        return response;
    }
}