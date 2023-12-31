/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.kazanik.betterreadsdataloader.parser.json.factory;

import pl.kazanik.betterreadsdataloader.parser.json.JsonParserManager;

/**
 *
 * @author Krysia
 */
@FunctionalInterface
public interface IJsonParserFactory<T> {
    
    public abstract JsonParserManager<T> createJsonParser();
}
