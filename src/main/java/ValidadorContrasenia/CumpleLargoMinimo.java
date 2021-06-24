package ValidadorContrasenia;

import exception.VerificadorException;

public class CumpleLargoMinimo implements ValidadorContraseniaInterface {

    @Override
    public void validar(String password) throws VerificadorException {
        if (password.length() < VerificadorException.LONGITUD_MINIMA) {
            throw new VerificadorException
                    (VerificadorException.LARGO_DEL_STRING_EXCEPTION);
        }
    }

}
