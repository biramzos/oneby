package com.web.oneby.Controllers;

import com.web.oneby.DTO.BookResponse;
import com.web.oneby.DTO.BookSearchFilterRequest;
import com.web.oneby.DTO.PageObject;
import com.web.oneby.DTO.SimpleObject;
import com.web.oneby.Enums.Genre;
import com.web.oneby.Enums.Language;
import com.web.oneby.Models.Book;
import com.web.oneby.Services.BookService;
import com.web.oneby.Utils.Response;
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
    public BookController (
            BookService bookService
    ) {
        this.bookService = bookService;
    }

    @ResponseBody
    @GetMapping("/genres/{language}")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public Response getGenres (@PathVariable("language") Language language) {
        Response response = new Response();
        List<SimpleObject> genres = Arrays.stream(Genre.values())
                .map((genre) -> new SimpleObject(genre.getId(), genre.getName(language.getId())))
                .toList();
        response.put("genres", genres);
        return response;
    }

    @ResponseBody
    @PostMapping("/{pageNumber}/{countInPart}/{language}")
    @PreAuthorize("isAuthenticated()")
    public Response findBooks(
            @PathVariable("pageNumber") Integer pageNumber,
            @PathVariable("countInPart") Integer countInPart,
            @PathVariable("language") Language language,
            @RequestBody BookSearchFilterRequest request
    ){
        Response response = new Response();
        PageObject<BookResponse> books = bookService.findAll(request, countInPart, pageNumber, language.getId());
        response.put("books", books);
        return response;
    }

    @ResponseBody
    @GetMapping("/image/{bookId}")
    @PreAuthorize("isAuthenticated() or isAnonymous()")
    public byte[] getBookImage (@PathVariable("bookId") Book book) {
        return book.getImage();
    }

    @ResponseBody
    @GetMapping("/file/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public byte[] getBookFile (@PathVariable("bookId") Book book) {
        return book.getContent();
    }


}
