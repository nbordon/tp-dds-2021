package ValidadorContrasenia;

import entidades.Password;
import exception.VerificadorException;

public interface ValidadorContraseniaInterface {
    public void validar(String password) throws VerificadorException;
}
