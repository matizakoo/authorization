package auth.rest.service;


import auth.rest.domain.Roles;
import auth.rest.domain.Users;
import auth.rest.dto.UsersDTO;
import auth.rest.mapper.UsersMapper;
import auth.rest.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("test")
@SpringBootTest
class UsersServiceImplTest {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UsersServiceImpl usersServiceImpl;

    @DisplayName("Save new users")
    @Test
    void saveNewUsers(){
        List<Users> usersList = Arrays.asList(
                new Users(1,"Karol", "Karol", Roles.USER, new HashSet<>()),
                new Users(2, "Giga kluch", "123", Roles.USER, new HashSet<>()),
                new Users(3, "Zdrapa admin", "Admin",Roles.ADMIN, new HashSet<>())
        );
        usersServiceImpl.saveAll(usersList);
        List<UsersDTO> usersDTOList = usersServiceImpl.findAll();
        List<UsersDTO> expectedList = Arrays.asList(
                new Users(1,"Karol", "Karol", Roles.USER, new HashSet<>()),
                new Users(2, "Giga kluch", "123", Roles.USER, new HashSet<>()),
                new Users(3, "Zdrapa admin", "Admin", Roles.ADMIN, new HashSet<>())
        ).stream().map(users -> {
                    UsersDTO dto = usersMapper.usersToUsersDto(users);
                    dto.setPassword(null);
                    return dto;
                }).collect(Collectors.toList());
        assertThat(usersDTOList).isEqualTo(expectedList);
    }

    @DisplayName("Find users by role: ADMIN")
    @Test
    void findAllByRoleAdmin() {
        List<Users> usersList = Arrays.asList(
                new Users(1, "Karol", "Karol", Roles.USER, new HashSet<>()),
                new Users(2, "Zdrapa admin", "Admin", Roles.ADMIN, new HashSet<>()),
                new Users(3, "Giga kluch", "123", Roles.USER, new HashSet<>())
        );

        usersServiceImpl.saveAll(usersList);

        List<UsersDTO> usersDTOList = usersServiceImpl.findAllByRole(Roles.ADMIN);
        List<UsersDTO> expectedList = Arrays.asList(
                new Users(2, "Zdrapa admin", null, Roles.ADMIN, new HashSet<>())
        ).stream().map(usersMapper::usersToUsersDto)
                .collect(Collectors.toList());
        assertThat(usersDTOList).isEqualTo(expectedList);
    }

    @DisplayName("Find users by role: USER")
    @Test
    void findAllByRoleUser() {
        List<Users> usersList = Arrays.asList(
                new Users(1, "Karol", "Karol", Roles.USER, new HashSet<>()),
                new Users(2, "Zdrapa admin", "Admin", Roles.ADMIN, new HashSet<>()),
                new Users(3, "Giga kluch", "123", Roles.USER, new HashSet<>())
        );

        usersServiceImpl.saveAll(usersList);

        List<UsersDTO> usersDTOList = usersServiceImpl.findAllByRole(Roles.USER);
        List<UsersDTO> expectedList = Arrays.asList(
                        new Users(1, "Karol", null, Roles.USER, new HashSet<>()),
                        new Users(3, "Giga kluch", null, Roles.USER, new HashSet<>())
                ).stream().map(usersMapper::usersToUsersDto)
                .collect(Collectors.toList());
        assertThat(usersDTOList).isEqualTo(expectedList);
    }
}