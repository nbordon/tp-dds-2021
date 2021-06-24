package entidades.Mascotas;

import entidades.Persona;
import exception.CaracteristicaRequeridaException;

public class MascotaBuilder {
    private Mascota mascota;

    private MascotaBuilder(){
        this.mascota = new Mascota();
    }

    public static MascotaBuilder crear(){
        return new MascotaBuilder();
    }

    public MascotaBuilder conNombre(String nombre){
        this.mascota.setNombre(nombre);
        return this;
    }

    public MascotaBuilder conApodo(String apodo){
        this.mascota.setApodo(apodo);
        return this;
    }

    public MascotaBuilder conEdadAproximada(Integer edad){
        this.mascota.setEdadAproximada(edad);
        return this;
    }

    public MascotaBuilder conSexo(String sexo){
        this.mascota.setSexo(sexo);
        return  this;
    }

    public MascotaBuilder conDescripcionFisica(String descripcion){
        this.mascota.getDescripcionFisica().add(descripcion);
        return this;
    }

    public MascotaBuilder conCaracteristicasDeMascota(CaracteristicaDeMascota caracteristicaDeMascotas){
        this.mascota.getCaracteristicas().add(caracteristicaDeMascotas);
        return this;
    }

    public MascotaBuilder conFotosUrl(String fotos){
        this.mascota.getFotosUrl().add(fotos);
        return this;
    }

    public MascotaBuilder conEstado(EstadoMascota estadoMascota){
        this.mascota.setEstado(estadoMascota);
        return this;
    }

    public MascotaBuilder conTipoDeMascota(TipoMascota tipoMascota){
        this.mascota.setTipoMascota(tipoMascota);
        return this;
    }

    public MascotaBuilder conPersona(Persona persona){
        this.mascota.setDuenio(persona);
        return this;
    }

    public Mascota construir() throws CaracteristicaRequeridaException {
        for(CaracteristicaDeMascota unaCaracteristica: this.mascota.getCaracteristicas()){
            if(unaCaracteristica.getValor().isEmpty()){
                throw new CaracteristicaRequeridaException();
            }
        }
        return this.mascota;
    }
}
