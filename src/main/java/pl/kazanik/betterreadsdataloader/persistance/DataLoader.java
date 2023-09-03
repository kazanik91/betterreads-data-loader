/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.kazanik.betterreadsdataloader.persistance;

import java.util.List;
import org.springframework.stereotype.Component;
import pl.kazanik.betterreadsdataloader.author.AuthorEntity;
import pl.kazanik.betterreadsdataloader.author.AuthorRepository;
import pl.kazanik.betterreadsdataloader.book.BookEntity;
import pl.kazanik.betterreadsdataloader.book.BookRepository;

/**
 *
 * @author Krysia
 */
@Component
public class DataLoader {
    
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public DataLoader(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }
    
    public void persistAuthors(List<AuthorEntity> authors) {
        authors.forEach(author -> {
            authorRepository.save(author);
        });
    }
    
    public void persistBooks(List<BookEntity> books) {
        books.forEach(book -> {
            bookRepository.save(book);
        });
    }
}
