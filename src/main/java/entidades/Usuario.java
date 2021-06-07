package entidades;

public class Usuario {

    private String nombreUsuario;
    protected String contrasenia;

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombreUsuario(){

        return nombreUsuario;
    }
    public String getContrasenia(){

        return contrasenia;
    }  }
