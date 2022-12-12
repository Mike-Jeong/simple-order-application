package org.example;

import javax.persistence.EntityManager;

public interface DBTransactionManager {

    EntityManager getEntityManager();

    void transactionBegin();

    void transactionCommit();

    void transactionRollback();
}
