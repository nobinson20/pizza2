package spring.project.pizza.dto;

import lombok.Data;
import spring.project.pizza.type.Role;
import spring.project.pizza.vo.Authority;
import spring.project.pizza.vo.UserVO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserForm {
    private Integer id;
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String confirm;
    //private Integer level;

    // DTO -> VO
    public UserVO toEntity(){
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(null, Role.USER.getValue(), null));
        return new UserVO(null, email, password, 1, authorities);
    }
}
