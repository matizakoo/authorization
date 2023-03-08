package auth.rest.dto;

import auth.rest.domain.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private Integer id;
    private String username;
    private String password;
    private Roles role;
}
