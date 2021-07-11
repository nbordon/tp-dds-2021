package entidades.Organizacion;

import publicaciones.Publicacion;
import publicaciones.PublicacionIntencionDeAdopcion;
import publicaciones.PublicacionMascotaEnAdopcion;
import publicaciones.PublicacionMascotaEncontradaSinChapita;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContenedorPublicaciones {
    private List<PublicacionMascotaEnAdopcion> publicacionesMascotaEnAdopcion;
    private List<PublicacionMascotaEncontradaSinChapita> publicacionesMascotaEncontradaSinChapita;
    private List<PublicacionIntencionDeAdopcion> publicacionesIntencionDeAdopcion;
    private List<Publicacion> publicacionesEnEsperaDeAprobacion;

    public ContenedorPublicaciones(){
        this.publicacionesEnEsperaDeAprobacion = new ArrayList<>();
        this.publicacionesIntencionDeAdopcion = new ArrayList<>();
        this.publicacionesMascotaEncontradaSinChapita = new ArrayList<>();
        this.publicacionesMascotaEnAdopcion = new ArrayList<>();
    }

    public List<PublicacionMascotaEnAdopcion> publicacionesAprobadasMascotaEnAdopcion() {
        return publicacionesMascotaEnAdopcion.stream().
                filter(publicacionMascotaEnAdopcion -> publicacionMascotaEnAdopcion.esAprobada()).
                collect(Collectors.toList());
    }

    public List<PublicacionMascotaEncontradaSinChapita> publicacionesAprobadasMascotaEncontradaSinChapita() {
        return publicacionesMascotaEncontradaSinChapita.stream().
                filter(publicacionMascotaEncontradaSinChapita -> publicacionMascotaEncontradaSinChapita.esAprobada()).
                collect(Collectors.toList());
    }

    public List<PublicacionIntencionDeAdopcion> publicacionesAprobadasIntencionDeAdopcion() {
        return publicacionesIntencionDeAdopcion.stream().
                filter(publicacionIntencionDeAdopcion -> publicacionIntencionDeAdopcion.esAprobada()).
                collect(Collectors.toList());
    }

    public void agregarPublicacionEnEsperaDeAprobacion(PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion) {
        this.publicacionesEnEsperaDeAprobacion.add(publicacionMascotaEnAdopcion);
    }

    public void addPublicacionMascotaEncontradaSinChapita(PublicacionMascotaEncontradaSinChapita publicacion){
        this.publicacionesMascotaEncontradaSinChapita.add(publicacion);
    }

}
