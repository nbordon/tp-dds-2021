package entidades.publicaciones;

import entidades.EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;
import entidades.InformacionPersonal;
import entidades.Mascotas.MascotaEncontradaSinChapita;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "publicacion_mascota_sin_chapita")
public class PublicacionMascotaEncontradaSinChapita extends Publicacion {
    @OneToOne
    @JoinColumn(name = "mascota_sin_chapita_id", referencedColumnName = "id")
    private MascotaEncontradaSinChapita mascotaEncontradaSinChapita;

    public PublicacionMascotaEncontradaSinChapita(){
        super.setEstado(EstadoPublicacion.PENDIENTE);
    }

    public MascotaEncontradaSinChapita getMascotaEncontradaSinChapita() {
        return mascotaEncontradaSinChapita;
    }

    public void setMascotaEncontradaSinChapita(MascotaEncontradaSinChapita mascotaEncontradaSinChapita) {
        this.mascotaEncontradaSinChapita = mascotaEncontradaSinChapita;
    }

    public void notificarMascotaEncontradaSinChapita(Contacto duenio) {
        InformacionPersonal informacionPersonalRescatista = mascotaEncontradaSinChapita.getRescatista().getInformacionPersonal();
        Contacto contactoRescatista = informacionPersonalRescatista.getContactoDuenio();
        List<EstrategiaDeNotificacion> estrategiasDeNotificacionRescatista = informacionPersonalRescatista.getFormaComunicacion();

        estrategiasDeNotificacionRescatista.forEach( estrategiaDeNotificacion -> {
            estrategiaDeNotificacion.notificar(
                    "Encontre a mi mascota",
                    "El dueño de la mascota "+duenio.getNombre()+" quiere contactar contigo. Contactar al "+duenio.getNumeroDeTelefono()+" o al su correo: "+duenio.getEmail(),
                    contactoRescatista);
        });
    }
}
