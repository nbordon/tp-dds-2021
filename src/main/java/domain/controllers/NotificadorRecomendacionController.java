package domain.controllers;

import domain.entities.Organizacion.ContenedorPublicaciones;
import domain.entities.Organizacion.Organizacion;
import domain.entities.Recomendaciones.NotificadorSemanal;
import domain.entities.Recomendaciones.Recomendacion;
import domain.entities.publicaciones.PublicacionIntencionDeAdopcion;
import domain.entities.publicaciones.PublicacionMascotaEnAdopcion;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import spark.Request;
import spark.Response;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

public class NotificadorRecomendacionController {
    private Repositorio<Organizacion> repoOrganizaciones;
    private Repositorio<PublicacionMascotaEnAdopcion> repoPublicacionesAdopcion;
    private  Repositorio<PublicacionIntencionDeAdopcion> repoPublicacionesIntencion;

    public NotificadorRecomendacionController() {
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
        this.repoPublicacionesAdopcion = FactoryRepositorio.get(PublicacionMascotaEnAdopcion.class);
        this.repoPublicacionesIntencion = FactoryRepositorio.get(PublicacionIntencionDeAdopcion.class);
    }

    public Response notificadorManual(Request request, Response response) throws InterruptedException {
        List<Organizacion> organizaciones = repoOrganizaciones.buscarTodos();
        List<PublicacionMascotaEnAdopcion> publicacionesMascotaEnAdopcion = repoPublicacionesAdopcion.buscarTodos();
        List<PublicacionIntencionDeAdopcion> publicacionesIntencionDeAdopcion = repoPublicacionesIntencion.buscarTodos();

        for (Organizacion organizacion : organizaciones) {
            List<PublicacionMascotaEnAdopcion> publicacionesOrganizacionAdopcion = publicacionesMascotaEnAdopcion.stream().filter(p ->
                    p.getOrganizacion().getId() == organizacion.getId()
            ).collect(Collectors.toList());
            organizacion.setContenedorPublicaciones(new ContenedorPublicaciones());
            publicacionesOrganizacionAdopcion.forEach(p -> {
                organizacion.getContenedorPublicaciones().agregarPublicacionMascotaEnAdopcion(p);
            });
            List<PublicacionIntencionDeAdopcion> intenciones = publicacionesIntencionDeAdopcion.stream().filter(p ->
                    p.getOrganizacion().getId() == organizacion.getId()
            ).collect(Collectors.toList());
            intenciones.forEach(p -> {
                organizacion.getContenedorPublicaciones().agregarPublicacionIntecnioDeAdopcion(p);
            });

            Recomendacion recomendacion = new Recomendacion();
            NotificadorSemanal notificador = new NotificadorSemanal(recomendacion, organizacion);
            Timer temporizador = new Timer();
            Integer segundos = 20;

            temporizador.scheduleAtFixedRate(notificador, new Date(), 1000 * segundos);
            //Thread.sleep(1000*60);
        }
        response.redirect("/home");
        return response;
    }
}
