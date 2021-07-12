package Recomendaciones;

import entidades.Mascotas.CaracteristicaDeMascota;
import entidades.Organizacion.Respuesta;
import publicaciones.PublicacionIntencionDeAdopcion;
import publicaciones.PublicacionMascotaEnAdopcion;

import java.util.ArrayList;
import java.util.List;

public class Recomendacion {

    public List<PublicacionMascotaEnAdopcion> obtenerPublicacionesRecomendadasPorIntencion(PublicacionIntencionDeAdopcion intencionAdopcion) {
        List<Respuesta> preferenciasCaracteristicasIntencion = intencionAdopcion.getRespuestasCaracteristicasDeMascota();
        List<Respuesta> comodidadesIntencion = intencionAdopcion.getListaPreferencias();
        List<PublicacionMascotaEnAdopcion> publicacionesMascotaEnAdopcion = getPublicacionesAdopcionOrganizacion(intencionAdopcion);
        List<PublicacionMascotaEnAdopcion> publicacionesRecomendadas = new ArrayList<>();
        //Preferencias = Caracteristicas de la mascota
        //Comodidades = Respuestas
        // Aca se tiene que matchear las respuestas de la intencion con las de las publicaciones de adopcion
        // y se van agregando a la lista de publicaciones recomendadas

        for (int i = 0; i <= publicacionesMascotaEnAdopcion.size(); i++) {
            if (caracteristicasCumplenPreferencias(preferenciasCaracteristicasIntencion, obtenerCaracteristicasMascota(publicacionesMascotaEnAdopcion.get(i)))
                    && respuestasCumplenComodidades(comodidadesIntencion, obtenerComodidadesAdoptante(publicacionesMascotaEnAdopcion.get(i)))) {
                publicacionesRecomendadas.add(publicacionesMascotaEnAdopcion.get(i));
            }
        }
        return publicacionesRecomendadas;
    }

    public List<PublicacionMascotaEnAdopcion> getPublicacionesAdopcionOrganizacion(PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion) {
        return publicacionIntencionDeAdopcion.getPersonaInteresada().getOrganizacion().getPublicacionesAprobadasMascotaEnAdopcion();

    }

    public List<CaracteristicaDeMascota> obtenerCaracteristicasMascota(PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion) {
        return publicacionMascotaEnAdopcion.getMascotaEnAdopcion().getCaracteristicas();
    }

    public List<Respuesta> obtenerComodidadesAdoptante(PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion) {
        return publicacionMascotaEnAdopcion.getRespuestasPreguntas();
    }

    public boolean caracteristicasCumplenPreferencias(List<Respuesta> caracteristicasMascotaIntencion, List<CaracteristicaDeMascota> caracteristicasDeMascotaAdopcion) {
        List<String> valoresPreferencias = new ArrayList<>();
        List<String> valoresCaracteristicas = new ArrayList<>();
        for (int i = 0; i <= caracteristicasMascotaIntencion.size(); i++) {
            valoresPreferencias.add(caracteristicasMascotaIntencion.get(i).getValor());
        }
        for (int i = 0; i <= caracteristicasDeMascotaAdopcion.size(); i++) {
            valoresCaracteristicas.add(caracteristicasDeMascotaAdopcion.get(i).getValor());
        }

        return valoresCaracteristicas.containsAll(valoresPreferencias);

    }

    public boolean respuestasCumplenComodidades(List<Respuesta> preferenciasIntencion, List<Respuesta> comodidadesAdopcion) {
        List<String> valoresRespuestasPersonaInteresada = new ArrayList<>();
        List<String> valoresComodidadesAdoptante = new ArrayList<>();
        for (int i = 0; i <= preferenciasIntencion.size(); i++) {
            valoresRespuestasPersonaInteresada.add(preferenciasIntencion.get(i).getValor());
        }
        for (int i = 0; i <= comodidadesAdopcion.size(); i++) {
            valoresComodidadesAdoptante.add(comodidadesAdopcion.get(i).getValor());
        }
        return valoresRespuestasPersonaInteresada.containsAll(valoresComodidadesAdoptante);
    }

}

