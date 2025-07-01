package com.elibrary.backend.author;

import com.elibrary.backend.author.dto.AuthorDto;
import com.elibrary.backend.book.dto.BookDto;
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
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> getAuthors(
        @RequestParam(name = "starts_with", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) @Min(0) int offset,
        @RequestParam(defaultValue = "100", required = false) @Min(0) @Max(100) int limit
    ) {
        List<AuthorDto> authors = authorService.getFilteredAuthors(
            startsWith,
            offset,
            limit
        );

        return ResponseEntity.ok(authors);
    }

    @GetMapping("/authors/{author_id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("author_id") Integer authorId) {
        AuthorDto author = authorService.getAuthor(authorId);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/authors/{author_id}/books")
    public ResponseEntity<List<BookDto>> getAuthorBooks(
        @PathVariable("author_id") Integer authorId,
        @RequestParam(name = "starts_with", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) @Min(0) int offset,
        @RequestParam(defaultValue = "100", required = false) @Min(0) @Max(100) int limit
    ) {
        List<BookDto> books = authorService.getFilteredAuthorBooks(
            authorId,
            startsWith,
            offset,
            limit
        );

        return ResponseEntity.ok(books);
    }
}
