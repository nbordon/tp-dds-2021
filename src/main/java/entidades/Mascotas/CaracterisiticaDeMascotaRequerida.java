package entidades.Mascotas;

import entidades.Organizacion.Pregunta;
import java.util.ArrayList;

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
