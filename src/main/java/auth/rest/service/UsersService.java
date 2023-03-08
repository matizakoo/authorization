package auth.rest.service;

import auth.rest.domain.Roles;
import auth.rest.domain.Users;
import auth.rest.dto.UsersDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {
    List<UsersDTO> findAllByRole(Roles role);
    void saveAll(List<Users> users);
    List<UsersDTO> findAll();
}
