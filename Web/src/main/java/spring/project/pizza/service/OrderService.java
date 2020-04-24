package spring.project.pizza.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.project.pizza.dto.OrderForm;
import spring.project.pizza.repository.MenuRepository;
import spring.project.pizza.repository.OrderRepository;
import spring.project.pizza.vo.Menu;
import spring.project.pizza.vo.Order;
import spring.project.pizza.vo.UserVO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuRepository menuRepository;

    public Order addToCart(Integer menuId, UserVO orderer) {
        // 피자를
        Menu menu = menuRepository.findById(menuId).orElse(null);

        // 카트에 담는다
        Order cart = orderRepository.findByStatus(Order.Status.IN_CART).orElse(
                Order.builder().status(Order.Status.IN_CART)
                        .orderer(orderer)
                        .menus(new ArrayList<>())
                        .build()
        );
        cart.add(menu);

        return orderRepository.save(cart);
    }


    // 해당 유저의 카트를 가져 옴
    public Order getCart(UserVO user) {
        log.info(user.toString());

        // 카트 검색
        Order cart = orderRepository.findByStatusAndOrderer(Order.Status.IN_CART, user).orElse(null);

        // 없다면?
        if (cart == null) {
            // 카트 생성
            cart = Order.builder().status(Order.Status.IN_CART).orderer(user).build();
            //cart = Order.builder().address("").id(null).menus(null).status((Order.Status.IN_CART)).orderer(user).build();
            log.info("비어있는 카트의 정보는 "+cart.toString());

            // DB 저장
            cart = orderRepository.save(cart);
        }
        log.info(cart.toString());
        return cart;
    }


    @Transactional
    public Order receive(OrderForm form) {
        // get order
        Integer orderId = form.getId();
        Order order = orderRepository.findById(orderId).orElse(null);
        log.info(order.toString());

        // set values
        order.setAddress(form.getAddress());
        order.setStatus(Order.Status.ORDERED);

        return orderRepository.save(order);
    }


    public List<Order> getOrders(UserVO user) {
        // 모든 주문 중, 상태가 ORDERED && 주문자가 user 인 것들
        return orderRepository.findAllByStatusAndOrderer(Order.Status.ORDERED, user)
                .orElse(new ArrayList<Order>());
    }


}
