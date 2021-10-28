package domain.entities.publicaciones;

import domain.entities.EstrategiasNotificacion.EstrategiaDeNotificacion;
import domain.entities.Contacto;
import domain.entities.Mascotas.Mascota;
import domain.entities.Mascotas.TipoMascota;
import domain.entities.Organizacion.Organizacion;
import domain.entities.Organizacion.PreguntasAdopcion;
import domain.entities.Organizacion.Respuesta;
import domain.entities.Persona;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publicacion_mascota_adopcion")
public class PublicacionMascotaEnAdopcion extends Publicacion {

    @OneToOne
    @JoinColumn(name = "mascota_id", referencedColumnName = "id")
    private Mascota mascotaEnAdopcion;
    @OneToMany
    @JoinTable(name = "respuestas_x_publicacion")
    private List<Respuesta> respuestasPreguntas = new ArrayList<>();

       public Persona getDuenio() {
        return mascotaEnAdopcion.getDuenio();
    }


    public Mascota getMascotaEnAdopcion() {
        return mascotaEnAdopcion;
    }

    public List<Respuesta> getRespuestasPreguntas() {
        return respuestasPreguntas;
    }

    public List<PreguntasAdopcion> getPreguntasAdopcion() {
        return this.organizacionDuenio().getPreguntasRequeridasAdopcion();
    }

    public Mascota getMascota() {
        return mascotaEnAdopcion;
    }

    public void setMascota(Mascota mascotaEnAdopcion){
        mascotaEnAdopcion = mascotaEnAdopcion;
    }

    public void cargarRespuestasPreguntas(Respuesta respuesta){ respuestasPreguntas.add(respuesta); }

    public void setMascotaEnAdopcion(Mascota mascotaEnAdopcion) { this.mascotaEnAdopcion = mascotaEnAdopcion; }

    private Organizacion organizacionDuenio(){
        return this.getOrganizacion();
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
