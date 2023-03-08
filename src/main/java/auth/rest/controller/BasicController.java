package auth.rest.controller;

import auth.rest.config.LoggerFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BasicController {
    @GetMapping("/admin/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/user/user")
    public String user(){
        return "user";
    }

    @GetMapping("/hi")
    public String hi(){
        return "hi";
    }

    @GetMapping("/home")
    public String home() {
        log.info("home", LoggerFilter.APPLICATION);
        return  BookController.url + "/dto ,/{id} " + "<br>" +
                UsersController.url + "/findby/{role}" + "<br>" +
                AuthorController.url + " /dto, /nodto" + "<br>" +
                "<a href=\"http://localhost:8080/swagger-ui/index.html#/\">swagger</a>";
    }
}
