package publicaciones;

import EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.Contacto;
import entidades.Mascotas.Mascota;
import entidades.Organizacion.Respuesta;


import java.util.ArrayList;
import java.util.List;

public class PublicacionMascotaEnAdopcion extends Publicacion {
    private Mascota mascotaEnAdopcion;
    private List<Respuesta> respuestasPreguntas;

    public Mascota getMascotaEnAdopcion() {
        return mascotaEnAdopcion;
    }

    public List<Respuesta> getRespuestasPreguntas() {
        return respuestasPreguntas;
    }
    //TODO Borrar
    /*

    public void completarPreguntas(){
        this.getOrganizacion().getPreguntasRequeridasAdopcion().forEach(
                pregunta-> pregunta.contestar());
    }*/

    @Override
    public void notificar() {
        String mensaje;
        Contacto contactoDuenio =this.getDuenio().getInformacionPersonal().getContactoDuenio();
        List<EstrategiaDeNotificacion> estrategiaDeUsuario = this.getDuenio().getInformacionPersonal()
                .getFormaComunicacion();
        mensaje = "La publicacion de adopcion de: " + this.getMascota().getNombre() + " esta en espera de aprobacion.";
        estrategiaDeUsuario.forEach(estrategia->estrategia.notificar(mensaje,contactoDuenio));
    }

    public PublicacionMascotaEnAdopcion(Mascota mascotaEnAdopcion,String titulo,List<String> fotos) {
        respuestasPreguntas = new ArrayList<>();
        //TODO: borrar, no se usan los setters en el costructor
        this.setMascota(mascotaEnAdopcion);
        this.setTitulo(titulo);
        this.setFotosURL(fotos);
        //this.completarPreguntas();
        this.cambiarEstadoAPendiente();
        this.notificar();
    }
}
