package entidades.Mascotas;

import entidades.Persona;

import java.util.ArrayList;
import java.util.List;

public class Mascota {

    private String nombre;
    private String apodo;
    private int edadAproximada;
    private String sexo;
    private List<String> descripcionFisica;
    private List<CaracteristicaDeMascota> caracteristicas;
    private List<String> fotosUrl;
    private EstadoMascota estado;
    private TipoMascota tipoMascota;
    private Persona duenio;


    public Mascota(){
        this.descripcionFisica = new ArrayList<>();
        this.caracteristicas = new ArrayList<>();
        this.fotosUrl = new ArrayList<>();
    };

    public Mascota(String nombre, String apodo, int edadAproximada,String sexo, List<String>descripcionFisica, List<CaracteristicaDeMascota> caracteristicas, List<String>fotosUrl,String codigoQr,EstadoMascota estado,TipoMascota tipoMascota,Persona duenio)
    {
        this.nombre=nombre;
        this.apodo= apodo;
        this.edadAproximada=edadAproximada;
        this.sexo=sexo;
        this.descripcionFisica=descripcionFisica;
        this.caracteristicas=caracteristicas;
        this.fotosUrl=fotosUrl;
        this.estado=estado;
        this.tipoMascota= tipoMascota;
        this.duenio=duenio;
    }

    public EstadoMascota getEstado() {
        return estado;
    }

    public void setEstado(EstadoMascota estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public int getEdadAproximada() {
        return edadAproximada;
    }

    public String getSexo() {
        return sexo;
    }

    public List<String> getDescripcionFisica() {
        return descripcionFisica;
    }

    public List<CaracteristicaDeMascota> getCaracteristicas() {
        return caracteristicas;
    }

    public List<String> getFotosUrl() {
        return fotosUrl;
    }

    public TipoMascota getTipoMascota() {
        return tipoMascota;
    }

    public Persona getDuenio() {
        return duenio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public void setEdadAproximada(int edadAproximada) {
        this.edadAproximada = edadAproximada;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setDescripcionFisica(List<String> descripcionFisica) {
        this.descripcionFisica = descripcionFisica;
    }

    public void setCaracteristicas(List<CaracteristicaDeMascota> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public void setFotosUrl(List<String> fotosUrl) {
        this.fotosUrl = fotosUrl;
    }

    public void setTipoMascota(TipoMascota tipoMascota) {
        this.tipoMascota = tipoMascota;
    }

    public void setDuenio(Persona duenio) {
        this.duenio = duenio;
    }
}
