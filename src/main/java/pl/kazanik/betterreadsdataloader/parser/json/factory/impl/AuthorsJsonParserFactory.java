/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.kazanik.betterreadsdataloader.parser.json.factory.impl;

import pl.kazanik.betterreadsdataloader.author.AuthorEntity;
import pl.kazanik.betterreadsdataloader.parser.json.IJsonDumpParser;
import pl.kazanik.betterreadsdataloader.parser.json.JsonParser;
import pl.kazanik.betterreadsdataloader.parser.json.factory.IJsonParserFactory;
import pl.kazanik.betterreadsdataloader.parser.json.impl.AuthorsJsonDumpParser;

/**
 *
 * @author Krysia
 */
public class AuthorsJsonParserFactory implements IJsonParserFactory<AuthorEntity>{

    @Override
    public JsonParser<AuthorEntity> createJsonParser() {
        IJsonDumpParser<AuthorEntity> authorsDumpParser = new AuthorsJsonDumpParser();
        JsonParser<AuthorEntity> authorsParser = new JsonParser<>(authorsDumpParser);
        return authorsParser;
    }
    
}
