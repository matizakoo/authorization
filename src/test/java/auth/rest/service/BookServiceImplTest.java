package auth.rest.service;

import auth.rest.domain.Author;
import auth.rest.domain.Book;
import auth.rest.dto.AuthorDTO;
import auth.rest.dto.BookDTO;
import auth.rest.mapper.AuthorMapper;
import auth.rest.mapper.BookMapper;
import auth.rest.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class BookServiceImplTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookServiceImpl bookServiceImpl;
    @Autowired
    private AuthorServiceImpl authorServiceImpl;
    @Test
    @DisplayName("findAll Books method")
    void findAll() {
        List<Author> authors = Arrays.asList(
                new Author(1, "Author 1", new HashSet<>()),
                new Author(2, "Author 2", new HashSet<>()),
                new Author(3, "Author 3", new HashSet<>())
        );
        authorServiceImpl.saveAll(authors);

        List<Book> books = Arrays.asList(
                new Book(1, "Book 1", new Author(1, "Author 1", new HashSet<>())),
                new Book(2, "Book 2", new Author(2, "Author 2", new HashSet<>())),
                new Book(3, "Book 3", new Author(3, "Author 3", new HashSet<>()))
        );
        bookServiceImpl.saveAll(books);

        List<BookDTO> result = bookServiceImpl.findAll();
        List<BookDTO> expectedList = List.copyOf(books)
                .stream()
                .map(book -> {
                    BookDTO bookDTO = bookMapper.bookToBookDto(book);
                    bookDTO.getAuthor().setBooks(bookServiceImpl.findByAuthorId(bookDTO.getId())
                            .stream()
                            .map(bookMapper::bookDtoToBook)
                            .collect(Collectors.toSet()));
                    return bookDTO;
                })
                .collect(Collectors.toList());

        List<BookDTO> expectedList2 = List.copyOf(books)
                .stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedList2);
    }
}