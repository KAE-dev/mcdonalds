package ru.rosbank.javaschool.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.web.dto.ProductDto;
import ru.rosbank.javaschool.web.dto.SandwichDto;

@Data
@NoArgsConstructor
public class SandwichModel extends ProductModel {

    private static final String CATEGORY = "Sandwich";

    public SandwichModel(int id, String name, int priceRub, String description, String imageUrl) {
        super(id, name, priceRub, description, imageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    @Override
    public ProductDto toDto() {
        return new SandwichDto(
                getId(),
                getName(),
                getPriceRub(),
                getImageUrl()
        );
    }
}
