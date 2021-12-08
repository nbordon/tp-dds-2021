package domain.entities;

import exception.VerificadorException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
public class Password extends EntidadPersistente {

    private final String password;
    private LocalDate fechaModificacion;

    public Password(String password) throws VerificadorException {
        String passwordEncoded = encriptarPassword(password);
        this.password = passwordEncoded;
        this.fechaModificacion = LocalDate.now();
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacionNueva) {
        fechaModificacion = fechaModificacionNueva;
    }

    public String encriptarPassword(String passwordTextoPlano) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(passwordTextoPlano);
    }

}
