package ValidadorContrasenia;

import exception.VerificadorException;

public class TieneCaracteresRepetidos implements ValidadorContraseniaInterface {
    @Override
    public void validar(String password) throws VerificadorException {
        //considero que hay caracteres repetidos si el mismo caracter se repite 3 veces
        for (int i = 0; i < (password.length() - 2); i++)
            if (password.charAt(i) == password.charAt(i + 1) && password.charAt(i) == password.charAt(i + 2)) {
                throw new VerificadorException(VerificadorException.TIENE_CARACTERES_REPETIDOS_EXCEPTION);
            }
    }

}
