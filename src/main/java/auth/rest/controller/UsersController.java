package auth.rest.controller;

import auth.rest.domain.Roles;
import auth.rest.domain.Users;
import auth.rest.dto.UsersDTO;
import auth.rest.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(UsersController.url)
@Slf4j
public class UsersController {
    public static final String url = "/user/users";
    private final UsersService usersService;
    @GetMapping("/findby/{role}")
    public List<UsersDTO> getUsersByRole(@PathVariable("role") String role){
        log.info("ROLE: " + role);
        return usersService.findAllByRole(Roles.valueOf(role.toUpperCase()));
    }

    @GetMapping("/findall")
    public List<UsersDTO> findAll(){
        return usersService.findAll();
    }
}
