package domain.entities.Mascotas;

import domain.entities.Organizacion.Pregunta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("caract_mascot_req")
public class CaracterisiticaDeMascotaRequerida extends Pregunta {
    public CaracterisiticaDeMascotaRequerida(){
        super.setValor(new ArrayList<>());
    }

    public CaracteristicaDeMascota contestar(String unValor) {
        if(!super.getValor().contains(unValor)){
            throw new RuntimeException("Valor no valido para la pregunta");
        }

        CaracteristicaDeMascota caracteristicaDeMascota = new CaracteristicaDeMascota();
        caracteristicaDeMascota.setValor(unValor);
        return caracteristicaDeMascota;
    }
}
