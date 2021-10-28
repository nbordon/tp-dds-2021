package ValidadorContrasenia;

import domain.entities.Password;
import exception.VerificadorException;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class EstaCaduca {

    public void validar(Password password) throws VerificadorException {
        //Considero que una contraseña caduco si ya tiene un año desde su creacion
        //este metodo lo voy a usar cuando creemos un metodo de logueo al sistema, no al momento
        //de registrar una nueva contraseña ya que es redundante ☺
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaContra = password.getFechaModificacion();
        long diasPasados = DAYS.between(fechaContra, fechaActual);
        if (diasPasados > 365) {
            throw new VerificadorException(VerificadorException.ESTA_CADUCA_EXCEPTION);
        }
    }
}
