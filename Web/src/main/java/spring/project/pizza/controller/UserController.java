package spring.project.pizza.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.project.pizza.dto.UserForm;
import spring.project.pizza.service.UserService;

import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    // Displaying signup page
    @GetMapping("/signup")
    public String signup(UserForm userForm) {

        return "users/signup";

    }

    @PostMapping("/users/signup")
    public String create(@Valid UserForm form, // 입력 폼 검증
                         BindingResult result){ // 검증 결과 객체
        log.info(form.toString());


        if (!form.getPassword().equals(form.getConfirm())) {
            log.info(String.format("PW(%s), CF(%s)", form.getPassword(), form.getConfirm()));
            FieldError confirmError = new FieldError("form", "confirm", "비밀번호가 일치하지 않습니다.");
            result.addError(confirmError);
        }

        if (result.hasErrors()) {
            return "users/signup"; // 검증 실패 시, 가입 폼으로 이동
        }

        userService.join(form);


        return "redirect:/signin";
    }

    @GetMapping("/signin")
    public String signin(){

        return "users/signin";
    }



//    // New member signs up and submit the form
//    @PostMapping("/users")
//    public String create(UserForm form) {
//
//        // form data 받기
//        String userId = form.getUserId();
//        String password = form.getPassword();
////        String password2 = form.getPassword2();
//        String name = form.getName();
//        String address = form.getAddress();
//        String email = form.getEmail();
//        int level = Integer.parseInt(form.getLevel());
//        Date regDate = form.getRegDate();
//        // VO 생성
//        User user = new User(null, userId, password, name, address, email, level, null, regDate);
//        log.info("menu VO is " + user.toString());
//        log.info("MenuForm (DTO) is " + form.toString());
//
//        // DB 저장
//        User saved = userRepository.save(user);
//        log.info("Saved to DB: " + saved.toString());
//        return "/users/thankYou";
//    }

    // Thank you page
//    @PostMapping("/users/thankYou")
//    public String thankYou(){
//        return "/users/thankYou";
//    }

    // login page
}
