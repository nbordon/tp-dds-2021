package domain.repositories.TestMascotaData;

import domain.entities.Mascotas.Mascota;
import domain.entities.Mascotas.TipoMascota;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;

import java.util.ArrayList;
import java.util.List;

public class DataMascota {
    private static List<Mascota> mascotas = new ArrayList<>();

    public static List<Mascota> getList(){
            Repositorio<Mascota> mascotaRepositorio = FactoryRepositorio.get(Mascota.class);

            if(mascotas.size() == 0) {

                    Mascota pepe = new Mascota();
                    pepe.setNombre("Pepe");
                    pepe.setApodo("Pepito");
                    pepe.setTipoMascota(TipoMascota.GATO);
                    pepe.setDescripcionFisica("un gato");

                    Mascota messi = new Mascota();
                    messi.setNombre("messi");
                    messi.setApodo("messi");
                    messi.setTipoMascota(TipoMascota.GATO);
                    messi.setDescripcionFisica("algo");

                    Mascota pipi = new Mascota();
                    pipi.setNombre("pipi");
                    pipi.setApodo("a");
                    pipi.setTipoMascota(TipoMascota.PERRO);

                    mascotas.add(pepe);
                    mascotas.add(pipi);
                    mascotas.add(messi);
            }

        return  mascotas;
    }

        public static List<Mascota> getMascotas() {
                return mascotas;
        }
}
