package auth.rest.controller;

import auth.rest.domain.Book;
import auth.rest.dto.BookDTO;
import auth.rest.repository.BookRepository;
import auth.rest.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/book")
@AllArgsConstructor
@Log4j2
public class BookController {
    private final BookService bookService;

    @GetMapping(value = "/dto")
    public List<BookDTO> books(){
        return bookService.findAll();
    }
}
