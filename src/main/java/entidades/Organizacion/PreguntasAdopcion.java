package entidades.Organizacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
@Entity
@DiscriminatorValue("pregunta_adopcion")
public class PreguntasAdopcion extends Pregunta {
    public PreguntasAdopcion() {
    }
}

