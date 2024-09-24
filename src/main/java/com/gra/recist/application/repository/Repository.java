package com.gra.recist.application.repository;

import java.util.UUID;

public interface Repository<T> {
    T get(UUID id);

    void save(T t);
}
