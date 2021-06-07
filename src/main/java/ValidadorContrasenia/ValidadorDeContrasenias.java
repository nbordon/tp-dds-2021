package ValidadorContrasenia;

import exception.VerificadorException;

import java.util.Arrays;
import java.util.List;

public class ValidadorDeContrasenias {
    private static final List<ValidadorContraseniaInterface> validadores = Arrays.asList(
            new CumpleLargoMinimoYMaximo(),
            new EsDeLas10kPeores(),
            new TieneCaracteresRepetidos(),
            new TieneUnaMayuscula(),
            new TieneUnNumero()
    );

    public static void validarPassword(String password) throws VerificadorException {
        for (ValidadorContraseniaInterface validadorPassword : validadores) {
            validadorPassword.validar(password);
        }
    }

}
