package domain.entities.Mascotas;

import domain.entities.EntidadPersistente;
import domain.entities.Persona;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "mascota")
public class Mascota extends EntidadPersistente {

    private String nombre;
    private String apodo;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private String sexo;
    private String descripcionFisica;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "caract_d_mascot")
    private List<CaracteristicaDeMascota> caracteristicas;
    @ElementCollection
    @CollectionTable(name="mascota_fotos_url")
    private List<String> fotosUrl;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_mascota")
    private EstadoMascota estado;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mascota")
    private TipoMascota tipoMascota;
    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona duenio;


    public Mascota(){ ;
        this.caracteristicas = new ArrayList<>();
        this.fotosUrl = new ArrayList<>();
    };

    public Mascota(String nombre, String apodo, LocalDate fechaNacimiento, String sexo, String descripcionFisica, List<CaracteristicaDeMascota> caracteristicas, List<String>fotosUrl, String codigoQr, EstadoMascota estado, TipoMascota tipoMascota, Persona duenio)
    {
        this.nombre=nombre;
        this.apodo= apodo;
        this.fechaNacimiento = fechaNacimiento;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public String getDescripcionFisica() {
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

    public void setFechaNacimiento(LocalDate edadAproximada) {
        this.fechaNacimiento = edadAproximada;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setDescripcionFisica(String descripcionFisica) {
        this.descripcionFisica = descripcionFisica;
    }

    public void setCaracteristicas(List<CaracteristicaDeMascota> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public void setFotosUrl(List<String> fotosUrl) {
        this.fotosUrl = fotosUrl;
    }

    /*public void agregoFoto(String fotoUrl){
        fotosUrl.add(fotoUrl);
    }

     */

    public void setTipoMascota(TipoMascota tipoMascota) {
        this.tipoMascota = tipoMascota;
    }

    public void setDuenio(Persona duenio) {
        this.duenio = duenio;
    }

}
