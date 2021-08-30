package test;

import db.EntityManagerHelper;
import entidades.Direccion;
import org.junit.Ignore;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.time.LocalDate;

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
	public void persistir1UsuarioTest(){
		Direccion dir = new Direccion();
		dir.setCalle("Las acacias");
		dir.setNumero("1234");
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().persist(dir);
		EntityManagerHelper.commit();
	}
}