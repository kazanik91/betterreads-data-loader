/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pl.kazanik.betterreadsdataloader.parser.json;

import pl.kazanik.betterreadsdataloader.parser.json.exception.JsonDumpParseException;

/**
 *
 * @author Krysia
 */
@FunctionalInterface
public interface IJsonDumpParser<T> {
    
    public abstract T parse(String json) throws JsonDumpParseException;
}
