package spring.project.pizza.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.project.pizza.dto.UserForm;
import spring.project.pizza.repository.AuthorityRepository;
import spring.project.pizza.repository.UserRepository;
import spring.project.pizza.vo.Authority;
import spring.project.pizza.vo.UserVO;

import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    // DTO UserForm 받아와서 비밀번호 인코딩 후 해당 VO를 DB에 저장 후 id 리턴
    //@Transactional
    public Integer join(UserForm userForm){
        // 비밀번호 암호화
        String encoded = new BCryptPasswordEncoder().encode(userForm.getPassword());

        userForm.setPassword(encoded);
        UserVO userVO = userForm.toEntity();
        //authorityRepository.save()
        List<Authority> authorities = userVO.getAuthorities();

        if("nobinson20@gmail.com".equals(userVO.getEmail())){
            authorities.add(new Authority(null,"ROLE_ADMIN", null));
        }

        List<Authority> savedAuthorities = authorityRepository.saveAll(authorities);
        //log.info("문제 확인!  "+savedAuthorities.toString());

        return userRepository.save(userVO).getId();
    }


    @Override
    //@Transactional (fetch = FetchType.EAGER를 uservo 클래스에 썼기때문에 필요없다. update/delete를 하지않기떄문에)
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        // 사용자 획득
        UserVO userVO = userRepository.findByEmail(userEmail).orElse(null);

        // 아래 라인을 쓰거나 fetch = FetchType.EAGER 를 UserVO 부분의 authorities 쪽에 어노테이션 달아줘야한다.
        //log.info("userVO 로그 " + userVO.toString());

        //List<GrantedAuthority> authorities = new ArrayList<>();
        //authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));

        //List<Authority> authorities = userVO.getAuthorities();

        //return new User(userVO.getEmail(), userVO.getPassword(), authorities);
        return userVO;
    }
}
