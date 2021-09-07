package entidades.Organizacion;

import Api.services.entities.Ubicacion;
import entidades.EntidadPersistente;
import entidades.Mascotas.CaracterisiticaDeMascotaRequerida;
import entidades.UsuarioVoluntario;
import entidades.publicaciones.*;
import entidades.publicaciones.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Entity
@Table(name = "organizacion")
public class Organizacion extends EntidadPersistente {
    private String nombre;
    private Integer altoFotoEstandar;
    private Integer anchoFotoEstandar;
    @OneToMany
    @JoinTable(name="caract_req_org")
    private List<CaracterisiticaDeMascotaRequerida> caracteristicasDeMascotasRequeridas;
    @OneToMany
    @JoinTable(name = "voluntario_organizacion")
    private List<UsuarioVoluntario> voluntariosAprobados;
    @OneToOne
    private Ubicacion ubicacion;
    @OneToMany
    private List<PreguntasAdopcion> preguntasRequeridasAdopcion;
    //TODO: como se carga la lista o el contenedor cuando se trae la organizacion de la db
    @Transient
    private List<Publicacion> publicacionesEnEsperaDeAprobacion;
    @Transient
    private ContenedorPublicaciones contenedorPublicaciones;

    public Organizacion() {
        caracteristicasDeMascotasRequeridas = new ArrayList<>();
        preguntasRequeridasAdopcion = new ArrayList<>();
        publicacionesEnEsperaDeAprobacion = new ArrayList<>();
    }

    public List<PublicacionMascotaEnAdopcion> getPublicacionesAprobadasMascotaEnAdopcion() {
        return contenedorPublicaciones.publicacionesAprobadasMascotaEnAdopcion();
    }

    public List<PublicacionMascotaEncontradaSinChapita> getPublicacionesAprobadasMascotaEncontradaSinChapita() {
        return contenedorPublicaciones.publicacionesAprobadasMascotaEncontradaSinChapita();
    }

    public List<PublicacionIntencionDeAdopcion> getPublicacionesAprobadasIntencionDeAdopcion() {
        return contenedorPublicaciones.publicacionesAprobadasIntencionDeAdopcion();
    }

    public List<UsuarioVoluntario> getUsuariosAprobados() {
        return voluntariosAprobados;
    }

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

    public List<CaracterisiticaDeMascotaRequerida> getCaracteristicasDeMascotasRequeridas() {
        return caracteristicasDeMascotasRequeridas;
    }

    public List<PreguntasAdopcion> getPreguntasRequeridasAdopcion() {
        return preguntasRequeridasAdopcion;
    }

    public void setCaracteristicasDeMascotasRequeridas(List<CaracterisiticaDeMascotaRequerida> caracteristicasDeMascotasRequeridas) {
        this.caracteristicasDeMascotasRequeridas = caracteristicasDeMascotasRequeridas;
    }

    public Ubicacion getUbicacion() {
        return this.ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    // TODO: ver cual de estas 3 usamos y cual borramos, no tiene mucho sentido tener las 3 pero las deje por el test
    public void addCaracteristicaDeMascotasRequerida(CaracterisiticaDeMascotaRequerida caracteristica) {
        this.caracteristicasDeMascotasRequeridas.add(caracteristica);
    }

    public void addCaracteristicaDeMascotasRequerida(CaracterisiticaDeMascotaRequerida... caracteristicas) {
        this.caracteristicasDeMascotasRequeridas.addAll(Arrays.stream(caracteristicas).collect(Collectors.toList()));
    }

    public void addCaracteristicaDeMascotasRequerida(List<CaracterisiticaDeMascotaRequerida> caracteristicas) {
        this.caracteristicasDeMascotasRequeridas.addAll(caracteristicas);
    }

    public void removeCaracteristicaDeMascotasRequerida(CaracterisiticaDeMascotaRequerida caracteristicaDeMascota) {
        this.caracteristicasDeMascotasRequeridas.remove(caracteristicaDeMascota);
    }

    public void agregarPublicacionEnEsperaDeAprobacion(PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion) {
        contenedorPublicaciones.agregarPublicacionEnEsperaDeAprobacion(publicacionMascotaEnAdopcion);
    }

    public void agregarPublicacionIntencionDeAdopcion(PublicacionIntencionDeAdopcion publicacionIntencionDeAdopcion) {
        this.contenedorPublicaciones.agregarPublicacionIntecnioDeAdopcion(publicacionIntencionDeAdopcion);

    }

    public ContenedorPublicaciones getContenedorPublicaciones() {
        return contenedorPublicaciones;
    }

    public void setContenedorPublicaciones(ContenedorPublicaciones contenedorPublicaciones) {
        this.contenedorPublicaciones = contenedorPublicaciones;
    }

    public List<Publicacion> getPublicacionesMascotaEncontrada(EstadoPublicacion estadoPublicacion) {
        return this.contenedorPublicaciones.getPublicacionesMascotaEncontrada(estadoPublicacion);
    }

    public List<PublicacionIntencionDeAdopcion> publicacionesIntencionDeAdopcion(EstadoPublicacion estado){
        return this.contenedorPublicaciones.publicacionesIntencionDeAdopcion(estado);
    }

    public List<Publicacion> getPublicacionesEnEsperaDeAprobacion() {
        return this.contenedorPublicaciones.getPublicacionesEnEsperaDeAprobacion();
    }
}