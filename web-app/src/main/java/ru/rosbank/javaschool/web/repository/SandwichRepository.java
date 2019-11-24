package ru.rosbank.javaschool.web.repository;

import ru.rosbank.javaschool.web.model.DrinkModel;
import ru.rosbank.javaschool.web.model.SandwichModel;

import java.util.List;
import java.util.Optional;

public interface SandwichRepository extends ProductRepository<SandwichModel> {

    List<SandwichModel> getAll();

    Optional<SandwichModel> getById(int id);

    void save(SandwichModel model);

    void removeById(int id);

}
