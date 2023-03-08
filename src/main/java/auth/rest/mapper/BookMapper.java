package auth.rest.mapper;


import auth.rest.domain.Book;
import auth.rest.dto.BookDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface BookMapper {
    BookDTO bookToBookDto(Book book);
    auth.rest.domain.Book bookDtoToBook(BookDTO book);
}
