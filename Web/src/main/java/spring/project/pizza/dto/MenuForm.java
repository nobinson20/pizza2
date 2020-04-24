package spring.project.pizza.dto;

import lombok.Data;
import spring.project.pizza.vo.Ingredient;

import java.util.List;

@Data
public class MenuForm {
    private String name;
    private String price;
    private List<Ingredient> ingredients;
}
