package exception;

public class VerificadorException extends Exception {
    public static final int LONGITUD_MINIMA = 8;
    public static final int LONGITUD_MAXIMA = 30;

    public VerificadorException(String errorMessage) {
        super(errorMessage);
    }

    public static String ES_DE_LAS_10KPEORES_EXCEPTION = "La contrasenia es demasiado debil, elija otra";
    public static String ESTA_CADUCA_EXCEPTION = "La contraseña caducó, cree una nueva contraseña";
    public static String TIENE_CARACTERES_REPETIDOS_EXCEPTION = "La contrasenia tiene muchos caracteres repetidos";
    public static String TIENE_MAYUSCULA_EXCEPTION = "La contrasenia debe tener al menos una mayuscula";
    public static String TIENE_UN_NUMERO_EXCEPTION = "La contrasenia debe contener al menos un numero";
    public static String LARGO_DEL_STRING_EXCEPTION = "Largo de la contrasenia invalido, debe tener al menos " + LONGITUD_MINIMA + " caracteres de largo y como maximo "+LONGITUD_MAXIMA+"";

}
