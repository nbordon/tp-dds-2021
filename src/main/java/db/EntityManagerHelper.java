package db;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EntityManagerHelper {

    private static EntityManagerFactory emf;

    private static ThreadLocal<EntityManager> threadLocal;

    static {
        Map<String, String> env = System.getenv();

        Map<String, Object> configOverrides = new HashMap<String, Object>();
        String db_url = env.get("DB_URL");
        String db_user = env.get("DB_USERNAME");
        String db_pass = env.get("DB_PASSWORD");
        String db_port = env.get("DB_PORT");
        String db_name = env.get("DB_NAME");

        if(!db_url.isEmpty() && !db_user.isEmpty() && !db_pass.isEmpty() && !db_port.isEmpty() && !db_name.isEmpty()){
            System.out.println("Using env config for database");
            configOverrides.put("hibernate.connection.url","jdbc:mysql://"+db_url+":"+db_port+"/"+db_name+"" );
            configOverrides.put("hibernate.connection.username", db_user);
            configOverrides.put("hibernate.connection.password",db_pass);
        }

        try {
            emf = Persistence.createEntityManagerFactory("db", configOverrides);
            threadLocal = new ThreadLocal<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EntityManager entityManager() {
        return getEntityManager();
    }

    public static EntityManager getEntityManager() {
        EntityManager manager = threadLocal.get();
        if (manager == null || !manager.isOpen()) {
            manager = emf.createEntityManager();
            threadLocal.set(manager);
        }
        return manager;
    }

    public static void closeEntityManager() {
        EntityManager em = threadLocal.get();
        threadLocal.set(null);
        em.close();
    }

    public static void beginTransaction() {
        EntityManager em = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if(!tx.isActive()){
            tx.begin();
        }
    }

    public static void commit() {
        EntityManager em = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        if(tx.isActive()){
            tx.commit();
        }

    }

    public static void rollback(){
        EntityManager em = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        if(tx.isActive()){
            tx.rollback();
        }
    }

    public static Query createQuery(String query) {
        return getEntityManager().createQuery(query);
    }

    public static void persist(Object o){
        entityManager().persist(o);
    }

    public static void withTransaction(Runnable action) {
        withTransaction(() -> {
            action.run();
            return null;
        });
    }
    public static <A> A withTransaction(Supplier<A> action) {
        beginTransaction();
        try {
            A result = action.get();
            commit();
            return result;
        } catch(Throwable e) {
            rollback();
            throw e;
        }
    }
}