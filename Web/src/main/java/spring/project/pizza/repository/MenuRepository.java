package spring.project.pizza.repository;

import org.springframework.data.repository.CrudRepository;
import spring.project.pizza.vo.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends CrudRepository<Menu, Integer> {

    @Override
    Menu save(Menu entity);

    @Override
    List<Menu> findAll();

    @Override
    Optional<Menu> findById(Integer integer);

    @Override
    void deleteById(Integer integer);
}
