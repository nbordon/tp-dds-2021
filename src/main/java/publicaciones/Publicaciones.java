package publicaciones;

import entidades.Organizacion.Organizacion;

import java.util.ArrayList;
import java.util.List;

public abstract class Publicacion {
    private Organizacion organizacion;
    private EstadoPublicacion estado;
    private String titulo;
    private List<String> fotosURL;

    public Publicacion(){
        this.fotosURL = new ArrayList<>();
        this.titulo = new String();
    }

    public Organizacion getOrganizacion() {
        return organizacion;
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

    public abstract void notificar();
}