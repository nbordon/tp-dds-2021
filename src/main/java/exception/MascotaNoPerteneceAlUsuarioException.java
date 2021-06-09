package exception;

public class MascotaNoPerteneceAlUsuarioException extends Exception {

    private static final String errorMsg = "Esta mascota no pertenece al usuario";

    public MascotaNoPerteneceAlUsuarioException() {
        super(errorMsg);
    }
}