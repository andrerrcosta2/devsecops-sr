package com.nobblecrafts.challenge.devsecopssr.util.context;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestComponent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@TestComponent
public class DatabaseContext {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private final CopyOnWriteArrayList<Object> configurations;

    public DatabaseContext() {
        configurations = new CopyOnWriteArrayList<>();
    }

    protected DatabaseContext(CopyOnWriteArrayList<Object> configurations) {
        this.configurations = configurations;
    }

    public Applier suppose(Object context) {
        configurations.add(context);
        Applier applier = new Applier(entityManagerFactory.createEntityManager(),
                configurations,
                this);
        return applier;
    }

    public static class Applier {
        private EntityManager em;
        private List<Object> configurations;
        DatabaseContext context;

        public Applier(EntityManager em,
                       List<Object> configurations,
                       DatabaseContext context) {
            this.em = em;
            this.configurations = configurations;
            this.context = context;
        }

        public DatabaseContext and() {
            return context;
        }

        public Applier existsOnDatabase() {
            try {
                em.getTransaction().begin();
                persistConfigurations();
                em.flush();
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                log.error("\nError on database context persistence");
                throw e;
            } finally {
                em.close();
            }
            return this;
        }

        private void persistConfigurations() {
            for(Object entity : configurations) {
                em.persist(entity);
            }
            configurations.clear();
        }
    }
}
