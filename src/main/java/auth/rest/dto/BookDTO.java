package auth.rest.dto;

import auth.rest.domain.Author;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Integer id;
    private String name;
    private Author author;
}
