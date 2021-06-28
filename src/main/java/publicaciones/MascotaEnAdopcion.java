package publicaciones;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Mascotas.Mascota;
import entidades.Persona;

import java.util.List;

public class MascotaEnAdopcion extends Publicacion {
    private Mascota mascota;

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    @Override
    public void notificar() {
        Persona exDuenio =  mascota.getDuenio();
        String mensaje;

        List<EstrategiaDeNotificacion> estrategiaDeUsuario = exDuenio.getInformacionPersonal().getFormaComunicacion();
        mensaje = mascota.getNombre() + " fue puesto en adopcion!";
        estrategiaDeUsuario.forEach(estrategia -> notificar(mensaje,exDuenio));

    }

}
