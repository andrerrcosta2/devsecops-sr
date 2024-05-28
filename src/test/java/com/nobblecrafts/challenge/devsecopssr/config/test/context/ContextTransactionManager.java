package com.nobblecrafts.challenge.devsecopssr.config.test.context;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ContextTransactionManager {
    @PersistenceUnit
    private final EntityManagerFactory emf;
    private EntityManager em;


    public void openTransaction() {
        if(em == null || !em.isOpen()) createEntityManager();
        log.debug("\n\nOpening transaction\n\n");
        if(!em.getTransaction().isActive()) em.getTransaction().begin();
    }

    public void closeTransaction() {
        em.close();
    }

    public void persistTransaction(List<Object> entities) {
        try {
            persistConfigurations(entities);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("\nError on ContextTransactionManager: {}", e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void createEntityManager() {
        this.em = emf.createEntityManager();
    }

    private void persistConfigurations(List<Object> entities) {
        for (Object entity : entities) {
            em.persist(entity);
        }
    }
}
