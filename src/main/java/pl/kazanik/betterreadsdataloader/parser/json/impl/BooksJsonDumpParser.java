/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.kazanik.betterreadsdataloader.parser.json.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.kazanik.betterreadsdataloader.author.AuthorEntity;
import pl.kazanik.betterreadsdataloader.book.BookEntity;
import pl.kazanik.betterreadsdataloader.parser.json.exception.JsonDumpParseException;
import pl.kazanik.betterreadsdataloader.parser.json.IJsonDumpParser;

/**
 *
 * @author Krysia
 */
public class BooksJsonDumpParser implements IJsonDumpParser<BookEntity>{

    private List<AuthorEntity> authors;

    public BooksJsonDumpParser(List<AuthorEntity> authors) {
        this.authors = authors;
    }
    
    public List<String> parseAuthorIds(JSONArray authorsArray) {
        List<String> authorIds = new ArrayList<>();
        for (int i = 0; i < authorsArray.length(); i++) {
            try {
                JSONObject jsonObject = authorsArray.getJSONObject(i);
                JSONObject authorObject = jsonObject.getJSONObject("author");
                String authorKey = authorObject.getString("key");
                authorIds.add(authorKey.replace("/authors/", ""));
            }
            catch (JSONException jsonEx) {
                jsonEx.printStackTrace();
            }
        }
        return authorIds;
    }
    
    public Optional<String> findAuthorNameById(String authorId) {
        AuthorEntity a = new AuthorEntity();
        a.setId(authorId);
        int index = authors.indexOf(a);
        if (index != -1) {
            return Optional.of(authors.get(index).getName());
        }
        else {
            return Optional.empty();
        }
    }
    
    public List<String> parseAuthorNames(List<String> authorIds) {
        /*
        List<String> authorNames = new ArrayList<>();
        authorIds.forEach(authorId -> {
            AuthorEntity a = new AuthorEntity();
            a.setId(authorId);
            int index = authors.indexOf(a);
            if (index != -1) {
                String authorName = authors.get(index).getName();
//                System.out.println("    author name: " + authorName);
                authorNames.add(authorName);
            }
        });
        */
        List<String> authorNames = authorIds.stream()
                .map(authorId -> findAuthorNameById(authorId))
                .map(optionalAuthor -> {
                    if (!optionalAuthor.isPresent()) {
                        return "Unknown Author";
                    }
                    return optionalAuthor.get();
                })
                .collect(Collectors.toList());
        return authorNames;
    }
    
    public List<String> parseBookCoverIds(JSONArray coversArray) {
        List<String> coverIds = new ArrayList<>();
        for (int i = 0; i < coversArray.length(); i++) {
            String coverId = "" + coversArray.optInt(i);
            coverIds.add(coverId);
        }
        return coverIds;
    }

    @Override
    public BookEntity parse(String jsonLine) throws JsonDumpParseException {
        // read and parse Book line
        String worksJsonStr = jsonLine.substring(jsonLine.indexOf("{"));
        JSONObject workObject = new JSONObject(worksJsonStr);

        // construct Book object
        JSONObject emptyObject = new JSONObject("{}");
        JSONArray emptyArray = new JSONArray("[]");

        BookEntity book = new BookEntity();

        book.setId(workObject.optString("key")
                .replace("/works/", ""));
        book.setTitle(workObject.optString("title"));

        JSONObject descriptionObj = workObject.optJSONObject(
                "description", emptyObject);
        book.setDescription(descriptionObj.optString("value"));

        JSONObject createdObj = workObject.optJSONObject(
                "created", emptyObject);
        String createdString = createdObj.optString("value");
        LocalDate publishDate = LocalDate.parse(
                createdString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        book.setPublishDate(publishDate);

        JSONArray authorsArray = workObject.optJSONArray("authors");
        authorsArray = authorsArray == null ? emptyArray : authorsArray;
        List<String> authorIds = parseAuthorIds(authorsArray);
        book.setAuthorIds(authorIds);
        
        List<String> authorNames = parseAuthorNames(authorIds);
        book.setAuthorNames(authorNames);

        JSONArray coversArray = workObject.optJSONArray("covers");
        coversArray = coversArray == null ? emptyArray : coversArray;
        List<String> coverIds = parseBookCoverIds(coversArray);
        book.setCoverIds(coverIds);

        System.out.println("Parsed Book: " + book + " saved");
        return book;
    }
    
}
