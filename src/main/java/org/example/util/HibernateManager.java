package org.example.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateManager implements DBTransactionManager {

    private static HibernateManager hibernateManager;
    private static EntityManager entityManager;

    private HibernateManager() {
        if(entityManager == null) {
            try {
                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("product");
                entityManager = entityManagerFactory.createEntityManager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized HibernateManager getInstance(){
        if(hibernateManager == null){
            hibernateManager = new HibernateManager();
        }
        return hibernateManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void transactionBegin() {
        getInstance().getEntityManager().getTransaction().begin();
    }

    @Override
    public void transactionCommit() {
        getInstance().getEntityManager().getTransaction().commit();
        getInstance().getEntityManager().clear();
    }

    @Override
    public void transactionRollback() {
        getInstance().getEntityManager().getTransaction().rollback();
        getInstance().getEntityManager().clear();
    }
}
