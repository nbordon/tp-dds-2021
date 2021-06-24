package ValidadorContrasenia;

import exception.VerificadorException;

public interface ValidadorContraseniaInterface {

    void validar(String password) throws VerificadorException;
}
