package auth.rest.controller;

import auth.rest.domain.Author;
import auth.rest.dto.AuthorDTO;
import auth.rest.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/author")
@AllArgsConstructor
@Log4j2
public class AuthorController {
    private final AuthorService authorService;
    //DTO - print relations between 2 columns, able to see books
    @GetMapping(value = "/dto")
    public List<AuthorDTO> authors(){
        return authorService.findAll();
    }

    //No DTO - print only authors without books
    @GetMapping(value = "/nodto")
    public List<Author> authorsNoDto(){
        return authorService.findAllNoDto();
    }
}
