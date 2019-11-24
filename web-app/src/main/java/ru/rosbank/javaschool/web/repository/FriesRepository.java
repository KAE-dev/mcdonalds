package ru.rosbank.javaschool.web.repository;

import ru.rosbank.javaschool.web.model.FriesModel;

import java.util.List;
import java.util.Optional;

public interface FriesRepository extends ProductRepository<FriesModel> {

    List<FriesModel> getAll();

    Optional<FriesModel> getById(int id);

    void save(FriesModel model);

    void removeById(int id);

}
