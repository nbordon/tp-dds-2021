package entidades.Organizacion;

import java.util.List;

public class Respuesta {

    private Pregunta preguntaALaQuePertenece;
    private List<String> valor;

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


}