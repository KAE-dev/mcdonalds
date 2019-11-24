package ru.rosbank.javaschool.web.repository;

import ru.rosbank.javaschool.web.model.DrinkModel;

import java.util.List;
import java.util.Optional;

public interface DrinkRepository extends ProductRepository<DrinkModel> {

    List<DrinkModel> getAll();

    Optional<DrinkModel> getById(int id);

    void save(DrinkModel model);

    void removeById(int id);

}
