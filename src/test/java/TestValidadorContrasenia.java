import ValidadorContrasenia.*;
import entidades.Password;
import exception.VerificadorException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDate;

public class TestValidadorContrasenia {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void contraseniaMuyCorta() throws VerificadorException {
        String contrasenia = "Abcde1";

        thrown.expect(VerificadorException.class);
        thrown.expectMessage(VerificadorException.LARGO_DEL_STRING_EXCEPTION);
        new CumpleLargoMinimoYMaximo().validar(contrasenia);
    }

    @Test
    public void contraseniaMuyLarga() throws VerificadorException {
        String contrasenia = "0d4566sd5f4f4sdfsdfas++dsafasdf";

        thrown.expect(VerificadorException.class);
        thrown.expectMessage(VerificadorException.LARGO_DEL_STRING_EXCEPTION);
        new CumpleLargoMinimoYMaximo().validar(contrasenia);
    }

    @Test
    public void contraseniaEsDeLas10KPeores() throws VerificadorException{
        String contrasenia = "123456";

        thrown.expect(VerificadorException.class);
        thrown.expectMessage(VerificadorException.ES_DE_LAS_10KPEORES_EXCEPTION);
        new EsDeLas10kPeores().validar(contrasenia);
    }

    @Test
    public void contraseniaConCaracteresRepetidos() throws VerificadorException {
        String contrasenia = "Aabbbcdd1";

        thrown.expect(VerificadorException.class);
        thrown.expectMessage(VerificadorException.TIENE_CARACTERES_REPETIDOS_EXCEPTION);
        new TieneCaracteresRepetidos().validar(contrasenia);

    }

    @Test
    public void contraseniaNoTieneMayuscula() throws VerificadorException {
        String contrasenia = "abcdefgh1";

        thrown.expect(VerificadorException.class);
        thrown.expectMessage(VerificadorException.TIENE_MAYUSCULA_EXCEPTION);
        new TieneUnaMayuscula().validar(contrasenia);
    }

    @Test
    public void contraseniaNoTieneNumero() throws VerificadorException {
        String contrasenia = "Aabbccdd";

        thrown.expect(VerificadorException.class);
        thrown.expectMessage(VerificadorException.TIENE_UN_NUMERO_EXCEPTION);
        new TieneUnNumero().validar(contrasenia);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void contraseniaValida() throws VerificadorException {
        String contrasenia = "asAAq3434asdfgd";
        ValidadorDeContrasenias.validarPassword(contrasenia);
    }

    @Test
    public void contraseniaEncriptada() throws VerificadorException {
        String contrasenia = "*-_Gabo!1?";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncoded = encoder.encode(contrasenia);
        Password password = new Password("*-_Gabo!1?");
        Assert.assertTrue(encoder.matches("*-_Gabo!1?",password.getPassword()));
    }

    @Test
    public void contraseniaCaduca() throws VerificadorException {
        Password password = new Password("*-_fasd!12?");
        password.setFechaModificacion(LocalDate.now().minusDays(366));
        EstaCaduca validadorEstaCaduca = new EstaCaduca();
        thrown.expect(VerificadorException.class);
        thrown.expectMessage(VerificadorException.ESTA_CADUCA_EXCEPTION);
        validadorEstaCaduca.validar(password);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void contraseniaNoCaduca() throws VerificadorException {
        Password password = new Password("*-_fasd!12?");
        password.setFechaModificacion(LocalDate.now().minusDays(365));
        EstaCaduca validadorEstaCaduca = new EstaCaduca();
        validadorEstaCaduca.validar(password);
    }
}
