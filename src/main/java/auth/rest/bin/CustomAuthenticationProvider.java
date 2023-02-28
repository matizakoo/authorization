//package auth.rest.bin;
//
//import lombok.extern.log4j.Log4j;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import java.util.ArrayDeque;
//
//@Component
//@Log4j2
//@EnableWebSecurity
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        log.info("Authentication");
//        String name = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        if(name != null && password != null)
//            return new UsernamePasswordAuthenticationToken(name, password, new ArrayDeque<>());
//        else
//            return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationFilter.class);
//    }
//}
