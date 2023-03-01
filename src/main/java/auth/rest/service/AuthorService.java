package auth.rest.service;

import auth.rest.domain.Author;
import auth.rest.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
    List<Author> findAllNoDto();
}
