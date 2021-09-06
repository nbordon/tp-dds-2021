package entidades.Mascotas;

import Api.services.entities.Ubicacion;
import entidades.EntidadPersistente;
import entidades.Rescatista;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MascotaEncontrada extends EntidadPersistente {
    @ElementCollection
    private List<String> fotos;
    private Date fechaEnLaQueSeEncontro;
    private String descripcionEstadoEncotrado;
    @OneToOne
    @JoinColumn(name = "rescatista_id", referencedColumnName = "id")
    private Rescatista rescatista;
    @Transient
    private Ubicacion ubicacion;

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public Date getFechaEnLaQueSeEncontro() {
        return fechaEnLaQueSeEncontro;
    }

    public void setFechaEnLaQueSeEncontro(Date fechaEnLaQueSeEncontro) {
        this.fechaEnLaQueSeEncontro = fechaEnLaQueSeEncontro;
    }

    public String getDescripcionEstadoEncotrado() {
        return descripcionEstadoEncotrado;
    }

    public void setDescripcionEstadoEncotrado(String descripcionEstadoEncotrado) {
        this.descripcionEstadoEncotrado = descripcionEstadoEncotrado;
    }

    public Rescatista getRescatista() {
        return rescatista;
    }

    public void setRescatista(Rescatista rescatista) {
        this.rescatista = rescatista;
    }

    public abstract void notificar();
}
