package spring.project.pizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.project.pizza.vo.UserVO;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserVO, Integer> {
    // 노 구현?
    Optional<UserVO> findByEmail(String email);
}
