package spring.project.pizza.repository;

import org.springframework.data.repository.CrudRepository;
import spring.project.pizza.vo.Order;
import spring.project.pizza.vo.UserVO;

import java.util.ArrayList;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    // 카트 상태인 주문을 찾는다.
    Optional<Order> findByStatus(Order.Status inCart);

    // 해당유저의 카트를 검색
    Optional<Order> findByStatusAndOrderer(Order.Status status, UserVO user);


    Optional<ArrayList<Order>> findAllByStatusAndOrderer(Order.Status ordered, UserVO user);
}
