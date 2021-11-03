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

import java.time.LocalDate;
import java.util.*;


public class ControllersMascota {
    private Repositorio<Mascota> repositorio;
    private Repositorio<Persona> repoPersonas;
    private Repositorio<Usuario> repoUsuarios;
    private Repositorio<Organizacion> repoOrganizaciones;

    public ControllersMascota(){
        this.repositorio = FactoryRepositorio.get(Mascota.class);
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
        Mascota mascota = this.repositorio.buscar(new Integer(request.params("id")));
        LoginController.cargarUsuario(parametros,request);
        if(parametros.get("usuario")==null){
            response.redirect("/login");
        }
        Persona duenioMascota = this.obtenerPersona(request);
        Organizacion organizacionAsociada = duenioMascota.getOrganizacion();
        parametros.put("caracteristicas",organizacionAsociada.getCaracteristicasDeMascotasRequeridas());
        if(mascota != null){
            parametros.put("mascota",mascota);
        }
        return new ModelAndView(parametros, "detalle-mascota.hbs");

    }

    public ModelAndView registrar(Request request,Response response){
        Map<String, Object> parametros = new HashMap<>();
        Persona duenioMascota = this.obtenerPersona(request);
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
        TipoMascota tipo = TipoMascota.valueOf(request.queryParams("tipoMascota"));
        mascota.setTipoMascota(tipo);
        LocalDate fechaNacimiento = LocalDate.parse(request.queryParams("fechaDeNacimiento"));
        mascota.setFechaNacimiento(fechaNacimiento);
        mascota.setEstado(EstadoMascota.NO_PERDIDA);
        List<String> fotos = Collections.singletonList(request.queryParams("fotos"));
        fotos.forEach(foto->mascota.agregoFoto(foto));
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
        Mascota mascota = new Mascota();
        asignarParametrosAMascota(mascota, request);
        this.repositorio.agregar(mascota);
        response.redirect("/listado-mascotas");
        return response;
    }

    public ModelAndView crear(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        Repositorio<Mascota> mascotaRepositorio = FactoryRepositorio.get(Mascota.class);
        parametros.put("mascotas",mascotaRepositorio.buscarTodos());
        return new ModelAndView(parametros,"listado-mascotas.hbs");
    }

    public ModelAndView editar(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        Mascota mascotaBuscada = this.repositorio.buscar(new Integer(request.params("id")));
        System.out.println(mascotaBuscada.getDescripcionFisica());
        parametros.put("mascota",mascotaBuscada);
        Persona duenioMascota = this.obtenerPersona(request);
        Organizacion organizacionAsociada = duenioMascota.getOrganizacion();
        parametros.put("caracteristicas",organizacionAsociada.getCaracteristicasDeMascotasRequeridas());
        return new ModelAndView(parametros, "registrarMascota.hbs");
    }

    public Response editarMascota(Request request, Response response){
        Mascota mascotaBuscada = this.repositorio.buscar(new Integer(request.params("id")));
        asignarParametrosAMascota(mascotaBuscada,request);
        this.repositorio.modificar(mascotaBuscada);
        response.redirect("/listado-mascotas");
        return  response;
    }

    public Response eliminar(Request request, Response response){
        Mascota mascota = this.repositorio.buscar(new Integer(request.params("id")));
        this.repositorio.eliminar(mascota);
        return response;
    }
}
