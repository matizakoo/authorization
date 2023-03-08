package auth.rest.domain;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Devices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;
}
