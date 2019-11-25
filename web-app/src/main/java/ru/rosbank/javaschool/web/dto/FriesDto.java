package ru.rosbank.javaschool.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.web.constant.Constants;

@Data
@NoArgsConstructor
public class FriesDto extends ProductDto {

    private static final String CATEGORY = Constants.FRIES;
    private String size;

    public FriesDto(int id, String name, int priceRub, String imageUrl, String size) {
        super(id, name, priceRub, imageUrl);
        this.size = size;
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

}
