package com.observability.graphql.controller;

import com.observability.graphql.model.Author;
import com.observability.graphql.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @QueryMapping
    public Author authorById(@Argument String id) {
        return authorService.findAuthorById(Long.valueOf(Long.parseLong(id)))
                .orElse(null);
    }

    @QueryMapping
    public List<Author> allAuthors() {
        return authorService.findAllAuthors();
    }

    @MutationMapping
    public Author createAuthor(@Argument Map<String, Object> author) {
        return authorService.createAuthor(author);
    }

    @MutationMapping
    public Author updateAuthor(@Argument String id, @Argument Map<String, Object> author) {
        return authorService.updateAuthor(Long.valueOf(Long.parseLong(id)), author);
    }

    @MutationMapping
    public Boolean deleteAuthor(@Argument String id) {
        return (Boolean) authorService.deleteAuthor(Long.valueOf(Long.parseLong(id)));
    }
}