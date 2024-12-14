package com.ifortex.bookservice.BookDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    @PersistenceContext
    private EntityManager entityManager;

}
