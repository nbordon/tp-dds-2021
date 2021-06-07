package entidades;

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

    public String getNombre() {
        return nombre;
    }

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
}
