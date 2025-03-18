package com.observability.graphql.service;

import com.observability.graphql.model.Author;
import com.observability.graphql.repository.AuthorRepository;
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
public class AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public Author createAuthor(Map<String, Object> authorInput) {
        Author author = new Author();
        author.setFirstName((String) authorInput.get("firstName"));
        author.setLastName((String) authorInput.get("lastName"));

        String birthDateStr = (String) authorInput.get("birthDate");
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            LocalDate birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_DATE);
            author.setBirthDate(birthDate);
        }

        return authorRepository.save(author);
    }

    @Transactional
    public Author updateAuthor(Long id, Map<String, Object> authorInput) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        if (authorInput.containsKey("firstName")) {
            author.setFirstName((String) authorInput.get("firstName"));
        }

        if (authorInput.containsKey("lastName")) {
            author.setLastName((String) authorInput.get("lastName"));
        }

        if (authorInput.containsKey("birthDate")) {
            String birthDateStr = (String) authorInput.get("birthDate");
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                LocalDate birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_DATE);
                author.setBirthDate(birthDate);
            } else {
                author.setBirthDate(null);
            }
        }

        return authorRepository.save(author);
    }

    @Transactional
    public boolean deleteAuthor(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}