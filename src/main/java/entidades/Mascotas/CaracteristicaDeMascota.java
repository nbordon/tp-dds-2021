package entidades.Mascotas;

import entidades.Organizacion.Respuesta;

import java.util.ArrayList;
import java.util.List;

public class CaracteristicaDeMascota extends Respuesta {


    //TODO Tira error porque no puede inferir el tipo del array
    public CaracteristicaDeMascota(){
        super.setValor(new ArrayList<>());
    }

    public CaracteristicaDeMascota(CaracterisiticaDeMascotaRequerida caracterisiticaDeMascotaRequerida) {
        super(caracterisiticaDeMascotaRequerida,new ArrayList<>());
    }

    public CaracteristicaDeMascota(CaracterisiticaDeMascotaRequerida caracterisiticaDeMascotaRequerida, List<String> respuestas){
        super(caracterisiticaDeMascotaRequerida,respuestas);
    }
}