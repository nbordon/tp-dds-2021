package domain.entities;

import javax.persistence.*;

@Entity
@Table(name="persona_no_registrada")
public abstract class PersonaNoRegistrada extends EntidadPersistente {
    @OneToOne
    public InformacionPersonal informacionPersonal;
    @OneToOne
    public Direccion direccion;

    public InformacionPersonal getInformacionPersonal() {
        return informacionPersonal;
    }

    public void setInformacionPersonal(InformacionPersonal informacionPersonal) {
        this.informacionPersonal = informacionPersonal;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public abstract void notificar();
}
