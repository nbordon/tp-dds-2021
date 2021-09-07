package entidades.Mascotas;

import entidades.Organizacion.Pregunta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("caracteristica_mascota_requerida")
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
