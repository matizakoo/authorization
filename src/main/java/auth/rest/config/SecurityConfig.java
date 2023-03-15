package auth.rest.config;


import auth.rest.domain.Roles;
import auth.rest.handlers.CustomLogoutSuccessHandler;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Log4j2
@AllArgsConstructor
public class SecurityConfig {
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChainOne(HttpSecurity http) throws Exception {
        log.info("Security filter chain ONE");
        http.csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .sessionFixation().migrateSession()
                    .and()
                .authorizeRequests()
                    .antMatchers("/user/**", "/v3/**").hasAuthority(Roles.USER.name())
                    .antMatchers("/admin/**").hasAuthority(Roles.ADMIN.name())
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .permitAll()
                    .successHandler(jwtAuthenticationSuccessHandler())
                    .defaultSuccessUrl("/home", true)
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .logoutSuccessHandler(customLogoutSuccessHandler)
                    .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//                .httpBasic();
        return http.build();
    }

//    @Bean
//    @Order(1)
//    public SecurityFilterChain securityFilterChainTwo(HttpSecurity http) throws Exception {
//        log.info("Security filter chain TWO");
//        System.out.println("filtr 2");
//        http.csrf().disable()
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                    .sessionFixation().migrateSession()
//                    .and()
//                .authorizeRequests()
//                    .antMatchers("/user/**", "/v3/**").hasAuthority("USER")
//                    .antMatchers("/admin/**").hasAuthority("ADMIN")
//                    .anyRequest().authenticated()
//                    .and()
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChainOne(HttpSecurity http) throws Exception {
//        log.info("Security filter chain ONE");
//        http.csrf().disable()
////                .exceptionHandling()
////                .authenticationEntryPoint(jwtAuthEntryPoint)
////                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .sessionFixation().migrateSession()
//                .and()
//                .authorizeRequests()
//                    .antMatchers("/login").permitAll()
//                    .antMatchers("/user/**", "v3/**").hasAnyAuthority(Roles.USER.name(), Roles.ADMIN.name())
//                    .antMatchers("/admin/**").hasAuthority(Roles.ADMIN.name())
//                    .anyRequest().authenticated()
//                    .and()
//                .formLogin()
//                    .permitAll()
//                    .defaultSuccessUrl("/home", true)
//                    .and()
//                .logout()
//                    .and()
//                .httpBasic();
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    public JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler(){
        return new JwtAuthenticationSuccessHandler();
    }

    @Bean
    FilterRegistrationBean<CustomAuthenticationnFilter> authorizationFilterFilterRegistrationBean(){
        final FilterRegistrationBean<CustomAuthenticationnFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new CustomAuthenticationnFilter());
        filterFilterRegistrationBean.addUrlPatterns("/login/*");
        filterFilterRegistrationBean.setOrder(1);
        return filterFilterRegistrationBean;
    }

    @Bean
    public CustomAuthenticationnFilter customAuthorizationFilter(){
        return new CustomAuthenticationnFilter();
    }
}
