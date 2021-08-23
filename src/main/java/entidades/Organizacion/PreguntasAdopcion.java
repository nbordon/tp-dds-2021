package entidades.Organizacion;

import java.util.List;

public class PreguntasAdopcion extends Pregunta{

    private List<Pregunta> preguntasObligatorias;

    public PreguntasAdopcion(List<Pregunta> preguntasObligatorias) {
        this.preguntasObligatorias = preguntasObligatorias;
    }
}

