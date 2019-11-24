package ru.rosbank.javaschool.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.dto.DrinkDto;
import ru.rosbank.javaschool.web.dto.ProductDto;

@Data
@NoArgsConstructor
public class DrinkModel extends ProductModel {

    private static final String CATEGORY = Constants.DRINKS;
    private int volumeMl;

    public DrinkModel(int id, String name, int priceRub,  String description,  String imageUrl, int volumeMl) {
        super(id, name, priceRub,  description, imageUrl);
        this.volumeMl = volumeMl;
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    @Override
    public ProductDto toDto() {
        return new DrinkDto(
                getId(),
                getName(),
                getPriceRub(),
                getImageUrl(),
                getVolumeMl()
        );
    }
}
