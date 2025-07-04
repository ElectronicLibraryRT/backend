package com.elibrary.backend.book;

import com.elibrary.backend.author.dto.AuthorDto;
import com.elibrary.backend.book.dto.BookDto;
import com.elibrary.backend.booklocation.dto.BookLocationDto;
import com.elibrary.backend.booklocation.dto.LocationDto;
import com.elibrary.backend.genre.dto.GenreDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getBooks(
        @RequestParam(name = "starts_with", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) @Min(0) int offset,
        @RequestParam(defaultValue = "100", required = false) @Min(0) @Max(100) int limit
    ) {
        List<BookDto> books = bookService.getFilteredBooks(
            startsWith,
            offset,
            limit
        );

        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/{book_id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("book_id") Integer bookId) {
        BookDto book = bookService.getBook(bookId);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/books/{book_id}/genres")
    public ResponseEntity<List<GenreDto>> getBookGenres(
        @PathVariable("book_id") Integer bookId,
        @RequestParam(name = "starts_with", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) @Min(0) int offset,
        @RequestParam(defaultValue = "100", required = false) @Min(0) @Max(100) int limit
    ) {
        List<GenreDto> books = bookService.getFilteredBookGenres(
            bookId,
            startsWith,
            offset,
            limit
        );

        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/{book_id}/authors")
    public ResponseEntity<List<AuthorDto>> getBookAuthors(
        @PathVariable("book_id") Integer bookId,
        @RequestParam(name = "starts_with", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) @Min(0) int offset,
        @RequestParam(defaultValue = "100", required = false) @Min(0) @Max(100) int limit
    ) {
        List<AuthorDto> books = bookService.getFilteredBookAuthors(
            bookId,
            startsWith,
            offset,
            limit
        );

        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/{book_id}/extensions")
    public ResponseEntity<List<BookLocationDto>> getBookExtensions(
        @PathVariable("book_id") Integer bookId,
        @RequestParam(name = "starts_with", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) @Min(0) int offset,
        @RequestParam(defaultValue = "100", required = false) @Min(0) @Max(100) int limit
    ) {
        List<BookLocationDto> books = bookService.getFilteredBookExtensions(
            bookId,
            startsWith,
            offset,
            limit
        );

        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/{book_id}/extensions/{extension_id}")
    public ResponseEntity<LocationDto> getBookLink(
        @PathVariable("book_id") Integer bookId,
        @PathVariable("extension_id") Integer extensionId
    ) {
        LocationDto location = bookService.getBookLink(
            bookId,
            extensionId
        );

        return ResponseEntity.ok(location);
    }
}


