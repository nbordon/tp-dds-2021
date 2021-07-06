package entidades.Organizacion;

import java.util.List;

public class Pregunta {
    private String descripcion;
    private List<String> valor;


    public Respuesta contestar(){
        return new Respuesta(this,valor);

    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

