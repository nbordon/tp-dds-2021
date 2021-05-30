package entidades.organizacion;

import entidades.mascotas.CaracteristicaDeMascota;

import java.util.List;

public class Organizacion {
    private String nombre;
    private Integer altoFotoEstandar;
    private Integer anchoFotoEstandar;
    private List<CaracteristicaDeMascota> caracteristicasDeMascotasRequeridas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAltoFotoEstandar() {
        return altoFotoEstandar;
    }

    public void setAltoFotoEstandar(Integer altoFotoEstandar) {
        this.altoFotoEstandar = altoFotoEstandar;
    }

    public Integer getAnchoFotoEstandar() {
        return anchoFotoEstandar;
    }

    public void setAnchoFotoEstandar(Integer anchoFotoEstandar) {
        this.anchoFotoEstandar = anchoFotoEstandar;
    }

    public List<CaracteristicaDeMascota> getCaracteristicasDeMascotasRequeridas() {
        return caracteristicasDeMascotasRequeridas;
    }

    public void setCaracteristicasDeMascotasRequeridas(List<CaracteristicaDeMascota> caracteristicasDeMascotasRequeridas) {
        this.caracteristicasDeMascotasRequeridas = caracteristicasDeMascotasRequeridas;
    }

    public void addCaracteristicasDeMascotasRequerida(CaracteristicaDeMascota ... caracteristicaDeMascotas){
        for(CaracteristicaDeMascota c: caracteristicaDeMascotas){
            this.caracteristicasDeMascotasRequeridas.add(c);
        }

    }

    public void removeCaracteristicaDeMascotasRequerida(CaracteristicaDeMascota caracteristicaDeMascota){
        this.caracteristicasDeMascotasRequeridas.remove(caracteristicaDeMascota);
    }
}
