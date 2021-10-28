package domain.controllers;

import domain.entities.Mascotas.*;
import domain.entities.Organizacion.Organizacion;
import domain.entities.Persona;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllersMascota {
    private Repositorio<Mascota> repositorio;
    private Repositorio<Persona> repoPersonas;
    private Repositorio<Organizacion> repoOrganizaciones;

    public ControllersMascota(){
        this.repositorio = FactoryRepositorio.get(Mascota.class);
        this.repoPersonas= FactoryRepositorio.get(Persona.class);
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
    }

    public ModelAndView mostrarTodos(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        List<Mascota> mascotas = this.repositorio.buscarTodos();
        parametros.put("mascotas", mascotas);
        return new ModelAndView(parametros,"listado-mascotas.hbs");
    }

    public ModelAndView mostrar(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        Mascota mascota = this.repositorio.buscar(new Integer(request.params("id")));
        if(mascota != null){
            parametros.put("mascota",mascota);
        }
        return new ModelAndView(parametros, "registrarMascota.hbs");

    }

    public ModelAndView registrar(Request request,Response response){
        Map<String, Object> parametros = new HashMap<>();
        //Integer idPersona = request.session().attribute("id");
        //Persona persona = this.repoPersonas.buscar(idPersona);
        //Organizacion organizacionAsociada = persona.getOrganizacion();
        //parametros.put("caracteristicas",organizacionAsociada.getCaracteristicasDeMascotasRequeridas());
        Organizacion organizacionAsociada = this.repoOrganizaciones.buscar(1);
        parametros.put("caracteristicas",organizacionAsociada.getCaracteristicasDeMascotasRequeridas());
        //TODO:^^^^^^^^^^^^^^^^^^^^^^ sacar estas lineas cuando tenga al usuario logeado ^^^^^^^^^^^^^^^
        return new ModelAndView(parametros,"registrarMascota.hbs");
    }

    private void asignarParametrosAMascota(Mascota mascota, Request request) {
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

        //todo: asociar la pregunta del color y castrada con la respuesta y cargarla a mascota

        /*TODO PROBAR CON EL USUARIO LOGEADO
        Integer idPersona = request.session().attribute("id");
        Persona duenioMascota = this.repoPersonas.buscar(idPersona);
        mascota.setDuenio(duenioMascota);
        Organizacion org = duenioMascota.getOrganizacion();
        org.getCaracteristicasDeMascotasRequeridas().
                forEach(caracteristica -> caracteristica.contestar(request.queryParams("caracteristica")));
         */

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
        Organizacion organizacionAsociada = this.repoOrganizaciones.buscar(1);
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
