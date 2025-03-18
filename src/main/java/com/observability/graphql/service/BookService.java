package com.observability.graphql.service;

import com.observability.graphql.model.Author;
import com.observability.graphql.model.Book;
import com.observability.graphql.repository.AuthorRepository;
import com.observability.graphql.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Transactional
    public Book createBook(Map<String, Object> bookInput) {
        Book book = new Book();
        book.setTitle((String) bookInput.get("title"));
        if (bookInput.containsKey("isbn")) {
            book.setIsbn((String) bookInput.get("isbn"));
        }
        if (bookInput.containsKey("pageCount")) {
            book.setPageCount((Integer) bookInput.get("pageCount"));
        }
        if (bookInput.containsKey("publishedDate")) {
            String publishedDateStr = (String) bookInput.get("publishedDate");
            if (publishedDateStr != null && !publishedDateStr.isEmpty()) {
                LocalDate publishedDate = LocalDate.parse(publishedDateStr, DateTimeFormatter.ISO_DATE);
                book.setPublishedDate(publishedDate);
            }
        }
        if (bookInput.containsKey("genre")) {
            book.setGenre((String) bookInput.get("genre"));
        }
        if (bookInput.containsKey("authorId")) {
            String authorIdStr = (String) bookInput.get("authorId");
            if (authorIdStr != null && !authorIdStr.isEmpty()) {
                Long authorId = (Long) Long.parseLong(authorIdStr);
                Author author = authorRepository.findById(authorId)
                        .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));
                book.setAuthor(author);
            }
        }
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Map<String, Object> bookInput) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        if (bookInput.containsKey("title")) {
            book.setTitle((String) bookInput.get("title"));
        }
        if (bookInput.containsKey("isbn")) {
            book.setIsbn((String) bookInput.get("isbn"));
        }
        if (bookInput.containsKey("pageCount")) {
            book.setPageCount((Integer) bookInput.get("pageCount"));
        }
        if (bookInput.containsKey("publishedDate")) {
            String publishedDateStr = (String) bookInput.get("publishedDate");
            if (publishedDateStr != null && !publishedDateStr.isEmpty()) {
                LocalDate publishedDate = LocalDate.parse(publishedDateStr, DateTimeFormatter.ISO_DATE);
                book.setPublishedDate(publishedDate);
            } else {
                book.setPublishedDate(null);
            }
        }
        if (bookInput.containsKey("genre")) {
            book.setGenre((String) bookInput.get("genre"));
        }

        if (bookInput.containsKey("authorId")) {
            String authorIdStr = (String) bookInput.get("authorId");
            if (authorIdStr != null && !authorIdStr.isEmpty()) {
                Long authorId = (Long) Long.parseLong(authorIdStr);
                Author author = authorRepository.findById(authorId)
                        .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));
                book.setAuthor(author);
            } else {
                book.setAuthor(null);
            }
        }

        return bookRepository.save(book);
    }

    @Transactional
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}