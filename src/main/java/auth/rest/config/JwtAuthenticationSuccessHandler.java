package auth.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtGenerator jwtGenerator;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String jwtToken = jwtGenerator.generateToken(authentication);
        response.setHeader("Authorization", "Bearer "+ jwtToken);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
