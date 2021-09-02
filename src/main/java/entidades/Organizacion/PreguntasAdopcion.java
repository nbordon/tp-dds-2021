package entidades.Organizacion;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
@Entity
public class PreguntasAdopcion extends Pregunta{
    //TODO: ver si esta ok esta relacion
    @OneToMany
    private List<Pregunta> preguntasObligatorias;

    public PreguntasAdopcion(List<Pregunta> preguntasObligatorias) {
        this.preguntasObligatorias = preguntasObligatorias;
    }

    public PreguntasAdopcion() {

    }
}

