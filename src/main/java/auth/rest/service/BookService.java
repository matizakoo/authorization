package auth.rest.service;

import auth.rest.domain.Book;
import auth.rest.dto.BookDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public interface BookService {
    List<BookDTO> findAll();
    void saveAll(List<Book> books);
    void save(BookDTO bookDTO);
    Set<BookDTO> findByAuthorId(Integer id);
}
