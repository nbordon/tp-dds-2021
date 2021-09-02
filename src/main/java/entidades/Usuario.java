package entidades;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
public class Usuario extends EntidadPersistente {

    protected String contrasenia;
    private String nombreUsuario;
    public String email;
    public String bearerToken;

    public Usuario(String email) {
        this.email = email;
    }

    public Usuario() {

    }

    public String getEmail() {
        return email;
    }

    public String setEmail(String emailNuevo) {
        return email = emailNuevo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
