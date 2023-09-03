/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.kazanik.betterreadsdataloader.parser;

import java.util.List;
import pl.kazanik.betterreadsdataloader.author.AuthorEntity;
import pl.kazanik.betterreadsdataloader.book.BookEntity;
import pl.kazanik.betterreadsdataloader.parser.json.JsonParserManager;
import pl.kazanik.betterreadsdataloader.parser.json.factory.IJsonParserFactory;
import pl.kazanik.betterreadsdataloader.parser.json.factory.impl.AuthorsJsonParserFactory;
import pl.kazanik.betterreadsdataloader.parser.json.factory.impl.BooksJsonParserFactory;

/**
 *
 * @author Krysia
 */
public class DataParser {
    
    public List<AuthorEntity> parseAuthorsData(String authorsDumpLocation) {
        System.out.println("###########################");
        System.out.println("Parsing authors...");
        System.out.println("###########################\n");
        IJsonParserFactory<AuthorEntity> authorsParserFactory = new AuthorsJsonParserFactory();
        JsonParserManager<AuthorEntity> authorsParser = authorsParserFactory.createJsonParser();
        List<AuthorEntity> authors = authorsParser.parse(authorsDumpLocation);
        return authors;
    }
    
    public List<BookEntity> parseBooksData(List<AuthorEntity> authors, String worksDumpLocation) {
        System.out.println("\n###########################");
        System.out.println("Parsing books...");
        System.out.println("###########################");
        IJsonParserFactory<BookEntity> booksParserFactory = new BooksJsonParserFactory(authors);
        JsonParserManager<BookEntity> booksParser = booksParserFactory.createJsonParser();
        List<BookEntity> books = booksParser.parse(worksDumpLocation);
        return books;
    }
}
