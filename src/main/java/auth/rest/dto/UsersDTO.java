package auth.rest.dto;

import auth.rest.domain.Devices;
import auth.rest.domain.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private Integer id;
    private String username;
    private String password;
    private Roles role;
    private Set<Devices> devices;
}
