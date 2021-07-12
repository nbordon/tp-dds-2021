package entidades.Organizacion;

import publicaciones.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContenedorPublicaciones {
    private List<PublicacionMascotaEnAdopcion> publicacionesMascotaEnAdopcion;
    private List<PublicacionMascotaEncontradaSinChapita> publicacionesMascotaEncontradaSinChapita;
    private List<IntencionDeAdopcion> publicacionesIntencionDeAdopcion;
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

    public List<IntencionDeAdopcion> publicacionesAprobadasIntencionDeAdopcion() {
        return publicacionesIntencionDeAdopcion.stream().
                filter(publicacionIntencionDeAdopcion -> publicacionIntencionDeAdopcion.esAprobada()).
                collect(Collectors.toList());
    }

    public void agregarPublicacionEnEsperaDeAprobacion(PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion) {
        this.publicacionesEnEsperaDeAprobacion.add(publicacionMascotaEnAdopcion);
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

}
