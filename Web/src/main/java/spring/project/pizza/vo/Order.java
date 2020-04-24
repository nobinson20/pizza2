package spring.project.pizza.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
// 인덱스 설정: https://wooriworld2006.tistory.com/397
@Table(name = "orders", indexes = {@Index(columnList = "status")})
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.ORDINAL) // https://jogeum.net/6?category=766565
    private Status status;

    public static enum Status {
        IN_CART, ORDERED
    }

    private String address;

    @Column(name = "created_at") // https://krksap.tistory.com/1268
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserVO orderer;

    @ManyToMany
    private List<Menu> menus;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    public void add(Menu menu) {
        this.menus.add(menu);
    }
}
