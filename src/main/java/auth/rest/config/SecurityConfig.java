package auth.rest.config;


import auth.rest.domain.Roles;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Log4j2
@AllArgsConstructor
public class SecurityConfig {
    private final LoggerFilter loggerFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Security filter chain");
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/user/**","/hi", "v3/**").hasAnyAuthority(Roles.USER.name(), Roles.ADMIN.name())
                    .antMatchers("/admin/**").hasAuthority(Roles.ADMIN.name())
                    .anyRequest().authenticated()
                    .and()
                .formLogin().permitAll()
                    .defaultSuccessUrl("/home", true)
                    .and()
                .logout()
                    .and()
                .addFilterBefore(loggerFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic();

        return http.build();
    }
}
