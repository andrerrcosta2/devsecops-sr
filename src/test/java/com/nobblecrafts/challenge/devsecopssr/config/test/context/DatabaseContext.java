package com.nobblecrafts.challenge.devsecopssr.config.test.context;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class DatabaseContext {
    private final CopyOnWriteArrayList<Object> configurations;
    private final ContextTransactionManager transactionManager;

    public DatabaseContext(ContextTransactionManager transactionManager) {
        configurations = new CopyOnWriteArrayList<>();
        this.transactionManager = transactionManager;
    }

    private DatabaseContext(ContextTransactionManager transactionManager, CopyOnWriteArrayList<Object> configurations) {
        this.configurations = configurations;
        this.transactionManager = transactionManager;
    }

    public <T> List<T> retrieve(Class<T> type) {
        return configurations.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }


    public Applier suppose(Object context) {
        configurations.add(context);
        Applier applier = new Applier(transactionManager, new ArrayList<>(configurations));
        return applier;
    }

    public static class Applier {

        private ContextTransactionManager transactionManager;
        private List<Object> configurations;

        public Applier(ContextTransactionManager transactionManager,
                       List<Object> configurations) {
            this.transactionManager = transactionManager;
            this.configurations = configurations;
        }

        public DatabaseContext and() {
            return new DatabaseContext(transactionManager, new CopyOnWriteArrayList<>(configurations));
        }

        public Applier thenSuppose(Function<DatabaseContext, Object> function) {
            openTransaction();
            persistTransaction();

            var context = new DatabaseContext(transactionManager, new CopyOnWriteArrayList<>(configurations));
            Object entity = function.apply(context);
            List<Object> configurations = new ArrayList<>();
            configurations.add(entity);

            return new Applier(transactionManager, configurations);
        }

        public Applier existsOnDatabase() {
            openTransaction();
            persistTransaction();
            closeTransaction();
            return this;
        }

        private void openTransaction() {
            transactionManager.openTransaction();
        }

        private void closeTransaction() {
            transactionManager.closeTransaction();
        }

        private void persistTransaction() {
            transactionManager.persistTransaction(configurations);
        }

    }
}
