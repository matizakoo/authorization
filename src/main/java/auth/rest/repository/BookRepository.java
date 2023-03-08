package auth.rest.repository;

import auth.rest.domain.Book;
import auth.rest.dto.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "select * from book where author_id=:id", nativeQuery = true)
    Set<Book> findAllByAuthorId(Integer id);
}
