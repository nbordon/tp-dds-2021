package domain.entities.Organizacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;
@Entity
@DiscriminatorValue("pregunta_adopcion")
public class PreguntasAdopcion extends Pregunta {
    public PreguntasAdopcion() {
    }
}

