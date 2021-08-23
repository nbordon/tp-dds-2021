package Recomendaciones;

import entidades.Organizacion.Organizacion;
import entidades.Persona;
import publicaciones.PublicacionIntencionDeAdopcion;
import publicaciones.PublicacionMascotaEnAdopcion;

import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificadorSemanal extends TimerTask {

    private Recomendacion recomendacion;
    private Organizacion organizacion;

    public NotificadorSemanal(Recomendacion recomendacion, Organizacion organizacion) {
        this.recomendacion = recomendacion;
        this.organizacion = organizacion;
    }

    /*
    El metodo tiene que recorrer todas la publicaciones de intencion de adopcion de la organizacion e ir obteniendo las
    recomendaciones para cada una de ellas y notificarlas a sus respectivas personas interesadas
    */

    @Override
    public void run() {
        List<PublicacionIntencionDeAdopcion> publicacionesPublicacionIntencionDeAdopcion = organizacion.getPublicacionesAprobadasIntencionDeAdopcion();
        for (int i = 0; i< publicacionesPublicacionIntencionDeAdopcion.size(); i++){
            List<PublicacionMascotaEnAdopcion> recomendaciones = recomendacion.obtenerPublicacionesRecomendadasPorIntencion(publicacionesPublicacionIntencionDeAdopcion.get(i));
            if(!recomendaciones.isEmpty()){
                notificarRecomendaciones(recomendaciones, publicacionesPublicacionIntencionDeAdopcion.get(i).getPersonaInteresada());//notificamos al interesado y limpiamos lista de recomendaciones
                recomendaciones.clear();
            }else throw new RuntimeException("Nada para recomendar");
            }
        }

    public void notificarRecomendaciones(List<PublicacionMascotaEnAdopcion> recomendaciones, Persona personaInteresada){
        recomendaciones.forEach(publicacion ->
                personaInteresada.getInformacionPersonal().getFormaComunicacion().forEach(
                        estrategiaDeNotificacion -> estrategiaDeNotificacion.notificar(
                               "Recomendacion de adopcion", publicacion.getTitulo(), personaInteresada.getInformacionPersonal().getContactoDuenio())));

    }
}
