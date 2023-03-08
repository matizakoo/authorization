package auth.rest.service;

import auth.rest.domain.Author;
import auth.rest.dto.AuthorDTO;
import auth.rest.mapper.AuthorMapper;
import auth.rest.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorMapper authorMapper;
    @InjectMocks
    private AuthorServiceImpl authorServiceImpl;

    @DisplayName("findAll Authors method")
    @Test
    void findAll() {
        List<Author> authors = Arrays.asList(
                new Author(1, "Zak", new HashSet<>()),
                new Author(2, "Zaczur", new HashSet<>()),
                new Author(3, "Zakikrakowskie", new HashSet<>())
        );

        authorServiceImpl.saveAll(authors);

        when(authorRepository.findAll()).thenReturn(authors);
        List<AuthorDTO> result = authorServiceImpl.findAll();

        Assertions.assertEquals(3, result.size());
    }
}