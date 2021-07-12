package publicaciones;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;
import entidades.Mascotas.Mascota;
import entidades.Organizacion.Organizacion;
import entidades.Organizacion.PreguntasAdopcion;
import entidades.Organizacion.Respuesta;


import java.util.List;

public class PublicacionMascotaEnAdopcion extends Publicacion {
    private Mascota mascotaEnAdopcion;
    private List<Respuesta> respuestasPreguntas;
    //private Organizacion organizacionDuenio = mascotaEnAdopcion.getDuenio().getOrganizacion();

    public Mascota getMascotaEnAdopcion() {
        return mascotaEnAdopcion;
    }

    public List<Respuesta> getRespuestasPreguntas() {
        return respuestasPreguntas;
    }

    public List<PreguntasAdopcion> getPreguntasAdopcion(){return this.organizacionDuenio().getPreguntasRequeridasAdopcion();}
/*
    public void cargarRespuestasPreguntas(Respuesta respuesta){
        respuestasPreguntas.add(respuesta);
    }

 */
    private Organizacion organizacionDuenio(){
        return mascotaEnAdopcion.getDuenio().getOrganizacion();
    }

    public void notificarAdoptanteEncontrado() {
        String mensaje;
        Contacto contactoDuenio =this.getDuenio().getInformacionPersonal().getContactoDuenio();
        List<EstrategiaDeNotificacion> estrategiaDeUsuario = this.getDuenio().getInformacionPersonal()
                .getFormaComunicacion();
        mensaje = "Tu mascota: " + mascotaEnAdopcion.getNombre() + " quiere ser adoptada!";
        estrategiaDeUsuario.forEach(estrategia->estrategia.notificar(mensaje,contactoDuenio));
    }

}
