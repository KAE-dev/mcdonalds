package ru.rosbank.javaschool.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.web.constant.Constants;

@Data
@NoArgsConstructor
public class DrinkDto extends ProductDto {

    private static final String CATEGORY = Constants.DRINKS;
    private int volumeMl;

    public DrinkDto(int id, String name, int priceRub, String imageUrl, int volumeMl) {
        super(id, name, priceRub, imageUrl);
        this.volumeMl = volumeMl;
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }
}
