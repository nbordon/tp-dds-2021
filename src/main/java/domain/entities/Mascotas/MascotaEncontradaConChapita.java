package domain.entities.Mascotas;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import domain.entities.Persona;
import domain.entities.Rescatista;
import lombok.*;

@Entity
@Table(name = "mascota_encontrada_con_chapita")
public class MascotaEncontradaConChapita extends MascotaEncontrada{
    @Getter @Setter
    @ManyToOne
    private Mascota mascota;

    @Override
    public void notificar(){

    };

    @Override
    public void notificar(String mensaajeMediosComunicacion) {
        Persona duenioMascota = this.mascota.getDuenio();
        Rescatista rescatista = this.getRescatista();
        duenioMascota.getInformacionPersonal().getFormaComunicacion().forEach(formaCom ->{
            String asunto = "ATENCION. MASCOTA ENCONTRADA   ";
            String mensaje = "Alguien ha encontrado a *"+this.mascota.getApodo()+"* " +
                    "y quiere comunicarse con su dueño para que devolverselo!. Este medio esta asociado a la mascota como contacto de emergencia." +
                    "Para comunicarte con el rescatista podes utilizar los siguientes medios de comunicacion : "+mensaajeMediosComunicacion+".\n Utiliza los siguientes datos para ponerte en contacto:" +
                    "\n Nombre y Apellido: "+rescatista.getInformacionPersonal().getNombre()+" "+rescatista.getInformacionPersonal().getApellido()+
                    "\n Telefono: "+rescatista.getInformacionPersonal().getContactoDuenio().getNumeroDeTelefono()+
                    "\n o en su email: "+rescatista.getInformacionPersonal().getEmail();
            this.mascota.getDuenio().getInformacionPersonal().getContactos().forEach(contacto -> {
                formaCom.notificar(asunto, mensaje, contacto);
            });
            //formaCom.notificar(asunto, mensaje, this.mascota.getDuenio().getInformacionPersonal().getContactoDuenio());
        });

    }
}
