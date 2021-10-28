package domain.entities.Recomendaciones;

import domain.entities.Mascotas.CaracteristicaDeMascota;
import domain.entities.Organizacion.Respuesta;
import domain.entities.publicaciones.PublicacionIntencionDeAdopcion;
import domain.entities.publicaciones.PublicacionMascotaEnAdopcion;

import java.util.ArrayList;
import java.util.List;

public class Recomendacion {

    public List<PublicacionMascotaEnAdopcion> obtenerPublicacionesRecomendadasPorIntencion(PublicacionIntencionDeAdopcion intencionAdopcion) {
        List<Respuesta> preferenciasCaracteristicasIntencion = intencionAdopcion.getRespuestasCaracteristicasDeMascota();
        List<Respuesta> comodidadesIntencion = intencionAdopcion.getRespuestasComodidades();
        List<PublicacionMascotaEnAdopcion> publicacionesMascotaEnAdopcion = obtenerPublicacionesAdopcionOrganizacion(intencionAdopcion);
        List<PublicacionMascotaEnAdopcion> publicacionesRecomendadas = new ArrayList<>();

        for (int i = 0; i < publicacionesMascotaEnAdopcion.size(); i++) {
            if (caracteristicasCumplenPreferencias(preferenciasCaracteristicasIntencion, obtenerCaracteristicasMascota(publicacionesMascotaEnAdopcion.get(i)))
                    && respuestasCumplenComodidades(comodidadesIntencion, obtenerComodidadesAdoptante(publicacionesMascotaEnAdopcion.get(i)))) {
                publicacionesRecomendadas.add(publicacionesMascotaEnAdopcion.get(i));
            }
        }
        return publicacionesRecomendadas;
    }

    public List<PublicacionMascotaEnAdopcion> obtenerPublicacionesAdopcionOrganizacion(PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion) {
        return publicacionIntencionDeAdopcion.getPersonaInteresada().getOrganizacion().getPublicacionesAprobadasMascotaEnAdopcion();
    }

    public List<CaracteristicaDeMascota> obtenerCaracteristicasMascota(PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion) {
        return publicacionMascotaEnAdopcion.getMascotaEnAdopcion().getCaracteristicas();
    }


    public List<Respuesta> obtenerComodidadesAdoptante(PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion) {
        return publicacionMascotaEnAdopcion.getRespuestasPreguntas();
    }

    public boolean caracteristicasCumplenPreferencias(List<Respuesta> caracteristicasMascotaIntencion, List<CaracteristicaDeMascota> caracteristicasDeMascotaAdopcion) {
        int contadorDeMatcheo = 0;
        for (int i = 0; i < caracteristicasMascotaIntencion.size(); i++) {
            for (int j = 0; j < caracteristicasDeMascotaAdopcion.size(); j++) {
                if (esMatch(caracteristicasMascotaIntencion.get(i), caracteristicasDeMascotaAdopcion.get(j))) {
                    contadorDeMatcheo++;
                }
            }
        }
        return contadorDeMatcheo == caracteristicasMascotaIntencion.size();
    }

    public boolean respuestasCumplenComodidades(List<Respuesta> preferenciasIntencion, List<Respuesta> comodidadesAdopcion) {
        int contadorDeMatcheo = 0;
        for (int i = 0; i < comodidadesAdopcion.size(); i++) {
            for (int j = 0; j < preferenciasIntencion.size(); j++) {
                if (esMatch(comodidadesAdopcion.get(i), preferenciasIntencion.get(j))) {
                    contadorDeMatcheo++;
                }
            }
        }
        return contadorDeMatcheo == comodidadesAdopcion.size();
    }

    //esMatch si tienen el mismo valor de respuesta y la misma pregunta
    public boolean esMatch(Respuesta caracteristicaIntencion, Respuesta caracteristicaMascotaAdopcion) {
        return (mismoValorRespuesta(caracteristicaIntencion.getValor(), caracteristicaMascotaAdopcion.getValor())
                && mismoIdPregunta(caracteristicaIntencion.getPreguntaALaQuePertenece().getId(), caracteristicaMascotaAdopcion.getPreguntaALaQuePertenece().getId()));
    }

    public boolean mismoValorRespuesta(String unValor, String otroValor) {
        return unValor.equals(otroValor);
    }

    public boolean mismoIdPregunta(Integer unId, Integer otrId) {
        return unId.equals(otrId);

    }
}

