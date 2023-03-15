package auth.rest.config;


import auth.rest.service.CookieService;
import auth.rest.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;


@Slf4j
public class CustomAuthenticationnFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtGenerator jwtGenerator;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("CustomAuthenticationnFilter");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String tokenFromCookie = getJwtFromCookie(request);
        if(tokenFromCookie != null && jwtGenerator.validateToken(tokenFromCookie)){
            filterChain.doFilter(request, response);
            return;
        }

        if(username == null || password == null){
            filterChain.doFilter(request, response);
            return;
        }
        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                UsernamePasswordAuthenticationToken authRequest =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authRequest);

                String newToken = jwtGenerator.generateToken(authRequest);
                CookieService jwtCookie = new CookieService("jwt", newToken, true,
                        request.isSecure(), "/", jwtGenerator.getExpirationInMillis());

                response.addCookie(jwtCookie);
                filterChain.doFilter(request, response);
            }
        }
        catch (UsernameNotFoundException e){
            filterChain.doFilter(request, response);
        }
    }

    private String getJwtFromCookie(HttpServletRequest request){
        Optional<Cookie> optionalCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .findFirst();
        return optionalCookie.map(Cookie::getValue).orElse(null);
    }
}
