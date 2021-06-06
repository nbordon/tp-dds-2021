package entidades;

import java.util.List;

public class Mascota{

        private String nombre;
        private String apodo;
        private int edadAproximada;
        private String sexo;
        private List<String> descripcionFisica;
        private List<CaracteristicaDeMascota> caracteristicas;
        private List<String>fotosUrl;
        private EstadoMascota estado;
        private TipoMascota tipoMascota;
        private Persona duenio;


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
        public EstadoMascota getEstado(){
        return estado;
        }

    public void setEstado(EstadoMascota estado) {
        this.estado=estado;
    }

}


