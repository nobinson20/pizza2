package spring.project.pizza.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

// https://www.baeldung.com/spring-security-method-security
@Configuration
@EnableGlobalMethodSecurity(

        // The prePostEnabled property enables Spring Security pre/post annotations
        prePostEnabled = true,
        // The securedEnabled property determines if the @Secured annotation should be enabled
        securedEnabled = true,
        // The jsr250Enabled property allows us to use the @RoleAllowed annotation
        jsr250Enabled = true
)
public class GlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}
