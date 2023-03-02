package auth.rest.service;

import auth.rest.dto.BookDTO;
import auth.rest.mapper.BookMapper;
import auth.rest.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    @Override
    public List<BookDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }
}
