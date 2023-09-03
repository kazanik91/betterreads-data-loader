/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pl.kazanik.betterreadsdataloader.wsclient.openlibrary.bookcover;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.kazanik.betterreadsdataloader.wsclient.openlibrary.bookcover.dto.BookCoverDto;

/**
 *
 * @author Krysia
 */
@FeignClient(value = "book-cover", url = "${openlibrary.book-cover.url}")
public interface BookCoverClient {
    
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/b/id/{bookId}",
            produces = "application/json"
    )
    public abstract BookCoverDto getBookCoverById(@PathVariable("bookId") String bookId);
}
