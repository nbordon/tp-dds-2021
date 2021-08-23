package publicaciones;

import entidades.Mascotas.Mascota;
import entidades.Organizacion.Organizacion;
import entidades.Persona;

import java.util.ArrayList;
import java.util.List;

public abstract class Publicacion {
    private Mascota mascota;

    private Organizacion organizacion;
    private EstadoPublicacion estado;
    private String titulo;
    private List<String> fotosURL;

    public Publicacion(){
        this.fotosURL = new ArrayList<>();
        this.titulo = new String();
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascotaEnAdopcion){
        mascota = mascotaEnAdopcion;
    }

    public Organizacion getOrganizacion() {
        return mascota.getDuenio().getOrganizacion();
    }

    public Persona getDuenio() {
        return this.getMascota().getDuenio();
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;

    }

    public EstadoPublicacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPublicacion estado) {
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getFotosURL() {
        return fotosURL;
    }

    public void setFotosURL(List<String> fotosURL) {
        this.fotosURL = fotosURL;
    }

    private  void agregarEnEsperaDeAprobacion(){
        this.getOrganizacion().agregarPublicacionEnEsperaDeAprobacion((PublicacionMascotaEnAdopcion) this);
    }
    public void cambiarEstadoAPendiente(){
        this.setEstado(EstadoPublicacion.PENDIENTE);
    }

    public boolean esAprobada(){
        return this.estado == EstadoPublicacion.APROBADA;
    }
}