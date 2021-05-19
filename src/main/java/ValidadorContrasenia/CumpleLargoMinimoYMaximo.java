package ValidadorContrasenia;

import exception.VerificadorException;

public class CumpleLargoMinimoYMaximo implements ValidadorContraseniaInterface {

    @Override
    public void validar(String password) throws VerificadorException {
        if (password.length() < VerificadorException.LONGITUD_MINIMA || password.length() > VerificadorException.LONGITUD_MAXIMA) {
            throw new VerificadorException
                    (VerificadorException.LARGO_DEL_STRING_EXCEPTION);
        }
    }

}
