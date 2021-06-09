package entidades;

public class Usuario {

    protected String contrasenia;
    private String nombreUsuario;
    public String email;
    public String bearerToken;

    public Usuario(String email) {
        this.email = email;
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
