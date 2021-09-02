package entidades.Organizacion;

import entidades.EntidadPersistente;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_de_pregunta")
public class Pregunta extends EntidadPersistente {
    private String descripcion;
    //TODO: agregar converter de list string
    @Transient
    private List<String> valor;

    //TODO: ver como manejamos esta relacion.
    public void setId(Integer id) {  }

    public Respuesta contestar(String unValor){
        return new Respuesta(this,unValor);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getValor() {
        return valor;
    }

    public void setValor(List<String> valor) {
        this.valor = valor;
    }
}

