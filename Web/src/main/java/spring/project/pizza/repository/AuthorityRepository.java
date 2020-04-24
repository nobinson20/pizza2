package spring.project.pizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.pizza.vo.Authority;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    List<Authority> findByUserId(Integer id);
}
