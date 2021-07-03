package publicaciones;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;
import entidades.Mascotas.Mascota;
import entidades.Organizacion.Organizacion;
import entidades.Persona;

import java.util.List;

public class PublicacionMascotaEnAdopcion extends Publicacion {
    private Mascota mascotaEnAdopcion;

    public void setMascota(Mascota mascota) {
        this.mascotaEnAdopcion = mascota;
    }

    public void publicacionMascotaEnAdopcion(){
        super.setEstado(EstadoPublicacion.PENDIENTE);
    }

    @Override
    public void notificar() {
        Persona exDuenio = mascotaEnAdopcion.getDuenio();
        Organizacion organizacionDuenio = exDuenio.getOrganizacion();
        String mensaje;

        organizacionDuenio.getPreguntasRequeridasAdopcion().forEach(
                pregunta-> pregunta.contestar());
        Contacto contactoDuenio = (Contacto) exDuenio.getInformacionPersonal().getContactos().stream().filter(
                contacto->contacto.getEsPrincipal()
        );
        this.publicacionMascotaEnAdopcion();
        exDuenio.getOrganizacion().agregarPublicacionEnEsperaDeAprobacion(this);
        List<EstrategiaDeNotificacion> estrategiaDeUsuario = mascotaEnAdopcion.getDuenio()
                .getInformacionPersonal()
                .getFormaComunicacion();
        mensaje = "La publicacion de adopcion de: " + mascotaEnAdopcion.getNombre() + " esta en espera de aprobacion.";
        estrategiaDeUsuario.forEach(estrategia->estrategia.notificar(mensaje,contactoDuenio));
    }
}
