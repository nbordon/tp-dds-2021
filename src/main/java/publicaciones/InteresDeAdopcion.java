package publicaciones;

import entidades.Contacto;
import entidades.Mascotas.Mascota;
import entidades.Persona;

public class InteresDeAdopcion extends Publicacion {
    private Contacto personaNoRegistrada;

    @Override
    public void notificar(){
        Persona duenioMascotaEnAdopcion = mascotaEnAdopcion.getDuenio();

        duenioMascotaEnAdopcion.getInformacionPersonal().getFormaComunicacion().forEach(
                forma->forma.notificar("Tu mascota quiere ser adoptada!",));

    }
}
