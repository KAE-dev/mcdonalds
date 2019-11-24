package ru.rosbank.javaschool.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.dto.FriesDto;
import ru.rosbank.javaschool.web.dto.ProductDto;

@Data
@NoArgsConstructor
public class FriesModel extends ProductModel{

    private static final String CATEGORY = Constants.FRENCHFRIES;
    private String size;

    public FriesModel(int id, String name, int priceRub, String description, String imageUrl, String size) {
        super(id, name, priceRub,  description, imageUrl);
        this.size = size;
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    @Override
    public ProductDto toDto() {
        return new FriesDto(
                getId(),
                getName(),
                getPriceRub(),
                getImageUrl(),
                getSize()
        );
    }

}
