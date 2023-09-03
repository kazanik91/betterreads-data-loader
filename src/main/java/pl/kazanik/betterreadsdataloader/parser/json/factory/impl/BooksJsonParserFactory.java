/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.kazanik.betterreadsdataloader.parser.json.factory.impl;

import java.util.List;
import pl.kazanik.betterreadsdataloader.author.AuthorEntity;
import pl.kazanik.betterreadsdataloader.book.BookEntity;
import pl.kazanik.betterreadsdataloader.parser.json.IJsonDumpParser;
import pl.kazanik.betterreadsdataloader.parser.json.JsonParserManager;
import pl.kazanik.betterreadsdataloader.parser.json.factory.IJsonParserFactory;
import pl.kazanik.betterreadsdataloader.parser.json.impl.BooksJsonDumpParser;

/**
 *
 * @author Krysia
 */
public class BooksJsonParserFactory implements IJsonParserFactory<BookEntity>{

    private List<AuthorEntity> authors;

    public BooksJsonParserFactory(List<AuthorEntity> authors) {
        this.authors = authors;
    }
    
    @Override
    public JsonParserManager<BookEntity> createJsonParser() {
        IJsonDumpParser<BookEntity> booksDumpParser = new BooksJsonDumpParser(authors);
        JsonParserManager<BookEntity> booksParser = new JsonParserManager<>(booksDumpParser);
        return booksParser;
    }
    
}
