package com.observability.graphql.controller;

import com.observability.graphql.model.Book;
import com.observability.graphql.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @QueryMapping
    public Book bookById(@Argument String id) {
        return bookService.findBookById(Long.parseLong(id))
                .orElse(null);
    }

    @QueryMapping
    public List<Book> allBooks() {
        return bookService.findAllBooks();
    }

    @QueryMapping
    public List<Book> booksByTitle(@Argument String title) {
        return bookService.findBooksByTitle(title);
    }

    @MutationMapping
    public Book createBook(@Argument Map<String, Object> book) {
        return bookService.createBook(book);
    }

    @MutationMapping
    public Book updateBook(@Argument String id, @Argument Map<String, Object> book) {
        return bookService.updateBook(Long.parseLong(id), book);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument String id) {
        return bookService.deleteBook(Long.parseLong(id));
    }
}