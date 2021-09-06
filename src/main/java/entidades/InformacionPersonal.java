package entidades;

import entidades.EstrategiasNotificacion.EstrategiaDeNotificacion;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
@Table(name = "informacion_personal")
public class InformacionPersonal extends EntidadPersistente{
    private  String nombre;
    private  String apellido;
    private  TipoDeDocumento tipoDoc;
    private int nroDocumento;
    //Todo: agregar tipo fecha
    private  LocalDate fechaNacimiento;
    private  String email;
   @Transient
    private  List<EstrategiaDeNotificacion> formaComunicacion;
    @OneToMany
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

    //TODO pasar lo de agarrar al duenio de entre la lista de contactos

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public TipoDeDocumento getTipoDoc() {
        return tipoDoc;
    }

    public int getNroDocumento() {
        return nroDocumento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public List<EstrategiaDeNotificacion> getFormaComunicacion() {
        return formaComunicacion;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public Contacto getContactoDuenio(){
        //return this.getContactos().
        return this.getContactos().stream().filter(contacto -> contacto.getEsPrincipal()).collect(Collectors.toList()).get(0);
    }
}