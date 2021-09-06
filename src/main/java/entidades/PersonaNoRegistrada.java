package entidades;

import javax.persistence.*;

@MappedSuperclass
public abstract class PersonaNoRegistrada extends EntidadPersistente {
    @OneToOne
    @JoinColumn(name = "informacion_personal_id", referencedColumnName = "id")
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
