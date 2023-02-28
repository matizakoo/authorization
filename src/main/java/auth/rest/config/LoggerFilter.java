package auth.rest.config;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Component
@Log4j2
public class LoggerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("doFilterInternal");
        String requestUsername;
        try{
            requestUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        }catch (Exception e){
            requestUsername = "unknown";
        }
        log.info("Received request from: " + requestUsername);
        log.info("Method: " + request.getMethod());
        log.info("URL: " + request.getRequestURL());
        log.info("Headers: " + request.getHeaderNames());

        filterChain.doFilter(request, response);

        log.info("Responded with status: " + response.getStatus());
        log.info("Headers: " + response.getHeaderNames());

        try(FileWriter writer = new FileWriter("src/main/logs/logs.txt", true)){
            writer.write(
                    "Username:\t" + requestUsername + "\n" +
                    "Method:\t\t" + request.getMethod() + "\n" +
                    "URL:\t\t" + request.getRequestURL() + "\n" +
                    "Headers:\t" + request.getHeaderNames() + "\n" +
                    "Status:\t\t" + response.getStatus() + "\n" +
                    "Headers:\t" + response.getHeaderNames() + "\n\n"
            );
        }
    }
}
