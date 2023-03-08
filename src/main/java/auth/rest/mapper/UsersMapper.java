package auth.rest.mapper;

import auth.rest.domain.Users;
import auth.rest.dto.UsersDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UsersMapper {
    Users usersDtoToUsers(UsersDTO usersDTO);
    UsersDTO usersToUsersDto(Users users);
}
