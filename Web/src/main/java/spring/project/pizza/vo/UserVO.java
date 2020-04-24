package spring.project.pizza.vo;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class UserVO implements UserDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    @Column(unique = true)
//    @Size(max = 15, message = "15자보다 작아야 합니다")
//    private String userId;
//    @Size(min = 5, message =  "5자보다 많아야 합니다")
//    private String password;
////    @Size(min = 5, message =  "5자보다 많아야 합니다")
////    private String password2;
//    @NotBlank
//    private String name;
//    @NotBlank
//    private String address;
//    @Email
//    private String email;
//    private Integer level; // 0: 휴면 계정, 1: 회원, 2: 관리자
//    @OneToMany
//    private List<Order> orders;
//    private Date regDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Email
    @Column(length = 20, nullable = false)
    private String email;
    @Column(length = 100, nullable = false)
    private String password;

    private Integer level;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Authority> authorities;

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}
