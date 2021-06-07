package entidades;

import entidades.Mascotas.EstadoMascota;
import entidades.Mascotas.TipoMascota;

import java.util.List;

public class Mascota {

    private final String nombre;
    private final String apodo;
    private final int edadAproximada;
    private final String sexo;
    private final List<String> descripcionFisica;
    private final List<CaracteristicaDeMascota> caracteristicas;
    private final List<String> fotosUrl;
    private EstadoMascota estado;
    private final TipoMascota tipoMascota;
    private final Persona duenio;


    public Mascota(String nombre, String apodo, int edadAproximada, String sexo, List<String> descripcionFisica, List<CaracteristicaDeMascota> caracteristicas, List<String> fotosUrl, String codigoQr, EstadoMascota estado, TipoMascota tipoMascota, Persona duenio) {
        this.nombre = nombre;
        this.apodo = apodo;
        this.edadAproximada = edadAproximada;
        this.sexo = sexo;
        this.descripcionFisica = descripcionFisica;
        this.caracteristicas = caracteristicas;
        this.fotosUrl = fotosUrl;
        this.estado = estado;
        this.tipoMascota = tipoMascota;
        this.duenio = duenio;

    }

    public EstadoMascota getEstado() {
        return estado;
    }

    public void setEstado(EstadoMascota estado) {
        this.estado = estado;
    }

}


