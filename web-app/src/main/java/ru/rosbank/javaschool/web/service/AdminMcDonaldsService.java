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
public class AdminMcDonaldsService {

    private final SandwichRepository sandwichRepository;
    private final FriesRepository friesRepository;
    private final DrinkRepository drinkRepository;

    private final OrderRepository orderRepository;
    private final OrderPositionRepository orderPositionRepository;

    public void save(ProductModel model) {
        if (model.getCategory().equals(Constants.SANDWICHES)) {
            sandwichRepository.save((SandwichModel) model);
        }
        if (model.getCategory().equals(Constants.FRENCHFRIES)) {
            friesRepository.save((FriesModel) model);
        }
        if (model.getCategory().equals(Constants.DRINKS)) {
            drinkRepository.save((DrinkModel) model);
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
        if (category.equals(Constants.FRENCHFRIES)) {
            return friesRepository.getById(id).orElseThrow(NotFoundException::new);
        }
        if (category.equals(Constants.SANDWICHES)) {
            return sandwichRepository.getById(id).orElseThrow(NotFoundException::new);
        }
        throw new NotFoundException();
    }
}
