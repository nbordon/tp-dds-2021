package domain.entities;

import db.converters.EstrategiaNotificacionConverter;
import domain.entities.EstrategiasNotificacion.EstrategiaDeNotificacion;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "informacion_personal")
public class InformacionPersonal extends EntidadPersistente{
    private  String nombre;
    private  String apellido;
    @Enumerated(EnumType.STRING)
    private  TipoDeDocumento tipoDoc;
    private int nroDocumento;
    private  LocalDate fechaNacimiento;
    private  String email;
    @Convert(converter = EstrategiaNotificacionConverter.class)
    @ElementCollection
    @CollectionTable(name="estrategia_notif_inf_pers")
    private  List<EstrategiaDeNotificacion> formaComunicacion;
    @OneToMany
    @JoinTable(name="inf_pers_contact")
    private  List<Contacto> contactos;

    public InformacionPersonal(String nombre, String apellido, TipoDeDocumento tipoDoc, int nroDocumento, LocalDate fechaNacimiento, String email, List<EstrategiaDeNotificacion> formaComunicacion, List<Contacto> contactos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDoc = tipoDoc;
        this.nroDocumento = nroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.formaComunicacion = formaComunicacion;
        this.contactos = contactos;

    }

    public InformacionPersonal() {

    }

    public List<EstrategiaDeNotificacion> getFormaComunicacion() {
        return formaComunicacion;
    }


    public Contacto getContactoDuenio(){
        //return this.getContactos().
        return this.getContactos().stream().filter(contacto -> contacto.getEsPrincipal()).collect(Collectors.toList()).get(0);
    }
}