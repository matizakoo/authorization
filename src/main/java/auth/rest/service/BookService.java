package auth.rest.service;

import auth.rest.dto.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> findAll();
}
