package domain.entities.Mascotas;


import domain.entities.Organizacion.Pregunta;
import domain.entities.Organizacion.Respuesta;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("caracteristica_mascota")
public class CaracteristicaDeMascota extends Respuesta {

    public CaracteristicaDeMascota(){
        super();
    }

    public CaracteristicaDeMascota(Pregunta preguntaALaQuePertenece , String unValor){super();}

    public CaracteristicaDeMascota(CaracterisiticaDeMascotaRequerida caracterisiticaDeMascotaRequerida, String respuestas){
        super(caracterisiticaDeMascotaRequerida,respuestas);
    }
}