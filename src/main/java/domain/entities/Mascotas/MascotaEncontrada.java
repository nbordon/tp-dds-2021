package domain.entities.Mascotas;

import Api.services.entities.Hogar;
import Api.services.entities.Ubicacion;
import domain.entities.EntidadPersistente;
import domain.entities.Rescatista;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;

@Entity
@Table(name="mascota_encontrada")
public abstract class MascotaEncontrada extends EntidadPersistente {

    @ElementCollection
    @CollectionTable(name="masco_encontrda_fotos")
    private List<String> fotos;
    @OneToMany
    @JoinTable(name = "fotos_mascota_encontrada")
    private List<Foto> fotosB64;
    private Date fechaEnLaQueSeEncontro;
    private String descripcionEstadoEncotrado;

    @Getter @Setter
    private TipoMascota tipoMascota;
    @Getter @Setter
    private String sexo;
    @OneToOne
    @JoinColumn(name = "rescatista_id")
    private Rescatista rescatista;
    @OneToOne
    private Ubicacion ubicacion;
    @Getter @Setter
    @ManyToOne
    private Hogar hogarDeTransito;
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
    public abstract void notificar(String mensaje);

    public Object getMascota(){
        return null;
    }

    public List<Foto> getFotosB64() {
        return fotosB64;
    }

    public void setFotosB64(List<Foto> fotosB64) {
        this.fotosB64 = fotosB64;
    }

    public void addFoto(Foto foto){
        this.fotosB64.add(foto);
    }
}
