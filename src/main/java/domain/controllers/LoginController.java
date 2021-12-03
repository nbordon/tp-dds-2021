package domain.controllers;

import domain.entities.Organizacion.Administrador;
import domain.entities.Persona;
import domain.entities.Usuario;
import domain.entities.UsuarioVoluntario;
import domain.repositories.Repositorio;
import domain.repositories.RepositorioUsuario;
import domain.repositories.factories.FactoryRepositorio;
import domain.repositories.factories.FactoryRepositorioUsuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    private static final RepositorioUsuario usuarioRepositorio = FactoryRepositorioUsuario.get();
    private static final Repositorio<Administrador> repositorioAdministradores = FactoryRepositorio.get(Administrador.class);
    private static final Repositorio<Persona> repositorioPersonas = FactoryRepositorio.get(Persona.class);
    private static final Repositorio<UsuarioVoluntario> repositorioVolunatarios = FactoryRepositorio.get(UsuarioVoluntario.class);

    public static final String USUARIO_VOLUNTARIO = "usuarioVoluntario";
    public static final String USUARIO_ADMINISTRADOR = "administrador";
    public static final String USUARIO_PERSONA = "persona";


    public ModelAndView login(Request request, Response response) {
        if (estaAutenticado(request)) {
            response.redirect("/home");
            return new ModelAndView(null, "home.hbs");
        } else {
            return new ModelAndView(null, "login.hbs");
        }
    }

    public ModelAndView loginError(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("error", true);
        return new ModelAndView(parametros, "login.hbs");
    }
    public Response iniciarSesion(Request request, Response response) {
        try {
            String usuarioNombre = request.queryParams("emailUser");
            String usuarioPassword = request.queryParams("passwordUser");

            if (usuarioRepositorio.existe(usuarioNombre)) {
                Usuario usuario = usuarioRepositorio.buscarUsuario(usuarioNombre);
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                if (bcrypt.matches(usuarioPassword, usuario.getContrasenia())) {
                    request.session(true);
                    request.session().attribute("id", usuario.getId());
                    response.redirect("/home");
                } else {
                    response.redirect("/loginError");
                }
            } else {
                response.redirect("/loginError");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/loginError");
        } finally {
            return response;
        }
    }

    public ModelAndView mostrarHome(Request request, Response response) {
        HashMap<String, Object> perfiles = new HashMap<>();
        try {
            cargarPerfiles(perfiles, request);
            Usuario usuario = usuarioRepositorio.buscar(request.session().attribute("id"));
            if (usuario != null) {
                perfiles.put("usuario", usuario);
            }
        } catch (Exception e) {

        } finally {
            return new ModelAndView(perfiles, "home.hbs");
        }

    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        response.redirect("/home");
        return response;
    }

    public Boolean estaAutenticado(Request request) {
        Integer usuarioId = request.session().attribute("id");
        if (usuarioId != null) {
            return usuarioRepositorio.buscar(usuarioId) != null;
        } else {
            return false;
        }
    }


    public static void cargarPerfiles(HashMap<String, Object> perfiles, Request request) {
        try {
            Administrador administrador = repositorioAdministradores.buscar(request.session().attribute("id"));
            Persona persona = repositorioPersonas.buscar(request.session().attribute("id"));
            UsuarioVoluntario usuarioVoluntario = repositorioVolunatarios.buscar(request.session().attribute("id"));

            if (administrador != null) {
                perfiles.put(USUARIO_ADMINISTRADOR, administrador);
            }
            if (persona != null) {
                perfiles.put(USUARIO_PERSONA, persona);
            }
            if (usuarioVoluntario != null) {
                perfiles.put(USUARIO_VOLUNTARIO, usuarioVoluntario);
                try {
                    persona = repositorioPersonas.buscar(usuarioVoluntario.getPersonaVoluntaria().getId());
                    if(persona!=null) {
                        perfiles.put(USUARIO_PERSONA, persona);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cargarUsuario(HashMap<String, Object> perfiles, Request request) {
        try {
            Usuario usuario = usuarioRepositorio.buscar(request.session().attribute("id"));
            if (usuario != null) {
                perfiles.put("usuario", usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
