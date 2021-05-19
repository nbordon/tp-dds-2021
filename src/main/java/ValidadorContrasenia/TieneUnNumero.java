package ValidadorContrasenia;

import exception.VerificadorException;

public class TieneUnNumero implements ValidadorContraseniaInterface {

    @Override
    public void validar(String password) throws VerificadorException {
        char[] caracteres = password.toCharArray();
        for (char c : caracteres) {
            if (Character.isDigit(c)) {
                return;
            }
        }
        throw new VerificadorException(VerificadorException.TIENE_UN_NUMERO_EXCEPTION);
    }

}
