package test;

import db.EntityManagerHelper;
import entidades.Direccion;
import entidades.EstrategiasNotificacion.EstrategiaDeNotificacion;
import entidades.EstrategiasNotificacion.EstrategiaEmail.EstrategiaDeEmail;
import entidades.EstrategiasNotificacion.EstrategiaWhatsApp.EstrategiaDeWhatsApp;
import entidades.InformacionPersonal;
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
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().persist(ip);
		EntityManagerHelper.commit();
	}
}