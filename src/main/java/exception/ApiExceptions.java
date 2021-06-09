package exception;

public class ApiExceptions extends Exception {
    public ApiExceptions(String errorMessage){ super (errorMessage); }

    public static String EXCEPTION_REGISTER_CONEXION = "Se produjo un problema de conexion con el servidor";
    public static String EXEPTION_REGISTER_RESPONSE = "Se produjo un problema decodificando la respuesta";
}
