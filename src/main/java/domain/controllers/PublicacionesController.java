package domain.controllers;

import domain.entities.Mascotas.Mascota;
import domain.entities.Organizacion.Organizacion;
import domain.entities.publicaciones.Publicacion;
import domain.entities.publicaciones.PublicacionIntencionDeAdopcion;
import domain.entities.publicaciones.PublicacionMascotaEnAdopcion;
import domain.entities.publicaciones.PublicacionMascotaEncontradaSinChapita;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicacionesController {
    private Repositorio<PublicacionMascotaEnAdopcion> repoPublicacionMascotaEnAdopcion;
    private Repositorio<PublicacionMascotaEncontradaSinChapita> repoPublicacionMascotaEncontradaSC;
    private Repositorio<PublicacionIntencionDeAdopcion> repoPublicacionesIntencionDeAdopcion;
    private Repositorio<Mascota> repoMascotas;
    private Repositorio<Organizacion> repoOrganizacion;

    public PublicacionesController(){
        this.repoPublicacionMascotaEnAdopcion = FactoryRepositorio.get(PublicacionMascotaEnAdopcion.class);
        this.repoPublicacionMascotaEncontradaSC = FactoryRepositorio.get(PublicacionMascotaEncontradaSinChapita.class);
        this.repoPublicacionesIntencionDeAdopcion = FactoryRepositorio.get(PublicacionIntencionDeAdopcion.class);

        this.repoMascotas = FactoryRepositorio.get(Mascota.class);
        this.repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
    }

    public ModelAndView mostrarTodosEncontradasSC(Request request, Response response){
        Map<String,Object> parametros = new HashMap<>();
        List<PublicacionMascotaEncontradaSinChapita> publicacionesMascotaEncontradaSinChapita;
        publicacionesMascotaEncontradaSinChapita = repoPublicacionMascotaEncontradaSC.buscarTodos();
        List<PublicacionMascotaEncontradaSinChapita> publicacionesMascotaEncontradaSinChapitaAprobadas = new ArrayList<>();
        for(int i=0;i<publicacionesMascotaEncontradaSinChapita.size();i++){
            if(publicacionesMascotaEncontradaSinChapita.get(i).esAprobada()){
                publicacionesMascotaEncontradaSinChapitaAprobadas.add(publicacionesMascotaEncontradaSinChapita.get(i));
            }
        }
        parametros.put("publicacionesMascotaEncontradaSC",publicacionesMascotaEncontradaSinChapita);
        return new ModelAndView(parametros,"mascotasEncontradas.hbs");
    }

    public ModelAndView mostrarEncontradaSC(Request request, Response response){
        Integer idPublicacionMascotaEncontrada = new Integer(request.params("id"));
        if(idPublicacionMascotaEncontrada != null){
            PublicacionMascotaEncontradaSinChapita publicacionMascotaEncontradaSinChapita = repoPublicacionMascotaEncontradaSC.buscar(new Integer(request.params("id")));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("publicacionMascotaEncontrada",publicacionMascotaEncontradaSinChapita);
            return new ModelAndView(parametros,"detalle-mascota-encontrada.hbs");
        }
        return new ModelAndView(null,"mascotasEncontradas.hbs");
    }


    public ModelAndView mostrarTodosAdopcion(Request request, Response response){
        Map<String,Object> parametros = new HashMap<>();
        List<PublicacionMascotaEnAdopcion> publicacionesMascotaEnAdopcion;
        publicacionesMascotaEnAdopcion = repoPublicacionMascotaEnAdopcion.buscarTodos();
        List<PublicacionMascotaEnAdopcion> publicacionesMascotaEnAdopcionAprobadas = new ArrayList<>();
        for(int i=0;i<publicacionesMascotaEnAdopcion.size();i++){
            if(publicacionesMascotaEnAdopcion.get(i).esAprobada()){
                publicacionesMascotaEnAdopcionAprobadas.add(publicacionesMascotaEnAdopcion.get(i));
            }
        }
        parametros.put("publicacionesMascotaEnAdopcion",publicacionesMascotaEnAdopcion);
        return new ModelAndView(parametros,"publicacionesAdopcion.hbs");
    }

    public ModelAndView mostrarMascotaEnAdopcion(Request request, Response response){
        Integer idPublicacionMascotaEnAdopcion = new Integer(request.params("id"));

        if(idPublicacionMascotaEnAdopcion != null){
            PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion = repoPublicacionMascotaEnAdopcion.buscar(new Integer(request.params("id")));
            Integer idMascotaEnAdopcion = publicacionMascotaEnAdopcion.getMascotaEnAdopcion().getId();
            if(idMascotaEnAdopcion!=null) {
                Mascota mascotaEnAdopcion = repoMascotas.buscar(idMascotaEnAdopcion);
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("publicacionMascotaEnAdopcion", publicacionMascotaEnAdopcion);
                parametros.put("mascota",mascotaEnAdopcion);
                return new ModelAndView(parametros, "detalle-mascota-en-adopcion.hbs");
            }
        }
        return new ModelAndView(null,"publicacionesAdopcion.hbs");
    }

    public ModelAndView mostrarTodosIntencionAdopcion(Request request, Response response){
        Map<String,Object> parametros = new HashMap<>();
        List<PublicacionIntencionDeAdopcion> publicacionesIntencionDeAdopcion;
        publicacionesIntencionDeAdopcion = repoPublicacionesIntencionDeAdopcion.buscarTodos();
        List<PublicacionIntencionDeAdopcion> publicacionesIntencionDeAdopcionAprobadas = new ArrayList<>();
        for(int i=0;i<publicacionesIntencionDeAdopcion.size();i++){
            if(publicacionesIntencionDeAdopcion.get(i).esAprobada()){
                publicacionesIntencionDeAdopcionAprobadas.add(publicacionesIntencionDeAdopcion.get(i));
            }
        }
        parametros.put("publicacionesIntencionAdopcion",publicacionesIntencionDeAdopcionAprobadas);
        return new ModelAndView(parametros,"publicacionesIntencion.hbs");
    }

    public ModelAndView mostrarIntencionDeAdopcion(Request request, Response response) {
        Integer idPublicacionIntencion = new Integer(request.params("id"));
        if(idPublicacionIntencion != null){
            PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion = repoPublicacionesIntencionDeAdopcion.buscar(new Integer(request.params("id")));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("publicacionIntencion",publicacionIntencionDeAdopcion);
            return new ModelAndView(parametros,"detalle-intencion.hbs");
        }
        return new ModelAndView(null,"mascotasEncontradas.hbs");

    }


    public ModelAndView mostrarTodosParaAprobar(Request request, Response response){
        Map<String,Object> parametros = new HashMap<>();
        List<PublicacionMascotaEnAdopcion> publicacionesMascotaEnAdopcion;
        publicacionesMascotaEnAdopcion = repoPublicacionMascotaEnAdopcion.buscarTodos();
        List<PublicacionMascotaEncontradaSinChapita> publicacionesMascotaEncontradaSinChapita;
        publicacionesMascotaEncontradaSinChapita = repoPublicacionMascotaEncontradaSC.buscarTodos();
        List<PublicacionIntencionDeAdopcion> publicacionesIntencionDeAdopcion;
        publicacionesIntencionDeAdopcion = repoPublicacionesIntencionDeAdopcion.buscarTodos();
        List<Publicacion> publicacionesParaAprobar = new ArrayList<>();
        for(int i = 0; i<publicacionesMascotaEnAdopcion.size();i++){
           if(publicacionesMascotaEnAdopcion.get(i).esPendiente()){
               publicacionesParaAprobar.add(publicacionesMascotaEnAdopcion.get(i));
           }
        }
        for(int i = 0; i<publicacionesMascotaEncontradaSinChapita.size();i++){
            if(publicacionesMascotaEncontradaSinChapita.get(i).esPendiente()){
                publicacionesParaAprobar.add(publicacionesMascotaEncontradaSinChapita.get(i));
            }
        }
        for(int i = 0; i<publicacionesIntencionDeAdopcion.size();i++){
            if(publicacionesIntencionDeAdopcion.get(i).esPendiente()){
                publicacionesParaAprobar.add(publicacionesIntencionDeAdopcion.get(i));
            }
        }
        parametros.put("publicacionesParaAprobar",publicacionesParaAprobar);
        return new ModelAndView(parametros, "publicacionesParaAprobar.hbs");
    }

    public ModelAndView mostrarPublicacionParaAprobar(Request request, Response response){
        Integer idPublicacionParaAprobar = new Integer(request.params("id"));

        if(repoPublicacionMascotaEncontradaSC.buscar(idPublicacionParaAprobar)!=null){
            return mostrarEncontradaSC(request,response);
        }else {
            if(
            repoPublicacionMascotaEnAdopcion.buscar(idPublicacionParaAprobar)!=null){
                return mostrarMascotaEnAdopcion(request,response);
            }else {
                if(repoPublicacionesIntencionDeAdopcion.buscar(idPublicacionParaAprobar)!=null){
                    return mostrarIntencionDeAdopcion(request,response);
                }
            }
        }
        return new ModelAndView(null,"publicacionesParaAprobar.hbs");
    }


    Publicacion buscarPublicacionPorId(List<Publicacion> publicaciones,Integer id){
        Publicacion publicacion = null;
        for(int i=0;i<publicaciones.size();i++){
            if (publicaciones.get(i).getId( )== id){
                publicacion = publicaciones.get(i);
            }
        }
        return publicacion;
    }





}
