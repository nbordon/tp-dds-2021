package entidades.Organizacion;

import entidades.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Respuesta extends EntidadPersistente {

    @ManyToOne
    private Pregunta preguntaALaQuePertenece;
    private String valor;

    public Respuesta(){}

    public Respuesta(Pregunta preguntaALaQuePertenece, String valor) {
        this.preguntaALaQuePertenece = preguntaALaQuePertenece;
        this.valor=valor;
    }

    public Pregunta getPreguntaALaQuePertenece() {
        return preguntaALaQuePertenece;
    }

    public void setPreguntaALaQuePertenece(Pregunta preguntaALaQuePertenece) {
        this.preguntaALaQuePertenece = preguntaALaQuePertenece;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}

