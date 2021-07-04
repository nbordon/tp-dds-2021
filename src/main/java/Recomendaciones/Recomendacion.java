package Recomendaciones;

import entidades.Organizacion.Respuesta;
import publicaciones.PublicacionIntencionDeAdopcion;
import publicaciones.Publicacion;
import publicaciones.PublicacionMascotaEnAdopcion;

import java.util.List;

public class Recomendacion {

    public List<PublicacionMascotaEnAdopcion> obtenerPublicacionesRecomendadas(PublicacionIntencionDeAdopcion intencionAdopcion){
        List<Respuesta> repuestasAdopcion = intencionAdopcion.getRepuestasPreguntas();
        List<PublicacionMascotaEnAdopcion> publicacionMascotaEnAdopcions = getPublicacionesAdopcionOrganizacion(intencionAdopcion);
        List<PublicacionMascotaEnAdopcion> publicacionesRecomendadas = null;
        // Aca se tiene que matchear las respuestas de la intencion con las de las publicaciones de adopcion
        // y se van agregando a la lista de publicaciones recomendadas


        return publicacionesRecomendadas;

    }

    public List<PublicacionMascotaEnAdopcion> getPublicacionesAdopcionOrganizacion(PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion){
        List<PublicacionMascotaEnAdopcion> publicacionMascotaEnAdopcion = publicacionIntencionDeAdopcion.getPersonaInteresada().getOrganizacion().getPublicacionesAprobadasMascotaEnAdopcion();
        //filtrar las publicaciones de adopcion
        return publicacionMascotaEnAdopcion;
    }
    /*
    public void notificacionesSemanales(List<Publicacion> publicaciones) {
            publicaciones.forEach(publicacion ->
                    personaInteresada.getInformacionPersonal().getFormaComunicacion().forEach(
                            estrategiaDeNotificacion -> estrategiaDeNotificacion.notificar(
                                    publicacion.getTitulo(), getContactoInteresado())
                    ));

        }

    }*/
}
