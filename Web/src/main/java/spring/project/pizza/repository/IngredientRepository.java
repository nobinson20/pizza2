package spring.project.pizza.repository;

import org.springframework.data.repository.CrudRepository;
import spring.project.pizza.vo.Ingredient;

import java.util.List;
import java.util.Optional;

// CrudRepository => CRUD를 위한 클래스 <관리할 데이터 (VO or entity) 타입, PK 타입>
public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {

    @Override
    //<S extends Ingredient> S save(S entity);
    Ingredient save (Ingredient entity);

    @Override
    List<Ingredient> findAll();

    @Override
    Optional<Ingredient> findById(Integer integer);

    @Override
    void deleteById(Integer integer);

    @Override
    List<Ingredient> findAllById(Iterable<Integer> integers);
}
