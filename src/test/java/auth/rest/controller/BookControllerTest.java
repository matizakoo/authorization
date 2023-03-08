package auth.rest.controller;

import auth.rest.domain.Author;
import auth.rest.domain.Book;
import auth.rest.dto.BookDTO;
import auth.rest.service.AuthorService;
import auth.rest.service.AuthorServiceImpl;
import auth.rest.service.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthorServiceImpl authorService;

    @DisplayName("Check http status when want to return books")
    @Test
    void getBooks() throws Exception {
        Author author = new Author(1, "Author 1", new HashSet<>());
        authorService.saveAll(List.of(author));

        BookDTO bookDTO = new BookDTO(1, "Book 1", new Author(1, "Author 1", new HashSet<>()));
        bookService.save(bookDTO);
        System.out.println("SIZE: " + bookService.findAll().size());
        mockMvc.perform(get( "/user/book/dto")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name[0]").exists());

//        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.name[0]").value("Book 1"));
    }

    @DisplayName("Check http status when want to return books 2")
    @Test
    void getBooks2() throws Exception {
        BookDTO bookDTO = new BookDTO(1, "Book 1", new Author(1, "Author 1", new HashSet<>()));
        bookService.save(bookDTO);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/book/dto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        List<BookDTO> books = objectMapper.readValue(responseJson, new TypeReference<List<BookDTO>>() {});
        assertEquals(1, books.size());
        assertEquals("Book 1", books.get(0).getName());
    }
}