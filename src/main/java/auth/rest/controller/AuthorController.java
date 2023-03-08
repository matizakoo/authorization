package auth.rest.controller;

import auth.rest.config.LoggerFilter;
import auth.rest.domain.Author;
import auth.rest.dto.AuthorDTO;
import auth.rest.service.AuthorService;
import auth.rest.service.AuthorServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(AuthorController.url)
@AllArgsConstructor
@Slf4j
public class AuthorController {
    public static final String url = "/user/author";
    private final AuthorServiceImpl authorService;
    //DTO - print relations between 2 columns, able to see books
    @GetMapping(value = "/dto")
    public List<AuthorDTO> authors(){
        log.info("get authors dtos", LoggerFilter.BUSINESS);
        return authorService.findAll();
    }

    @GetMapping(value = "/nodto")
    public List<Author> authorsNoDto(){
        log.info("get authors entities", LoggerFilter.BUSINESS);
        return authorService.findAllNoDto();
    }
}
