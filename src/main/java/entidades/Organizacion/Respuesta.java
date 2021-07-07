package entidades.Organizacion;

import java.util.ArrayList;
import java.util.List;

public class Respuesta {


private Pregunta preguntaALaQuePertenece;
    private List<String> valor;

    public Respuesta(){
        this.valor = new ArrayList<>();
    }

    public Respuesta(Pregunta preguntaALaQuePertenece,List<String>valor) {
        this.preguntaALaQuePertenece = preguntaALaQuePertenece;
        this.valor=valor;
    }

    public Pregunta getPreguntaALaQuePertenece() {
        return preguntaALaQuePertenece;
    }

    public void setPreguntaALaQuePertenece(Pregunta preguntaALaQuePertenece) {
        this.preguntaALaQuePertenece = preguntaALaQuePertenece;
    }

    public List<String> getValor() {
        return valor;
    }

    public void setValor(List<String> valor) {
        this.valor = valor;
    }
}

