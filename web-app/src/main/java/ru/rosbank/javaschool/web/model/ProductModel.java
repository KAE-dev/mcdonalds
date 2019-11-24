package ru.rosbank.javaschool.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.web.dto.ProductDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProductModel {

    private int id;
    private String name;
    private int priceRub;
    private String description;
    private String imageUrl;

    public abstract String getCategory();

    public abstract ProductDto toDto();

}
