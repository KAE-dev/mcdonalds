package ru.rosbank.javaschool.web.repository;

import ru.rosbank.javaschool.web.model.ProductModel;

import java.util.List;
import java.util.Optional;

public interface ProductRepository<T> {

    List<T> getAll();

    Optional<T> getById(int id);

    void save(T model);

    void removeById(int id);

}
