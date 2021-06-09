package ValidadorContrasenia;

import exception.VerificadorException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EsDeLas10kPeores implements ValidadorContraseniaInterface {

    public static final String RUTA_10KPEORES = "src/main/java/resources/10k-most-common.txt";

    @Override
    public void validar(String password) throws VerificadorException {

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(RUTA_10KPEORES));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains(password)) {
                    throw new VerificadorException(VerificadorException.ES_DE_LAS_10KPEORES_EXCEPTION);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
