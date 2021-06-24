package exception;

public class CaracteristicaRequeridaException extends Exception{
    private static final String errorMsg = "Característica requerida no completada";

    public CaracteristicaRequeridaException(){super(errorMsg);}
}
