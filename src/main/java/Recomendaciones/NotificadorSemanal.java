package Recomendaciones;

import entidades.Organizacion.Organizacion;
import entidades.Persona;
import publicaciones.IntencionDeAdopcion;
import publicaciones.PublicacionMascotaEnAdopcion;

import java.util.List;
import java.util.TimerTask;

public class NotificadorSemanal extends TimerTask {

    private Recomendacion recomendacion;
    private Organizacion organizacion;

    //El metodo tiene que recorrer todas la publicaciones de intencion de adopcion de la organizacion e ir obteniendo las
    // recomendaciones para cada una de ellas y notificarlas a sus respectivas personas interesadas

    @Override
    public void run() {
        List<IntencionDeAdopcion> publicacionesIntencionDeAdopcion = organizacion.getPublicacionesAprobadasIntencionDeAdopcion();
        for (int i=0;i<=publicacionesIntencionDeAdopcion.size();i++){
            List<PublicacionMascotaEnAdopcion> recomendaciones = recomendacion.obtenerPublicacionesRecomendadasPorIntencion(publicacionesIntencionDeAdopcion.get(i));
            if(!recomendaciones.isEmpty()){
                notificarRecomendaciones(recomendaciones,publicacionesIntencionDeAdopcion.get(i).getPersonaInteresada());//notificamos al interesado y limpiamos lista de recomendaciones
                recomendaciones.clear();
            }else throw new RuntimeException("Nada para recomendar");
        }


    }

    public void notificarRecomendaciones(List<PublicacionMascotaEnAdopcion> recomendaciones, Persona personaInteresada){
        recomendaciones.forEach(publicacion ->
                personaInteresada.getInformacionPersonal().getFormaComunicacion().forEach(
                        estrategiaDeNotificacion -> estrategiaDeNotificacion.notificar(
                                publicacion.getTitulo(), personaInteresada.getInformacionPersonal().getContactoDuenio())));

    }
}
