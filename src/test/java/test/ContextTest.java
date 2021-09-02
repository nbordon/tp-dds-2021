package test;

import db.EntityManagerHelper;
import entidades.Direccion;
import entidades.Organizacion.Pregunta;
import org.junit.Ignore;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}
	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}

	@Test @Ignore
	public void persistir1UsuarioTest(){
		Pregunta dir = new Pregunta();
		dir.setDescripcion("¿Color?");
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().persist(dir);
		EntityManagerHelper.commit();
	}
}