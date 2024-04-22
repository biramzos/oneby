package com.web.oneby.modules.books.Controllers;

import com.web.oneby.commons.DTOs.PageObject;
import com.web.oneby.commons.DTOs.SearchFilter;
import com.web.oneby.commons.DTOs.SimpleObject;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Utils.Response;
import com.web.oneby.modules.books.DTOs.BookResponse;
import com.web.oneby.modules.books.Enums.Genre;
import com.web.oneby.modules.books.Models.Book;
import com.web.oneby.modules.books.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController (BookService bookService) {
        this.bookService = bookService;
    }

    @ResponseBody
    @GetMapping("/genres")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public Response getGenres(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language) {
        return Response.getResponse("genres", Arrays.stream(Genre.values()).map(genre -> new SimpleObject(genre.name(), genre.getName(language))).toList());
    }

    @ResponseBody
    @PostMapping("/")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public Response search(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language, @RequestBody SearchFilter filter) {
        return Response.getResponse("books", bookService.search(filter, language));
    }

    @ResponseBody
    @GetMapping("/{bookId}")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public Response getBook(@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language, @PathVariable("bookId") Book book) {
        return Response.getResponse("book", BookResponse.fromBook(book, language));
    }

//    @ResponseBody
//    @GetMapping("/")
//    @PreAuthorize("isAuthenticated() or isAnonymous()")
//    public Response get

}
