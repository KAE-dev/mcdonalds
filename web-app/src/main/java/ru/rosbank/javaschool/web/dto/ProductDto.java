package ru.rosbank.javaschool.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProductDto {

    private int id;
    private String name;
    private int priceRub;
    private String imageUrl;

    public abstract String getCategory();

}
