package entidades.Organizacion;

import entidades.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class Pregunta extends EntidadPersistente {
    private String descripcion;
    @Transient
    private List<String> valor;

    //TODO: delete this, it is used in a test
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

