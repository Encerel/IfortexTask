package com.ifortex.bookservice.BookDao;

import com.ifortex.bookservice.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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



}
