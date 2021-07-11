package entidades.Mascotas;

import Api.services.entities.Ubicacion;
import entidades.Rescatista;

import java.util.Date;
import java.util.List;

public abstract class MascotaEncontrada {
    private List<String> fotos;
    private Date fechaEnLaQueSeEncontro;
    private String descripcionEstadoEncotrado;
    private Rescatista rescatista;
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
