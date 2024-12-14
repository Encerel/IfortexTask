package com.ifortex.bookservice.BookDao;

import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDao {

    @PersistenceContext
    private EntityManager entityManager;
    public static final String FIND_ALL_HQL = "FROM Book";

    public List<Book> findAll() {
        return entityManager.createQuery(FIND_ALL_HQL, Book.class).getResultList();
    }

    public List<Book> findByCriteria(SearchCriteria searchCriteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        Predicate predicate = cb.conjunction();

        if (searchCriteria.getTitle() != null && !searchCriteria.getTitle().isBlank()) {
            String title = searchCriteria.getTitle().strip();
            predicate = cb.and(predicate, cb.like(book.get("title"), "%" + title + "%"));
        }

        if (searchCriteria.getDescription() != null && !searchCriteria.getDescription().isBlank()) {
            String description = searchCriteria.getDescription().strip();
            predicate = cb.and(predicate, cb.like(book.get("description"), "%" + description + "%"));
        }

        if (searchCriteria.getAuthor() != null && !searchCriteria.getAuthor().isBlank()) {
            String author = searchCriteria.getAuthor().strip();
            predicate = cb.and(predicate, cb.like(book.get("author"), "%" + author + "%"));
        }

        if (searchCriteria.getYear() != null) {
            Expression<Integer> publicationYear = cb.function("date_part", Integer.class,
                    cb.literal("year"), book.get("publicationDate"));
            predicate = cb.and(predicate, cb.equal(publicationYear, searchCriteria.getYear()));
        }

        if (searchCriteria.getGenre() != null && !searchCriteria.getGenre().isBlank()) {
            String genre = searchCriteria.getGenre().strip();
            Expression<String> genresAsString = cb.function("array_to_string", String.class,
                    book.get("genres"), cb.literal(",")
            );
            predicate = cb.and(predicate, cb.like(genresAsString, "%" + genre + "%"));

        }

        query.select(book).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }


}
