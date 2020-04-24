package spring.project.pizza.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import spring.project.pizza.dto.MenuForm;
import spring.project.pizza.repository.IngredientRepository;
import spring.project.pizza.repository.MenuRepository;
import spring.project.pizza.vo.Ingredient;
import spring.project.pizza.vo.Menu;
import spring.project.pizza.vo.Ingredient.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private IngredientRepository ingredientRepository;


    @GetMapping("/menus")
    public String index(Model model){

        List<Menu> menus = (List<Menu>) menuRepository.findAll();
        model.addAttribute("menus", menus);



        log.info(menus.toString());


        return "menus/index";
    }

    @GetMapping("/menus/new")
    public String newMenu(Model model){

        List<Ingredient> ingredients = ingredientRepository.findAll();

        // types is an array with 'Type' elements.
        Ingredient.Type[] types = Ingredient.Type.values();


        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

        model.addAttribute("menu", new Menu());


        return "/menus/new";
    }

    // new.html 에서  form 작성 후 post 요청(메뉴 추가 버튼)이 들어왔을 때 처리
    @PostMapping("/menus")
    public String create(MenuForm form){
        // form 데이터 받기
        String name = form.getName();
        Double price = Double.parseDouble(form.getPrice());
        List<Ingredient> ingredients = form.getIngredients();
        // VO 생성
        Menu menu = new Menu(null, name, price, ingredients);
        log.info("menu VO is "+ menu.toString());
        log.info("MenuForm (DTO) is "+ form.toString());

        // DB 저장
        Menu saved = menuRepository.save(menu);
        log.info("Saved to DB: "+ saved.toString());

        return "redirect:/menus";
    }

    // Controller about 각 메뉴명에 대한 수정 요청
    // model => view attributes를 담은 view 객체
    @GetMapping("/menus/edit/{id}")
    public String edit(@PathVariable int id, Model model){
///////////////
        List<Ingredient> ingredients = ingredientRepository.findAll();

        // types is an array with 'Type' elements.
        Ingredient.Type[] types = Ingredient.Type.values();


        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

       // model.addAttribute("menu", new Menu());
//////////////////

        Menu menu = menuRepository.findById(id).orElse(null);


        model.addAttribute("menu", menu);
        return "menus/edit";
    }

    // Controller about 각 메뉴명에 대한 수정 요청 (post)
    // edit (get) => update (post)
    @PostMapping("/menus/update/{id}")
    public String update(@PathVariable int id, MenuForm form){
        // form data log
        log.info(form.toString());

        // VO 생성
        String name = form.getName();
        Double price = Double.parseDouble(form.getPrice());
//        List<Ingredient> ingredients = new ArrayList<Ingredient>();
//        for (ingredient : form.getIngredients()){
//            ingredients.add(new Ingredient(null, ingredient,  ));
//        }

        List<Ingredient> ingredients = form.getIngredients();

        Menu menu = new Menu(id, name, price, ingredients);
//      Menu menu = new Menu(id, name, price, ingredients);

        log.info("menu(VO) is "+menu.toString());
        // DB 저장
        Menu saved = menuRepository.save(menu);
        log.info("Saved to DB: "+saved.toString());
        // redirect


        return "redirect:/menus";
    }

    @GetMapping("/menus/delete/{id}")
    public String delete(@PathVariable int id){
        menuRepository.deleteById(id);

        return "redirect:/menus";
    }

    @GetMapping("/menus/{id}")
    public String show(@PathVariable int id, Model model){

        Menu menu = menuRepository.findById(id).orElse(null);
        //Optional<Menu> menu = menuRepository.findById(id);
        model.addAttribute("menu", menu);

        return "menus/show";
    }

    @GetMapping("/menus/init")
    public String init() {
        // 재료
        ingredientRepository.save(new Ingredient(null, "오리지날 도우", Type.DOUGH, 20.0));
        ingredientRepository.save(new Ingredient(null, "씬 도우", Type.DOUGH, 20.0));
        ingredientRepository.save(new Ingredient(null, "나폴리 도우", Type.DOUGH, 20.0));
        ingredientRepository.save(new Ingredient(null, "토마토 소스", Type.SAUCE, 20.0));
        ingredientRepository.save(new Ingredient(null, "크림 소스", Type.SAUCE, 20.0));
        ingredientRepository.save(new Ingredient(null, "모짜렐라 치즈", Type.CHEESE, 20.0));
        ingredientRepository.save(new Ingredient(null, "페퍼로니", Type.TOPPING, 20.0));
        ingredientRepository.save(new Ingredient(null, "쉬림프", Type.TOPPING, 20.0));
        ingredientRepository.save(new Ingredient(null, "포크", Type.TOPPING, 20.0));
        ingredientRepository.save(new Ingredient(null, "비프", Type.TOPPING, 20.0));
        ingredientRepository.save(new Ingredient(null, "불고기", Type.TOPPING, 20.0));
        ingredientRepository.save(new Ingredient(null, "포테이토", Type.TOPPING, 20.0));
        ingredientRepository.save(new Ingredient(null, "까망베르", Type.TOPPING, 20.0));

        // 페퍼로니 피자
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(4);
        ids.add(6);
        ids.add(7);
        List<Ingredient> ingredients = ingredientRepository.findAllById(ids);
        menuRepository.save(new Menu(null, "페퍼로니 피자", 990.0, ingredients));

        // 포테이토 피자
        ids.clear();
        ids.add(2);
        ids.add(4);
        ids.add(6);
        ids.add(9);
        ids.add(12);
        ingredients = ingredientRepository.findAllById(ids);
        menuRepository.save(new Menu(null, "포테이토 피자", 990.0, ingredients));

        // 까망베르 피자
        ids.clear();
        ids.add(3);
        ids.add(5);
        ids.add(6);
        ids.add(13);
        ingredients = ingredientRepository.findAllById(ids);
        menuRepository.save(new Menu(null, "까망베르 피자", 990.0, ingredients));

        return "redirect:/menus";
    }




    // If there is an ingredient in 'List<Ingredient> ingredients' that matches with type
    // it returns 'List<Ingredient>' of ingredients.
    // ex) [monterrey jack, cheddar, sour cream] & cheese => [monterrey jack, cheddar]
    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type){
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
