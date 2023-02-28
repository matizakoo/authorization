//package auth.rest.config;
//
//import lombok.NonNull;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@Log4j2
//public class AuthenticationFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
//        log.info("Auth filter X: " + SecurityContextHolder.getContext().getAuthentication());
//
//        filterChain.doFilter(request, response);
//    }
//}
