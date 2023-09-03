/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.kazanik.betterreadsdataloader.parser.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import pl.kazanik.betterreadsdataloader.parser.json.exception.JsonDumpParseException;

/**
 *
 * @author Krysia
 */
public class JsonParser<T> {
    
    private IJsonDumpParser<T> parser;

    public JsonParser(IJsonDumpParser<T> parser) {
        this.parser = parser;
    }
    
    public List<T> parse(String jsonDumpLocation) throws JsonDumpParseException {
        Path jsonDumpPath = Paths.get(jsonDumpLocation);
        List<T> parsed = new ArrayList<>();
        
        try (Stream<String> lines = Files.lines(jsonDumpPath)) {
            lines.forEach(lineJson -> {
                
                try {
                    T entity = parser.parse(lineJson);
                    parsed.add(entity);
                }
                catch (JsonDumpParseException jsonParseEx) {
                    jsonParseEx.printStackTrace();
                }
            });
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return parsed;
    }
}
