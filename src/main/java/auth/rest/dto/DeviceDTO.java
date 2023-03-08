package auth.rest.dto;

import auth.rest.domain.DeviceType;
import auth.rest.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    private Integer id;
    private String name;
    private DeviceType deviceType;
    private UUID uuid;
    private Users users;
}
