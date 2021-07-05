package entidades;

import Api.services.entities.Hogar;
import entidades.Mascotas.MascotaEncontrada;

public class Rescatista extends PersonaNoRegistrada{
    private MascotaEncontrada mascotaEncontrada;
    private Hogar hogarDeTransito;

    public MascotaEncontrada getMascotaEncontrada() {
        return mascotaEncontrada;
    }

    public void setMascotaEncontrada(MascotaEncontrada mascotaEncontrada) {
        this.mascotaEncontrada = mascotaEncontrada;
    }

    public Hogar getHogarDeTransito() {
        return hogarDeTransito;
    }

    public void setHogarDeTransito(Hogar hogarDeTransito) {
        this.hogarDeTransito = hogarDeTransito;
    }

    @Override
    public void notificar() {
        //TODO informar al dueño o generar publicacion
    }
}
