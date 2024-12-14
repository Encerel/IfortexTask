package com.ifortex.bookservice.BookDao;

import com.ifortex.bookservice.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    @PersistenceContext
    private EntityManager entityManager;
    private static final String FIND_BY_OLDEST_ROMANCE_BOOK_AND_RECENT_REGISTRATION_SQL = """
            SELECT m.id, m.name, m.membership_date
                        FROM members m
                        JOIN member_books mb ON m.id = mb.member_id
                        JOIN books b ON mb.book_id = b.id AND book_id = (SELECT b.id
                                                                         FROM books b
                                                                         WHERE array_position(b.genre, 'Romance') IS NOT NULL
                                                                         ORDER BY b.publication_date
                                                                         LIMIT 1)
                        ORDER BY m.membership_date DESC
                        LIMIT 1
            """;

    public Member findMemberByOldestRomanceBookAndRecentRegistration() {
        return (Member) entityManager.createNativeQuery(FIND_BY_OLDEST_ROMANCE_BOOK_AND_RECENT_REGISTRATION_SQL, Member.class).getSingleResult();
    }
}
