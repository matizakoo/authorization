package auth.rest.service;


import auth.rest.domain.Book;
import auth.rest.dto.BookDTO;
import auth.rest.mapper.BookMapper;
import auth.rest.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
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

    @Transactional
    @Override
    public void saveAll(List<Book> books) {
        bookRepository.saveAll(books);
    }

    @Transactional
    @Override
    public void save(BookDTO bookDTO) {
        bookRepository.save(bookMapper.bookDtoToBook(bookDTO));
    }

    @Transactional
    @Override
    public Set<BookDTO> findByAuthorId(Integer id) {
        return bookRepository.findAllByAuthorId(id)
                .stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toSet());
    }
}
