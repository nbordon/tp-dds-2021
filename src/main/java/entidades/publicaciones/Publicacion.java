package entidades.publicaciones;

import entidades.EntidadPersistente;
import entidades.Mascotas.Mascota;
import entidades.Organizacion.Organizacion;
import entidades.Persona;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "publicacion")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Publicacion extends EntidadPersistente {
    @ManyToOne
    private Mascota mascota;
    @ManyToOne
    private Organizacion organizacion;
    @Enumerated(EnumType.STRING)
    private EstadoPublicacion estado;
    private String titulo;
    //TODO: agregar converter de lista de strings
    @Transient
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