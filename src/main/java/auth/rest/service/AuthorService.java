package auth.rest.service;

import auth.rest.domain.Author;
import auth.rest.dto.AuthorDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface AuthorService {
    List<AuthorDTO> findAll();
    List<Author> findAllNoDto();
    void saveAll(List<Author> authors);
}
