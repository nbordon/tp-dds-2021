package test;

import domain.entities.Mascotas.Mascota;
import domain.entities.Persona;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestRegistroDeMascotas {
    Persona pepe;
    Mascota firulais,tonny;

    @Before
    public void init(){
        pepe = new Persona("pepe@gmail.com");
        pepe.setMascotas(new ArrayList<>());

        firulais = new Mascota();
        tonny = new Mascota();

        firulais.setNombre("firulais");
        tonny.setNombre("");
    }

    @Test
    public void registrarUnaMascota(){
        pepe.registrarMascota(firulais);
        Assert.assertEquals(1,pepe.getMascotas().size());
    }

    @Test
    public void registrarDosMascotas(){
        pepe.registrarMascota(firulais);
        pepe.registrarMascota(tonny);

        Assert.assertEquals(2,pepe.getMascotas().size());
    }
}
