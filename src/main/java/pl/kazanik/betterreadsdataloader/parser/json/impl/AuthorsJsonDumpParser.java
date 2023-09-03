/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.kazanik.betterreadsdataloader.parser.json.impl;

import org.json.JSONObject;
import pl.kazanik.betterreadsdataloader.author.AuthorEntity;
import pl.kazanik.betterreadsdataloader.parser.json.exception.JsonDumpParseException;
import pl.kazanik.betterreadsdataloader.parser.json.IJsonDumpParser;

/**
 *
 * @author Krysia
 */
public class AuthorsJsonDumpParser implements IJsonDumpParser<AuthorEntity>{

    @Override
    public AuthorEntity parse(String jsonLine) throws JsonDumpParseException {
        // read and parse Author line
        String json = jsonLine.substring(jsonLine.indexOf("{"));
        JSONObject jsonObject = new JSONObject(json);

        // construct Author object
        AuthorEntity author = new AuthorEntity();
        author.setName(jsonObject.optString("name"));
        author.setPersonalName(jsonObject.optString("personal_name"));
        String key = jsonObject.optString("key");
        author.setId(key.replace("/authors/", ""));

        System.out.println("Parsed Author: " + author + " saved");
        return author;
    }
    
}
