package entidades.Organizacion;

import java.util.ArrayList;
import java.util.List;

public class Respuesta {


    private Pregunta preguntaALaQuePertenece;
    private String valor;

    public Respuesta(Pregunta preguntaALaQuePertenece, String valor) {
        this.preguntaALaQuePertenece = preguntaALaQuePertenece;
        this.valor=valor;
    }

    public Pregunta getPreguntaALaQuePertenece() {
        return preguntaALaQuePertenece;
    }

    public void setPreguntaALaQuePertenece(Pregunta preguntaALaQuePertenece) {
        this.preguntaALaQuePertenece = preguntaALaQuePertenece;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}

