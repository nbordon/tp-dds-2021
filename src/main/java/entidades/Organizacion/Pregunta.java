package entidades.Organizacion;

import java.util.List;

public class Pregunta {
    private String descripcion;
    private List<String> valor;


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

