package ru.rosbank.javaschool.web.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.model.DrinkModel;
import ru.rosbank.javaschool.web.model.FriesModel;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.model.SandwichModel;
import ru.rosbank.javaschool.web.repository.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class AdminServiceTest {

    @Test
    void save() {
        val drinkRepository = mock(DrinkRepositoryJdbcImpl.class);
        val sandwichRepository = mock(SandwichRepositoryJdbcImpl.class);
        val friesRepository = mock(FriesRepositoryJdbcImpl.class);
        val orderRepository = mock(OrderRepositoryJdbcImpl.class);
        val orderPositionRepository = mock(OrderPositionRepositoryJdbcImpl.class);
        ProductModel model = new SandwichModel(
                0,
                "something",
                2,
                "",
                ""
        );
        ProductModel model2 = new FriesModel(
                0,
                "something",
                2,
                "",
                "",
                ""
        );
        ProductModel model3 = new DrinkModel(
                0,
                "something",
                2,
                "",
                "",
                500
        );
        val service = new AdminService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);
        assertNotNull(service.save(model));
        assertNotNull(service.save(model2));

    }

    @Test
    void getAllProductModel() {
        val drinkRepository = mock(DrinkRepositoryJdbcImpl.class);
        val sandwichRepository = mock(SandwichRepositoryJdbcImpl.class);
        val friesRepository = mock(FriesRepositoryJdbcImpl.class);
        val orderRepository = mock(OrderRepositoryJdbcImpl.class);
        val orderPositionRepository = mock(OrderPositionRepositoryJdbcImpl.class);
        int id = 0;
        doReturn(Optional.of(new DrinkModel())).when(drinkRepository).getById(id);
        doReturn(Optional.of(new FriesModel())).when(friesRepository).getById(id);
        doReturn(Optional.of(new SandwichModel())).when(sandwichRepository).getById(id);
        val service = new AdminService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);
        List<ProductModel> item = service.getAllProductModel();
            assertNotNull(item);
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
        val service = new AdminService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);
        ProductModel item = service.getById(Constants.DRINKS, id);
        ProductModel item1 = service.getById(Constants.FRIES, id);
        ProductModel item2 = service.getById(Constants.SANDWICHES, id);
        assertNotNull(item);
        assertNotNull(item1);
        assertNotNull(item2);
    }

    @Test
    void removeProduct() {
        val drinkRepository = mock(DrinkRepositoryJdbcImpl.class);
        val sandwichRepository = mock(SandwichRepositoryJdbcImpl.class);
        val friesRepository = mock(FriesRepositoryJdbcImpl.class);
        val orderRepository = mock(OrderRepositoryJdbcImpl.class);
        val orderPositionRepository = mock(OrderPositionRepositoryJdbcImpl.class);
        ProductModel item = new SandwichModel(
                0,
                "",
                0,
                "",
                ""
        );
        ProductModel item1 = new DrinkModel(
                0,
                "",
                0,
                "",
                "",
                12
        );
        ProductModel item2 = new FriesModel(
                0,
                "",
                0,
                "",
                "",
                "as"
        );
        val service = new AdminService(sandwichRepository,friesRepository, drinkRepository, orderRepository, orderPositionRepository);

        assertTrue(service.removeProduct(item.getCategory(), item.getId()));
        assertTrue(service.removeProduct(item1.getCategory(), item1.getId()));
        assertTrue(service.removeProduct(item2.getCategory(), item2.getId()));
    }
}