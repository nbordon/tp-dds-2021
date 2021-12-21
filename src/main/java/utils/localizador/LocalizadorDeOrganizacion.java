package utils.localizador;

import Api.services.entities.Hogar;
import Api.services.entities.Ubicacion;
import domain.entities.Organizacion.Organizacion;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalizadorDeOrganizacion {

    //TODO: deberiamos ver de donde sacamos la lista de organizaciones
    private List<Organizacion> organizaciones ;
    private Repositorio<Organizacion> repoOrganizaciones;
    private Repositorio<Hogar> hogarRepositorio;

    public LocalizadorDeOrganizacion(){
        this.repoOrganizaciones = FactoryRepositorio.get(Organizacion.class);
        this.hogarRepositorio = FactoryRepositorio.get(Hogar.class);
        this.organizaciones = repoOrganizaciones.buscarTodos();
    }

    public Organizacion obtenerOrganizacionMasCercana(Ubicacion ubicacion) {
        return organizaciones
                .stream()
                .min(Comparator.comparing(organizacion -> CalculadorDeDistancia.entre(ubicacion,organizacion.getUbicacion())))
                .get();
    }

    public Organizacion obtenerOrganizacionMasCercana(Ubicacion ubicacion, Double radioMinimo) {
        return organizacionesCercanas(ubicacion, radioMinimo)
                .min(Comparator.comparing(organizacion -> CalculadorDeDistancia.entre(ubicacion,organizacion.getUbicacion())))
                .get();
    }

    public List<Organizacion> obtenerOrganizacionesCercanas(Ubicacion ubicacion, Double radioMinimo) {
        return organizacionesCercanas(ubicacion, radioMinimo).collect(Collectors.toList());
    }

    public Hogar obtenerHogarMasCercano(Ubicacion ubicacion) {
        return hogarRepositorio.buscarTodos()
                .stream()
                .min(Comparator.comparing(hogar -> CalculadorDeDistancia.entre(ubicacion,hogar.getUbicacion())))
                .get();
    }

    private Stream<Organizacion> organizacionesCercanas(Ubicacion ubicacion, Double radioMinimo) {
        return organizaciones
                .stream()
                .filter(organizacion -> CalculadorDeDistancia.entre(ubicacion,organizacion.getUbicacion()) < radioMinimo);
    }

    public void addOrganizacion(Organizacion organizacion) {
        this.organizaciones.add(organizacion);
    }
}
