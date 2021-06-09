package Api.services.entities;

public class Usuario {
    public String email;
    public  String bearerToken;

    public Usuario(String email) {
        this.email = email;
    }

    public String getEmail(){return  email;}
    public String setEmail(String emailNuevo){return email = emailNuevo;}

}