package ru.rosbank.javaschool.web.service;

import lombok.RequiredArgsConstructor;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.exception.NotFoundException;
import ru.rosbank.javaschool.web.model.DrinkModel;
import ru.rosbank.javaschool.web.model.FriesModel;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.model.SandwichModel;
import ru.rosbank.javaschool.web.repository.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AdminService {

    private final SandwichRepository sandwichRepository;
    private final FriesRepository friesRepository;
    private final DrinkRepository drinkRepository;

    private final OrderRepository orderRepository;
    private final OrderPositionRepository orderPositionRepository;

    public ProductModel save(ProductModel model) {
        if (model.getClass().equals(SandwichModel.class)) {
            SandwichModel castedModel = (SandwichModel) model;
            sandwichRepository.save(castedModel);
            return model;
        }
        if (model.getClass().equals(DrinkModel.class)) {
            DrinkModel castedModel = (DrinkModel) model;
            drinkRepository.save(castedModel);
            return model;
        }
        if (model.getClass().equals(FriesModel.class)) {
            FriesModel castedModel = (FriesModel) model;
            friesRepository.save(castedModel);
            return model;
        }
        throw new NotFoundException();
    }

    public List<ProductModel> getAllProductModel() {
        List<ProductModel> result = new ArrayList<>();
        result.addAll(sandwichRepository.getAll());
        result.addAll(friesRepository.getAll());
        result.addAll(drinkRepository.getAll());
        return result;
    }

    public ProductModel getById(String category, int id) {
        if (category.equals(Constants.DRINKS)) {
            return drinkRepository.getById(id).orElseThrow(NotFoundException::new);
        }
        if (category.equals(Constants.FRIES)) {
            return friesRepository.getById(id).orElseThrow(NotFoundException::new);
        }
        if (category.equals(Constants.SANDWICHES)) {
            return sandwichRepository.getById(id).orElseThrow(NotFoundException::new);
        }
        throw new NotFoundException();
    }

    public Boolean removeProduct(String category, int id) {
        if (category.equals(Constants.DRINKS)) {
            drinkRepository.removeById(id);
            return true;
        }
        if (category.equals(Constants.FRIES)) {
            friesRepository.removeById(id);
            return true;
        }
        if (category.equals(Constants.SANDWICHES)) {
            sandwichRepository.removeById(id);
            return true;
        }
        return false;
    }
}
