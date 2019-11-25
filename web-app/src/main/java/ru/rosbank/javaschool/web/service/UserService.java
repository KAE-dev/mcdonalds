package ru.rosbank.javaschool.web.service;

import lombok.RequiredArgsConstructor;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.dto.ProductDto;
import ru.rosbank.javaschool.web.exception.NotFoundException;
import ru.rosbank.javaschool.web.model.*;
import ru.rosbank.javaschool.web.repository.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserService {

  private final SandwichRepository sandwichRepository;
  private final FriesRepository friesRepository;
  private final DrinkRepository drinkRepository;

  private final OrderRepository orderRepository;
  private final OrderPositionRepository orderPositionRepository;


  public List<ProductDto> getAllProductDto() {
    List<ProductDto> result = new ArrayList<>();
    for (SandwichModel model : sandwichRepository.getAll()) {
      result.add(model.toDto());
    }
    for (FriesModel model : friesRepository.getAll()) {
      result.add(model.toDto());
    }
    for (DrinkModel model : drinkRepository.getAll()) {
      result.add(model.toDto());
    }
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

  public int createOrder() {
    OrderModel model = new OrderModel(0, false);
    orderRepository.save(model);
    return model.getId();
  }

  public OrderPositionModel order(int orderId, String category, int id, int quantity) {
    ProductModel productModel = this.getById(category, id);
    OrderPositionModel orderPositionModel = new OrderPositionModel(
        0,
        orderId,
        productModel.getId(),
        productModel.getCategory(),
        productModel.getName(),
        productModel.getPriceRub(),
        quantity
    );
    orderPositionRepository.save(orderPositionModel);
    return orderPositionModel;
  }




  public int paidOrder(int orderId) {
      orderRepository.save(new OrderModel(orderId, true));
    return orderId;
  }


  public List<OrderPositionModel> getAllOrderPosition(int orderId) {
      return orderPositionRepository.getAllByOrderId(orderId);
  }

  public OrderPositionModel updateOrderPositionModel(int orderId, int orderPositionId, String category, int id, int quantity) {
    ProductModel productModel = this.getById(category, id);
    OrderPositionModel orderPositionModel = new OrderPositionModel(
            orderPositionId,
            orderId,
            productModel.getId(),
            productModel.getCategory(),
            productModel.getName(),
            productModel.getPriceRub(),
            quantity
    );
    orderPositionRepository.save(orderPositionModel);
    return orderPositionModel;
  }

  public int removeOrderPositionModelById(int idOrderPositionModel) {
      orderPositionRepository.removeById(idOrderPositionModel);
    return idOrderPositionModel;
  }
}
