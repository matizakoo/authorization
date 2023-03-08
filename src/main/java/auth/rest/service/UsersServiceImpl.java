package auth.rest.service;



import auth.rest.domain.Roles;
import auth.rest.domain.Users;
import auth.rest.dto.UsersDTO;
import auth.rest.mapper.UsersMapper;
import auth.rest.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersMapper usersMapper;
    private final UsersRepository usersRepository;
    @Override
    public List<UsersDTO> findAllByRole(Roles role) {
        return usersRepository.findAllByRole(role)
                .stream()
                .map(users -> {
                    UsersDTO dto = usersMapper.usersToUsersDto(users);
                    dto.setPassword(null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveAll(List<Users> users) {
        usersRepository.saveAll(users);
    }

    @Override
    public List<UsersDTO> findAll() {
        return usersRepository.findAll()
                .stream()
                .map(users -> {
                    UsersDTO dto = usersMapper.usersToUsersDto(users);
                    dto.setPassword(null);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
