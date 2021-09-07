package entidades;

import Api.services.entities.Hogar;
import entidades.Mascotas.MascotaEncontrada;

import javax.persistence.*;

@Entity
@Table(name = "rescatista")
public class Rescatista extends PersonaNoRegistrada {
    @OneToOne
    @JoinColumn(name = "mascota_encontrada_id", referencedColumnName = "id")
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
