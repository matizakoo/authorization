package auth.rest.config;

import auth.rest.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class AuthenticationConfig {
    private final UsersRepository usersRepository;
    @Bean
    public UserDetailsService userDetailsService(){
        log.info("UserDetailsService");
        System.out.println("UserDetailsService");
        return usersRepository::findByUsername;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        log.info("AuthenitcationProvider");
        System.out.println("AuthenitcationProvider");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
