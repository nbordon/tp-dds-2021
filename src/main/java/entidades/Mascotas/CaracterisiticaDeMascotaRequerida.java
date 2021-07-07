package entidades.Mascotas;

import entidades.Organizacion.Pregunta;
import entidades.Organizacion.Respuesta;

import java.util.ArrayList;
import java.util.List;

public class CaracterisiticaDeMascotaRequerida extends Pregunta {
    public CaracterisiticaDeMascotaRequerida(){
        super.setValor(new ArrayList<>());
    }

    public CaracteristicaDeMascota contestar(String unValor) {
        if(!super.getValor().contains(unValor)){
            throw new RuntimeException("Valor no valido para la pregunta");
        }
        List<String> respuesta = new ArrayList<>();
        respuesta.add(unValor);
        return new CaracteristicaDeMascota(this,respuesta);
    }
}
