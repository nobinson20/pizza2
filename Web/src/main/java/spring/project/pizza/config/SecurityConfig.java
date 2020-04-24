package spring.project.pizza.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import spring.project.pizza.service.UserService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor // => WebSecurityConfigurerAdapter 안에 있는 인스턴스 변수들을 위해서
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 노 이해
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 하위 목록 인증 무시
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 접근 설정
                .antMatchers("/").permitAll()
                .antMatchers("/signup").permitAll() // 회원가입 창 접근 허가
                .antMatchers("/users/signup").permitAll() // 회원가입 처리 접근 허가
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()


                // 로그인 설정
                .and().formLogin()
                .loginPage("/signin")
                .defaultSuccessUrl("/")
                .permitAll()

                // 로그아웃 설정
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)

                // 403 예외 처리
                .and().exceptionHandling().accessDeniedPage("/user/denied");
    }
}
