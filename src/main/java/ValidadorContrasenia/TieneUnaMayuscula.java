package ValidadorContrasenia;

import exception.VerificadorException;

public class TieneUnaMayuscula implements ValidadorContraseniaInterface {

    @Override
    public void validar(String password) throws VerificadorException {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return;
            }
        }
        throw new VerificadorException(VerificadorException.TIENE_MAYUSCULA_EXCEPTION);
    }
}
