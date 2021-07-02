package publicaciones;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;
import entidades.Mascotas.Mascota;
import entidades.Organizacion.Organizacion;
import entidades.Persona;

import java.util.List;

public class PublicacionMascotaEnAdopcion extends Publicacion {
    private Mascota mascotaEnAdopcion;
   //private Preguntas respuestasPreguntas; -> agregar al diagrama

    public void setMascota(Mascota mascota) {
        this.mascotaEnAdopcion = mascota;
    }

    public void publicacionMascotaEnAdopcion(){
        super.setEstado(EstadoPublicacion.PENDIENTE);
    }
    //TODO las preguntas para la adopcion
    @Override
    public void notificar() {
        Persona exDuenio = mascotaEnAdopcion.getDuenio();
        Organizacion organizacionDuenio = exDuenio.getOrganizacion();
        String mensaje;
        //TODO poner las respuestas del duenio
        Contacto contactoDuenio = (Contacto) exDuenio.getInformacionPersonal().getContactos().stream().filter(
                contacto->contacto.getEsPrincipal()
        );
        this.publicacionMascotaEnAdopcion();
        List<EstrategiaDeNotificacion> estrategiaDeUsuario = mascotaEnAdopcion.getDuenio().getInformacionPersonal().getFormaComunicacion();
        mensaje = "La publicacion de adopcion de: " + mascotaEnAdopcion.getNombre() + " esta en espera de aprobacion.";
        estrategiaDeUsuario.forEach(estrategia->estrategia.notificar(mensaje,contactoDuenio));
        exDuenio.getOrganizacion().agregarPublicacionEnEsperaDeAprobacion(this);


    }
}
