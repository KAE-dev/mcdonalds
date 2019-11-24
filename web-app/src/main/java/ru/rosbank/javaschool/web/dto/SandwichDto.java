package ru.rosbank.javaschool.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.web.constant.Constants;

@Data
@NoArgsConstructor
public class SandwichDto extends ProductDto {

    private static final String CATEGORY = Constants.SANDWICHES;

    public SandwichDto(int id, String name, int priceRub, String imageUrl) {
        super(id, name, priceRub,  imageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

}
