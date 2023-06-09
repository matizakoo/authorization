package auth.rest.service;

import auth.rest.domain.Author;
import auth.rest.dto.AuthorDTO;
import auth.rest.mapper.AuthorMapper;
import auth.rest.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::authorToAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Author> findAllNoDto(){
        return authorRepository.findAll();
    }

    @Transactional
    @Override
    public void saveAll(List<Author> authors) {
        authorRepository.saveAll(authors);
    }


}
