package publicaciones;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;
import entidades.Mascotas.Mascota;

import java.util.List;

public class MascotaEnAdopcion extends Publicacion {
    private Mascota mascotaEnAdopcion;

    public void setMascota(Mascota mascota) {
        this.mascotaEnAdopcion = mascota;
    }

    public void publicacionMascotaEnAdopcion(){
        super.setEstado(EstadoPublicacion.PENDIENTE);
    }
    //TODO las preguntas para la adopcion
    @Override
    public void notificar() {
        Contacto exDuenio = mascotaEnAdopcion.getDuenio();
        String mensaje;

        this.publicacionMascotaEnAdopcion();
        List<EstrategiaDeNotificacion> estrategiaDeUsuario = mascotaEnAdopcion.getDuenio().getInformacionPersonal().getFormaComunicacion();
        mensaje = "La publicacion de adopcion de: " + mascotaEnAdopcion.getNombre() + " esta en espera de aprobacion.";
        estrategiaDeUsuario.forEach(estrategia->estrategia.notificar(mensaje,exDuenio));

        //TODO agregar preguntas que el duenio va a tener que responder
    }
}
