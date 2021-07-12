package entidades.Organizacion;
import publicaciones.PublicacionIntencionDeAdopcion;
import publicaciones.Publicacion;
import publicaciones.PublicacionMascotaEnAdopcion;
import publicaciones.PublicacionMascotaEncontradaSinChapita;
import publicaciones.*;

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

    public List<PublicacionIntencionDeAdopcion> publicacionesIntencionDeAdopcion(EstadoPublicacion estado) {
        return publicacionesIntencionDeAdopcion.stream().
                filter(publicacionIntencionDeAdopcion -> publicacionIntencionDeAdopcion.getEstado().equals(estado)).
                collect(Collectors.toList());
    }

    public void agregarPublicacionEnEsperaDeAprobacion(PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion) {
        this.publicacionesEnEsperaDeAprobacion.add(publicacionMascotaEnAdopcion);
    }

    public List<Publicacion> getPublicacionesEnEsperaDeAprobacion() {
        return publicacionesEnEsperaDeAprobacion;
    }

    public void agregarPublicacionEnEsperaDeAprobacion(Publicacion publicacion) {
        this.publicacionesEnEsperaDeAprobacion.add(publicacion);
    }

    public void addPublicacionMascotaEncontradaSinChapita(PublicacionMascotaEncontradaSinChapita publicacion){
        this.publicacionesMascotaEncontradaSinChapita.add(publicacion);
    }

    public List<Publicacion> getPublicacionesMascotaEncontrada(EstadoPublicacion estadoPublicacion){
        return this.publicacionesMascotaEncontradaSinChapita.stream().filter(p -> p.getEstado().equals(estadoPublicacion)).collect(Collectors.toList());
    }

    public void agregarPublicacionIntecnioDeAdopcion(PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion) {
        this.publicacionesIntencionDeAdopcion.add(publicacionIntencionDeAdopcion);
    }
}
