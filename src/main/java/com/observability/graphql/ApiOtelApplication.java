package com.observability.graphql;

import com.observability.graphql.config.FeignConfig;
import com.observability.graphql.model.Author;
import com.observability.graphql.model.Book;
import com.observability.graphql.repository.AuthorRepository;
import com.observability.graphql.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.observability.graphql.client"}, defaultConfiguration = FeignConfig.class)
public class ApiOtelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiOtelApplication.class, args);
	}

	/**
	 * Inicializa la base de datos con algunos datos de ejemplo
	 */
	@Bean
	public CommandLineRunner initDatabase(AuthorRepository authorRepository, BookRepository bookRepository) {
		return args -> {
			// Crear autores
			Author author1 = Author.builder()
					.firstName("Gabriel")
					.lastName("García Márquez")
					.birthDate(LocalDate.of(1927, 3, 6))
					.build();

			Author author2 = Author.builder()
					.firstName("J.K.")
					.lastName("Rowling")
					.birthDate(LocalDate.of(1965, 7, 31))
					.build();

			Author author3 = Author.builder()
					.firstName("George")
					.lastName("Orwell")
					.birthDate(LocalDate.of(1903, 6, 25))
					.build();

			// Guardar autores
			authorRepository.saveAll(Arrays.asList(author1, author2, author3));

			// Crear libros
			Book book1 = Book.builder()
					.title("Cien años de soledad")
					.isbn("9780307474728")
					.pageCount(417)
					.publishedDate(LocalDate.of(1967, 5, 30))
					.genre("Realismo mágico")
					.author(author1)
					.build();

			Book book2 = Book.builder()
					.title("El amor en los tiempos del cólera")
					.isbn("9780307387264")
					.pageCount(368)
					.publishedDate(LocalDate.of(1985, 3, 10))
					.genre("Novela romántica")
					.author(author1)
					.build();

			Book book3 = Book.builder()
					.title("Harry Potter y la piedra filosofal")
					.isbn("9788478886456")
					.pageCount(309)
					.publishedDate(LocalDate.of(1997, 6, 26))
					.genre("Fantasía")
					.author(author2)
					.build();

			Book book4 = Book.builder()
					.title("1984")
					.isbn("9780451524935")
					.pageCount(328)
					.publishedDate(LocalDate.of(1949, 6, 8))
					.genre("Distopía")
					.author(author3)
					.build();

			// Guardar libros
			bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4));
		};
	}
}