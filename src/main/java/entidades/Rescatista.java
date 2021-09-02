package entidades;

import Api.services.entities.Hogar;
import entidades.Mascotas.MascotaEncontrada;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
public class Rescatista extends PersonaNoRegistrada {
    @OneToOne
    private MascotaEncontrada mascotaEncontrada;
    @ManyToOne
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
        this.mascotaEncontrada.notificar();
    }
}
