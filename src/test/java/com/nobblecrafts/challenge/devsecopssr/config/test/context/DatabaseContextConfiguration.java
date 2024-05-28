package com.nobblecrafts.challenge.devsecopssr.config.test.context;

import lombok.experimental.SuperBuilder;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@SuperBuilder(toBuilder = true)
public abstract class DatabaseContextConfiguration<T> {

    private final CopyOnWriteArrayList<T> configurations = new CopyOnWriteArrayList<>();

    public void addEntity(T entity) {
        configurations.add(entity);
    }

    public Optional<Class> getEntityClass() {
        return configurations.stream().findAny()
                .map(Object::getClass);
    }

    public Set<T> getEntities() {
        Set<T> set = configurations.stream().collect(Collectors.toSet());
        return Collections.unmodifiableSet(set);
    }
}
