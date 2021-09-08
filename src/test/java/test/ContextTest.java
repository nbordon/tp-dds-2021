package test;

import db.EntityManagerHelper;
import entidades.Contacto;
import entidades.Direccion;
import entidades.EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.EstrategiasNotificacion.EstrategiaEmail.EstrategiaDeEmail;
import entidades.EstrategiasNotificacion.EstrategiaWhatsApp.EstrategiaDeWhatsApp;
import entidades.InformacionPersonal;
import entidades.Mascotas.CaracterisiticaDeMascotaRequerida;
import entidades.Mascotas.CaracteristicaDeMascota;
import entidades.Mascotas.Mascota;
import entidades.Organizacion.Administrador;
import entidades.Organizacion.Pregunta;
import entidades.Persona;
import org.junit.Ignore;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	@Test @Ignore
	public void contextUp() {
		assertNotNull(entityManager());
	}
	@Test @Ignore
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}

	@Test @Ignore
	public void persistirEstrategiasNotificacion(){
		InformacionPersonal ip = new InformacionPersonal();
		List<EstrategiaDeNotificacion> estrategias =new ArrayList<> ();
		estrategias.add(new EstrategiaDeEmail());
		estrategias.add(new EstrategiaDeWhatsApp());
		ip.setApellido("Bori");
		ip.setEmail("gabo@gmail.com");
		ip.setNroDocumento(123435667);
		ip.setFormaComunicacion(estrategias);
		Contacto contacto = new Contacto();
		contacto.setNombre("Pedro");
		contacto.setApellido("Perez");
		contacto.setEmail("pedro@gmail.com");
		contacto.setNumeroDeTelefono("11998822");

		Contacto contacto2 = new Contacto();
		contacto2.setNombre("Lionel");
		contacto2.setApellido("Messi");
		contacto2.setEmail("lionel.messi@gmail.com");
		contacto2.setNumeroDeTelefono("101010101010");
		List<Contacto> contactosIp = new ArrayList<> ();
		contactosIp.add(contacto);
		contactosIp.add(contacto2);
		ip.setContactos(contactosIp);

		List<EstrategiaDeNotificacion> estrategiasNotif = new ArrayList<>();
		estrategiasNotif.add(new EstrategiaDeWhatsApp());
		estrategiasNotif.add(new EstrategiaDeEmail());
		ip.setFormaComunicacion(estrategiasNotif);

		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().persist(contacto);
		EntityManagerHelper.getEntityManager().persist(contacto2);
		EntityManagerHelper.getEntityManager().persist(ip);
		EntityManagerHelper.commit();
	}

	@Test @Ignore
	public void persistirMascotaConCaracteristica(){
		Mascota m = new Mascota();
		m.setApodo("huesos");
		m.setNombre("Ayudante de santa");
		m.setDescripcionFisica("El perro de Bart Simpson");

		//Se crea una caracteristica de mascota nueva y se le agregan los posibles valores
		CaracterisiticaDeMascotaRequerida carReq = new CaracterisiticaDeMascotaRequerida();
		carReq.setDescripcion("Esta castrado");
		List<String> valoresRespuesta = new ArrayList<>();
		valoresRespuesta.add("Si");
		valoresRespuesta.add("No");
		carReq.setValor(valoresRespuesta);


		CaracteristicaDeMascota c1 = new CaracteristicaDeMascota();
		c1.setPreguntaALaQuePertenece(carReq);
		c1.setValor(carReq.getValor().get(0));

		List<CaracteristicaDeMascota> caracteristicasC1 = new ArrayList<>();
		caracteristicasC1.add(c1);
		m.setCaracteristicas(caracteristicasC1);
		EntityManagerHelper.beginTransaction();

		EntityManagerHelper.getEntityManager().persist(carReq);
		EntityManagerHelper.getEntityManager().persist(c1);
		EntityManagerHelper.getEntityManager().persist(m);
		EntityManagerHelper.commit();
	}
}