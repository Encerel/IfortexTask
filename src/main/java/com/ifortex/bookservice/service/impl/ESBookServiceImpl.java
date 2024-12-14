package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.BookDao.BookDao;
import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;
import com.ifortex.bookservice.service.BookService;
import com.ifortex.bookservice.util.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

// Attention! It is FORBIDDEN to make any changes in this file!
@Service
public class ESBookServiceImpl implements BookService {

  @Override
  public Map<String, Long> getBooks() {
    var context = ApplicationContextProvider.getApplicationContext();
    var bookDao = context.getBean(BookDao.class);
    var books = bookDao.findAll();
    var countBooksByGenre = new HashMap<String, Long>();
    for (var book : books) {
      for (var genre : book.getGenres()) {
        countBooksByGenre.compute(genre, (k, frequency) -> frequency == null ? 1 : frequency + 1);
      }
    }
    return countBooksByGenre.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(
                    Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (v1, v2) -> v1 - v2,
                            LinkedHashMap::new
                    )
            );
  }

  @Override
  public List<Book> getAllByCriteria(SearchCriteria searchCriteria) {
    var context = ApplicationContextProvider.getApplicationContext();
    var bookDao = context.getBean(BookDao.class);
    return bookDao.findByCriteria(searchCriteria);
  }
}
