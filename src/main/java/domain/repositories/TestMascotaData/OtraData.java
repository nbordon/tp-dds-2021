package domain.repositories.TestMascotaData;

import domain.entities.EntidadPersistente;
import domain.entities.Mascotas.Mascota;
import domain.entities.Mascotas.TipoMascota;
import domain.repositories.Repositorio;
import domain.repositories.factories.FactoryRepositorio;

import java.util.ArrayList;
import java.util.List;

public class OtraData { //De esta forma lo tienen en el seminario pero no levanta nada
    private static List<Mascota> mascotas = new ArrayList<>();

    public static List<EntidadPersistente> getList(){
        if(mascotas.size() == 0) {
            Repositorio<Mascota> repositorio = FactoryRepositorio.get(Mascota.class);

            Mascota pepe = new Mascota();
            pepe.setNombre("Pepe");
            pepe.setApodo("Pepito");
            pepe.setTipoMascota(TipoMascota.PERRO);
            pepe.setDescripcionFisica("Muy lindo");

            Mascota jose = new Mascota();
            pepe.setNombre("jose");
            pepe.setApodo("jochi");
            pepe.setTipoMascota(TipoMascota.PERRO);
            pepe.setDescripcionFisica("Muy hermoso");

            Mascota messi = new Mascota();
            pepe.setNombre("messi");
            pepe.setApodo("messirve");
            pepe.setTipoMascota(TipoMascota.GATO);
            pepe.setDescripcionFisica("Muy bueno");

            mascotas.add(pepe);
            mascotas.add(jose);
            mascotas.add(messi);
        }

        return  (List<EntidadPersistente>)(List<?>) mascotas ;

    }
}
