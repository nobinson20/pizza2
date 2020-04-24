package spring.project.pizza.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import spring.project.pizza.dto.IngredientForm;
import spring.project.pizza.repository.IngredientRepository;
import spring.project.pizza.vo.Ingredient;
import spring.project.pizza.vo.Ingredient.Type;

import java.util.List;

@Controller
@Slf4j
public class IngredientController {


    @Autowired
    private IngredientRepository ingredientRepository;


    @Secured("ROLE_ADMIN")
    @GetMapping("/ingredients")
    public String index(Model model){
        List<Ingredient> ingredients = (List<Ingredient>) ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);

        return "ingredients/index";
    }



    @GetMapping("/ingredients/new")
    public String newIngredient(){
        return "/ingredients/new";
    }

    @PostMapping("/ingredients")
    public String create(IngredientForm form){    //ingredient form 클래스로 form 내용을 전달 받음

        //form 데이터 받기
        String name = form.getName();
        Type type = Type.valueOf((form.getType().toUpperCase()));
        Double price = Double.parseDouble(form.getPrice());

        //VO생성
        Ingredient ingredient = new Ingredient(null, name, type, price);
        log.info(ingredient.toString());

        log.info(form.toString());

        // DB 저장
        Ingredient saved = ingredientRepository.save(ingredient);
        log.info(saved.toString());


        return "redirect:/ingredients";
    }

    @GetMapping("/ingredients/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        model.addAttribute("ingredient", ingredient);

        log.info(ingredient.toString());

        return "ingredients/edit";
    }

    @PostMapping("/ingredients/update/{id}")
    public String update(@PathVariable int id, IngredientForm form){
        // form data log
        log.info(form.toString());

        // VO 생성
        String name = form.getName();
        Type type = Type.valueOf(form.getType().toUpperCase());
        Double price = Double.parseDouble(form.getPrice());

        Ingredient ingredient = new Ingredient(id, name, type, price);
        log.info(ingredient.toString());
        // DB 저장
        Ingredient saved = ingredientRepository.save(ingredient);
        log.info(saved.toString());
        // redirect


        return "redirect:/ingredients";
    }

    @GetMapping("/ingredients/delete/{id}")
    public String delete(@PathVariable int id){
        ingredientRepository.deleteById(id);

        return "redirect:/ingredients";
    }

    @GetMapping("/ingredients/{id}")
    public String show(@PathVariable int id, Model model){
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);

        model.addAttribute("ingredient", ingredient);

        return "ingredients/show";


    }

    @GetMapping("/ingredients/init")
    public String init() {
        ingredientRepository.save(new Ingredient(null, "오리지날 도우", Type.DOUGH, 1000.0));
        ingredientRepository.save(new Ingredient(null, "씬 도우", Type.DOUGH, 1000.0));
        ingredientRepository.save(new Ingredient(null, "나폴리 도우", Type.DOUGH, 100.00));
        ingredientRepository.save(new Ingredient(null, "토마토 소스", Type.SAUCE, 100.0));
        ingredientRepository.save(new Ingredient(null, "크림 소스", Type.SAUCE, 100.0));
        ingredientRepository.save(new Ingredient(null, "모짜렐라 치즈", Type.CHEESE, 10.00));
        ingredientRepository.save(new Ingredient(null, "페퍼로니", Type.TOPPING, 10.00));
        ingredientRepository.save(new Ingredient(null, "쉬림프", Type.TOPPING, 100.0));
        ingredientRepository.save(new Ingredient(null, "포크", Type.TOPPING, 100.0));
        ingredientRepository.save(new Ingredient(null, "비프", Type.TOPPING, 100.0));
        ingredientRepository.save(new Ingredient(null, "불고기", Type.TOPPING, 100.0));
        ingredientRepository.save(new Ingredient(null, "포테이토", Type.TOPPING, 100.0));
        ingredientRepository.save(new Ingredient(null, "까망베르", Type.TOPPING, 100.0));
        return "redirect:/ingredients";
    }
}
