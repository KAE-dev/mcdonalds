package ru.rosbank.javaschool.web.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.dto.ProductDto;
import ru.rosbank.javaschool.web.exception.NotFoundException;
import ru.rosbank.javaschool.web.model.*;
import ru.rosbank.javaschool.web.repository.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


class UserServiceTest {

    @Test
    void getAllProductDto() {
        val drinkRepository = mock(DrinkRepositoryJdbcImpl.class);
        val sandwichRepository = mock(SandwichRepositoryJdbcImpl.class);
        val friesRepository = mock(FriesRepositoryJdbcImpl.class);
        val orderRepository = mock(OrderRepositoryJdbcImpl.class);
        val orderPositionRepository = mock(OrderPositionRepositoryJdbcImpl.class);
        DrinkModel dModel = new DrinkModel();
        FriesModel fModel = new FriesModel();
        SandwichModel sModel = new SandwichModel();
        doReturn(Collections.singletonList(dModel)).when(drinkRepository).getAll();
        doReturn(Collections.singletonList(fModel)).when(friesRepository).getAll();
        doReturn(Collections.singletonList(sModel)).when(sandwichRepository).getAll();
        val service = new UserService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);

        List<ProductDto> result = service.getAllProductDto();
        assertEquals(3, result.size());
    }

    @Test
    void getById() {
        val drinkRepository = mock(DrinkRepositoryJdbcImpl.class);
        val sandwichRepository = mock(SandwichRepositoryJdbcImpl.class);
        val friesRepository = mock(FriesRepositoryJdbcImpl.class);
        val orderRepository = mock(OrderRepositoryJdbcImpl.class);
        val orderPositionRepository = mock(OrderPositionRepositoryJdbcImpl.class);
        int id = 0;
        doReturn(Optional.of(new DrinkModel())).when(drinkRepository).getById(id);
        doReturn(Optional.of(new FriesModel())).when(friesRepository).getById(id);
        doReturn(Optional.of(new SandwichModel())).when(sandwichRepository).getById(id);
        val service = new UserService(sandwichRepository, friesRepository, drinkRepository, orderRepository, orderPositionRepository);

        ProductModel result = service.getById(Constants.DRINKS, id);
        ProductModel result2 = service.getById(Constants.FRIES, id);
        ProductModel result3 = service.getById(Constants.SANDWICHES, id);

        assertNotNull(result);
        assertNotNull(result2);
        assertNotNull(result3);

        doReturn(Optional.empty()).when(drinkRepository).getById(id);
        doReturn(Optional.empty()).when(sandwichRepository).getById(id);
        doReturn(Optional.empty()).when(friesRepository).getById(id);
        val service1 = new UserService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);

        assertThrows(NotFoundException.class, () -> service1.getById(Constants.CATEGORY, id));
    }

    @Test
    void createOrder() {
        val drinkRepository = mock(DrinkRepositoryJdbcImpl.class);
        val sandwichRepository = mock(SandwichRepositoryJdbcImpl.class);
        val friesRepository = mock(FriesRepositoryJdbcImpl.class);
        val orderRepository = mock(OrderRepositoryJdbcImpl.class);
        val orderPositionRepository = mock(OrderPositionRepositoryJdbcImpl.class);
        val service = new UserService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);
        int result = service.createOrder();
        assertEquals(0, result);
    }

    @Test
    void order() {
        val drinkRepository = mock(DrinkRepositoryJdbcImpl.class);
        val sandwichRepository = mock(SandwichRepositoryJdbcImpl.class);
        val friesRepository = mock(FriesRepositoryJdbcImpl.class);
        val orderRepository = mock(OrderRepositoryJdbcImpl.class);
        val orderPositionRepository = mock(OrderPositionRepositoryJdbcImpl.class);
        val service = new UserService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);
        int orderId = 0;
        String category = Constants.FRIES;
        int id = 0;
        int quantity = 1;
        doReturn(Optional.of(new FriesModel())).when(friesRepository).getById(id);
        OrderPositionModel result = service.order(orderId, category, id, quantity);
        assertNotNull(result);
    }

    @Test
    void paidOrder() {
        val drinkRepository = mock(DrinkRepositoryJdbcImpl.class);
        val sandwichRepository = mock(SandwichRepositoryJdbcImpl.class);
        val friesRepository = mock(FriesRepositoryJdbcImpl.class);
        val orderRepository = mock(OrderRepositoryJdbcImpl.class);
        val orderPositionRepository = mock(OrderPositionRepositoryJdbcImpl.class);
        val service = new UserService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);
        int result = service.paidOrder(0);
        assertEquals(0, result);
    }

    @Test
    void getAllOrderPosition() {
        val drinkRepository = mock(DrinkRepositoryJdbcImpl.class);
        val sandwichRepository = mock(SandwichRepositoryJdbcImpl.class);
        val friesRepository = mock(FriesRepositoryJdbcImpl.class);
        val orderRepository = mock(OrderRepositoryJdbcImpl.class);
        val orderPositionRepository = mock(OrderPositionRepositoryJdbcImpl.class);
        val service = new UserService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);
        doReturn(Collections.emptyList()).when(orderPositionRepository).getAllByOrderId(0);
        List<OrderPositionModel> result = service.getAllOrderPosition(0);
        assertTrue(result.isEmpty());
    }

    @Test
    void updateOrderPositionModel() {
        val drinkRepository = mock(DrinkRepositoryJdbcImpl.class);
        val sandwichRepository = mock(SandwichRepositoryJdbcImpl.class);
        val friesRepository = mock(FriesRepositoryJdbcImpl.class);
        val orderRepository = mock(OrderRepositoryJdbcImpl.class);
        val orderPositionRepository = mock(OrderPositionRepositoryJdbcImpl.class);
        val service = new UserService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);
        int orderId = 0;
        int orderPositionId = 0;
        String category = Constants.SANDWICHES;
        int id = 0;
        int quantity = 0;
        doReturn(Optional.of(new SandwichModel())).when(sandwichRepository).getById(id);
        OrderPositionModel result = service.updateOrderPositionModel(orderId, orderPositionId, category, id, quantity);
        assertNotNull(result);
    }

    @Test
    void removeOrderPositionModelById() {
        val drinkRepository = mock(DrinkRepositoryJdbcImpl.class);
        val sandwichRepository = mock(SandwichRepositoryJdbcImpl.class);
        val friesRepository = mock(FriesRepositoryJdbcImpl.class);
        val orderRepository = mock(OrderRepositoryJdbcImpl.class);
        val orderPositionRepository = mock(OrderPositionRepositoryJdbcImpl.class);
        val service = new UserService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);
        int result = service.removeOrderPositionModelById(0);
        assertEquals(result, 0);
    }
}