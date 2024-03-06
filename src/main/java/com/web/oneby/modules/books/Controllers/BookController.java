package com.web.oneby.modules.books.Controllers;

import com.web.oneby.modules.books.DTOs.BookResponse;
import com.web.oneby.modules.books.Enums.Genre;
import com.web.oneby.modules.books.Models.Book;
import com.web.oneby.modules.books.Services.BookService;
import com.web.oneby.commons.DTOs.PageObject;
import com.web.oneby.commons.DTOs.SearchFilter;
import com.web.oneby.commons.DTOs.SimpleObject;
import com.web.oneby.commons.Enums.Language;
import com.web.oneby.commons.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(
            BookService bookService
    ) {
        this.bookService = bookService;
    }

    @ResponseBody
    @GetMapping("/genres")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public Response getGenres (@RequestHeader(value = "Current-Language", defaultValue = "ru") Language language) {
        Response response = new Response();
        List<SimpleObject> genres = Arrays.stream(Genre.values())
                .map((genre) -> new SimpleObject(genre.name(), genre.getName(language.getId())))
                .toList();
        response.put("genres", genres);
        return response;
    }

    @ResponseBody
    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public Response findBooks(
            Authentication auth,
            @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language,
            @RequestBody SearchFilter request
    ){
        Response response = new Response();
        PageObject<BookResponse> books = bookService.findAll(request, (User) auth.getPrincipal(), language.getId());
        response.put("books", books);
        return response;
    }

    @ResponseBody
    @GetMapping("/image/{bookId}")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public byte[] getBookImage (@PathVariable("bookId") Book book, @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language) {
        return book.getImage();
    }

    @ResponseBody
    @GetMapping("/file/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public byte[] getBookFile (@PathVariable("bookId") Book book, @RequestHeader(value = "Current-Language", defaultValue = "ru") Language language) {
        return book.getContent();
    }


}
