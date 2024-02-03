package com.web.oneby.books.Services;

import com.web.oneby.books.DTOs.BookRequest;
import com.web.oneby.books.DTOs.CreateOrganizationRequest;
import com.web.oneby.commons.Enums.HTTPMessage;
import com.web.oneby.commons.Enums.UserRole;
import com.web.oneby.commons.Handlers.HTTPMessageHandler;
import com.web.oneby.books.Models.Book;
import com.web.oneby.books.Models.Organization;
import com.web.oneby.commons.Models.User;
import com.web.oneby.books.Repositories.OrganizationRepository;
import com.web.oneby.commons.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class OrganizationService {
    private OrganizationRepository organizationRepository;
    private UserService userService;
    private BookService bookService;

    @Autowired
    public OrganizationService (
            OrganizationRepository organizationRepository,
            UserService userService,
            BookService bookService
    ) {
        this.organizationRepository = organizationRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public Organization create(CreateOrganizationRequest request, HTTPMessageHandler messageHandler, int language) throws IOException {
        Organization organization = organizationRepository.findByName(request.getName()).orElse(null);
        if (organization != null) {
            messageHandler.set(HTTPMessage.ORGANIZATION_IS_EXIST, language);
            return null;
        }
        if (request.getUser().getRoles().contains(UserRole.SELLER)) {
            organization = organizationRepository.save(
                new Organization(
                    request.getName(),
                    request.getImage().getBytes(),
                    request.getUser()
                )
            );
            return organization;
        } else {
            messageHandler.set(HTTPMessage.USER_IS_NOT_SELLER, language);
            return null;
        }
    }

    public Organization getOrganizationByBook(Book book){
        return organizationRepository.findByBooksContains(book).orElseThrow(() -> new RuntimeException("Seller is not found!"));
    }

    public Organization getOrganizationByUser(User user){
        return organizationRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Organization is not exist!"));
    }

    public void addAlBook(Organization organization, Book book) {
        organization.getBooks().add(book);
        organizationRepository.save(organization);
    }

    public void addBook(Organization organization, BookRequest request, User user) throws IOException {
        Book book = bookService.save(request, user);
        organization.getBooks().add(book);
        organizationRepository.save(organization);
    }

    public void removeBook(Organization organization, Book book, HTTPMessageHandler messageHandler, int language) {
        organization.getBooks().remove(book);
        organizationRepository.save(organization);
        bookService.delete(book, messageHandler, language);
    }

    public void delete(Organization organization){
        organizationRepository.delete(organization);
    }
}
