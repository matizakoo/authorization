package auth.rest.config;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class LoggerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("doFilterInternal");
        String id = UUID.randomUUID().toString();
        String requestUsername;
        try{
            requestUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        }catch (Exception e){
            requestUsername = "unknown";
        }
        log.info("Request ID: " + id, BUSINESS);
        log.info("Received request from: " + requestUsername, BUSINESS);
        log.info("Method: " + request.getMethod(), BUSINESS);
        log.info("URL: " + request.getRequestURL(), BUSINESS);
        log.info("Headers: " + request.getHeaderNames(), BUSINESS);

        filterChain.doFilter(request, response);

        log.info("Responded with status: " + response.getStatus(), APPLICATION);
        log.info("Headers: " + response.getHeaderNames(), APPLICATION);

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

    public static final Marker BUSINESS = MarkerFactory.getMarker("BUSINESS");
    public static final org.slf4j.Marker APPLICATION = MarkerFactory.getMarker("APPLICATION");
}
