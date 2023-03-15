package auth.rest.config;

import auth.rest.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtAuthenticationFilter");
        String tokenFromCookie = getJwtFromCookie(request);
        if(StringUtils.hasText(tokenFromCookie) && jwtGenerator.validateToken(tokenFromCookie)) {
            String username = jwtGenerator.getUsernameFromJWT(tokenFromCookie);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            List<String> roles = jwtGenerator.getRolesFromJwt(tokenFromCookie);
            Set<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
            log.info("User has role: " + userDetails.getUsername() + " " + userDetails.getAuthorities());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
//        else {
//            String username = request.getParameter("username");
//            String password = request.getParameter("password");
//            System.out.println("Logowanie: " + username + "   " + password);
//            if(username == null || password == null){
//                filterChain.doFilter(request, response);
//                System.out.println("check");
//                return;
//            }
//            System.out.println("check 2");
//            try {
//                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//                if(passwordEncoder.matches(password, userDetails.getPassword())){
//                    UsernamePasswordAuthenticationToken authRequest =
//                            new UsernamePasswordAuthenticationToken(
//                                    userDetails,
//                                    null,
//                                    userDetails.getAuthorities());
//                    authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authRequest);
//
//                    String newToken = jwtGenerator.generateToken(authRequest);
//                    CookieService jwtCookie = new CookieService("jwt", newToken, true, request.isSecure(),
//                            "/", jwtGenerator.getExpirationInMillis());
//
//                    response.addCookie(jwtCookie);
//                    filterChain.doFilter(request, response);
//                    return;
//                }
//            }catch (UsernameNotFoundException e){
//                filterChain.doFilter(request, response);
//            }
//        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromCookie(HttpServletRequest request){
        Optional<Cookie> optionalCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .findFirst();
        return optionalCookie.map(Cookie::getValue).orElse(null);
    }
}
