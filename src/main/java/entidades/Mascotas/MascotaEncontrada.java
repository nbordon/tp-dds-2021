package entidades.Mascotas;

import Api.services.entities.Ubicacion;
import entidades.EntidadPersistente;
import entidades.Rescatista;
import javax.persistence.*;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="mascota_encontrada")
public abstract class MascotaEncontrada extends EntidadPersistente {
    @Transient
    private List<String> fotos;
    private Date fechaEnLaQueSeEncontro;
    private String descripcionEstadoEncotrado;
    @Transient
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
