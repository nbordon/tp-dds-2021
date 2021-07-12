package entidades.Mascotas;

import entidades.Organizacion.Respuesta;

public class CaracteristicaDeMascota extends Respuesta {

    public CaracteristicaDeMascota(){
        super();
    }

    public CaracteristicaDeMascota(CaracterisiticaDeMascotaRequerida caracterisiticaDeMascotaRequerida, String respuestas){
        super(caracterisiticaDeMascotaRequerida,respuestas);
    }
}