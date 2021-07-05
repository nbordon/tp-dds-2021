package entidades;

import EstrategiasNotificacion.EstrategiaDeNotificacion;

import java.time.LocalDate;
import java.util.List;

public class InformacionPersonal {

    private final String nombre;
    private final String apellido;
    private final TipoDeDocumento tipoDoc;
    private final int nroDocumento;
    private final LocalDate fechaNacimiento;
    private final String email;
    private final List<EstrategiaDeNotificacion> formaComunicacion;
    private final List<Contacto> contactos;

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

    public Contacto getDuenio(){
        return (Contacto) this.getContactos().stream().filter(contacto -> contacto.getEsPrincipal());
    }

}
