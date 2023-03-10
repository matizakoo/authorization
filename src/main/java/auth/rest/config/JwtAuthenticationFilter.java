package auth.rest.config;

import auth.rest.service.CookieService;
import auth.rest.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.Optional;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);
        System.out.println("JwtAuthenticationFilter token: " + token);
        String tokenFromCookie = getJwtFromCookie(request);
        System.out.println("Token from cookie " + tokenFromCookie);
        if(StringUtils.hasText(tokenFromCookie) && jwtGenerator.validateToken(tokenFromCookie)) {
            String username = jwtGenerator.getUsernameFromJWT(tokenFromCookie);
            System.out.println("Test czy jest token a powinien być");
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
            return;
        }else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            System.out.println("Logowanie: " + username + "   " + password);
            if(username == null || password == null){
                filterChain.doFilter(request, response);
                System.out.println("check");
                return;
            }
            System.out.println("check 2");
            try {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if(passwordEncoder.matches(password, userDetails.getPassword())){
                    UsernamePasswordAuthenticationToken authRequest =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authRequest);

                    String newToken = jwtGenerator.generateToken(authRequest);
//                    response.setHeader("Authorization", "Bearer " + newToken);
////                    response.setContentType("application/json");
//                    response.getWriter().write("{ \"token\": \"" + newToken + "\" }");
//                    response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                    CookieService jwtCookie = new CookieService(
                            "jwt",
                            newToken,
                            true,
                            request.isSecure(),
                            "/",
                            jwtGenerator.getExpirationInMillis());

                    response.addCookie(jwtCookie);

                    filterChain.doFilter(request, response);
                    return;
                }
            }catch (UsernameNotFoundException e){
                System.out.println("chuja");
                filterChain.doFilter(request, response);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getJwtFromCookie(HttpServletRequest request){
        Optional<Cookie> optionalCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .findFirst();
        return optionalCookie.map(Cookie::getValue).orElse(null);
    }
}
