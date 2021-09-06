package entidades.publicaciones;

import entidades.EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;
import entidades.Mascotas.Mascota;
import entidades.Organizacion.Organizacion;
import entidades.Organizacion.PreguntasAdopcion;
import entidades.Organizacion.Respuesta;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publicacion_mascota_adopcion")
public class PublicacionMascotaEnAdopcion extends Publicacion {

    @Transient
    private Mascota mascotaEnAdopcion;
    @OneToMany
    @JoinTable(name = "respuestas_x_publicacion")
    private List<Respuesta> respuestasPreguntas = new ArrayList<>();

    public Mascota getMascotaEnAdopcion() {
        return mascotaEnAdopcion;
    }

    public List<Respuesta> getRespuestasPreguntas() {
        return respuestasPreguntas;
    }

    public List<PreguntasAdopcion> getPreguntasAdopcion() {
        return this.organizacionDuenio().getPreguntasRequeridasAdopcion();
    }

    public void cargarRespuestasPreguntas(Respuesta respuesta){ respuestasPreguntas.add(respuesta); }

    public void setMascotaEnAdopcion(Mascota mascotaEnAdopcion) { this.mascotaEnAdopcion = mascotaEnAdopcion; }

    private Organizacion organizacionDuenio(){
        return mascotaEnAdopcion.getDuenio().getOrganizacion();
    }

    public void notificarAdoptanteEncontrado(Contacto interesado) {
        String mensaje;
        Contacto contactoDuenio = this.getDuenio().getInformacionPersonal().getContactoDuenio();
        List<EstrategiaDeNotificacion> estrategiaDeUsuario = this.getDuenio().getInformacionPersonal()
                .getFormaComunicacion();
        mensaje = "Tu mascota: " + mascotaEnAdopcion.getNombre() + " quiere ser adoptada!";
        estrategiaDeUsuario.forEach(estrategia->estrategia.notificar("Tu mascota quiere ser adoptada",mensaje,contactoDuenio));
    }
}
