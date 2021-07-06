package entidades.Organizacion;

import Api.services.entities.Ubicacion;
import entidades.Mascotas.CaracteristicaDeMascota;
import entidades.UsuarioVoluntario;
import publicaciones.IntencionDeAdopcion;
import publicaciones.Publicacion;
import publicaciones.PublicacionIntencionDeAdopcion;
import publicaciones.PublicacionMascotaEnAdopcion;
import publicaciones.PublicacionMascotaEncontradaSinChapita;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Organizacion {
    private String nombre;
    private Integer altoFotoEstandar;
    private Integer anchoFotoEstandar;
    private List<CaracteristicaDeMascota> caracteristicasDeMascotasRequeridas = new ArrayList<CaracteristicaDeMascota>();
    private List<UsuarioVoluntario> voluntariosAprobados;
    private Ubicacion ubicacion;
    private List<PreguntasAdopcion>preguntasRequeridasAdopcion;
    private  List<Publicacion> publicacionesEnEsperaDeAprobacion;
    private  List<Publicacion> publicacionesInteresDeAdopcion;
    //private List<Publicacion>publicacionesAprobadas;

    private ContenedorPublicaciones contenedorPublicaciones;

    public List<PublicacionMascotaEnAdopcion> getPublicacionesAprobadasMascotaEnAdopcion(){
        return contenedorPublicaciones.publicacionesAprobadasMascotaEnAdopcion();
    }

    public List<PublicacionMascotaEncontradaSinChapita> getPublicacionesAprobadasMascotaEncontradaSinChapita(){
        return contenedorPublicaciones.publicacionesAprobadasMascotaEncontradaSinChapita();
    }

    public List<PublicacionIntencionDeAdopcion> getPublicacionesAprobadasIntencionDeAdopcion(){
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

    public List<CaracteristicaDeMascota> getCaracteristicasDeMascotasRequeridas() {
        return caracteristicasDeMascotasRequeridas;
    }

    public List<PreguntasAdopcion> getPreguntasRequeridasAdopcion(){
        return preguntasRequeridasAdopcion;
    }

    public void setCaracteristicasDeMascotasRequeridas(List<CaracteristicaDeMascota> caracteristicasDeMascotasRequeridas) {
        this.caracteristicasDeMascotasRequeridas = caracteristicasDeMascotasRequeridas;
    }

    public Ubicacion getUbicacion() {
        return this.ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    // TODO: ver cual de estas 3 usamos y cual borramos, no tiene mucho sentido tener las 3 pero las deje por el test
    public void addCaracteristicaDeMascotasRequerida(CaracteristicaDeMascota caracteristica) {
        this.caracteristicasDeMascotasRequeridas.add(caracteristica);
    }

    public void addCaracteristicaDeMascotasRequerida(CaracteristicaDeMascota... caracteristicas) {
        this.caracteristicasDeMascotasRequeridas.addAll(Arrays.stream(caracteristicas).collect(Collectors.toList()));
    }

    public void addCaracteristicaDeMascotasRequerida(List<CaracteristicaDeMascota> caracteristicas) {
        this.caracteristicasDeMascotasRequeridas.addAll(caracteristicas);
    }

    public void removeCaracteristicaDeMascotasRequerida(CaracteristicaDeMascota caracteristicaDeMascota) {
        this.caracteristicasDeMascotasRequeridas.remove(caracteristicaDeMascota);
    }

    public void agregarPublicacionEnEsperaDeAprobacion(PublicacionMascotaEnAdopcion publicacionMascotaEnAdopcion) {
       contenedorPublicaciones.agregarPublicacionEnEsperaDeAprobacion(publicacionMascotaEnAdopcion);
    }

    public void agregarPublicacionIntencionDeAdopcion(IntencionDeAdopcion intencionDeAdopcion) {
        publicacionesInteresDeAdopcion.add(intencionDeAdopcion);
    }

}