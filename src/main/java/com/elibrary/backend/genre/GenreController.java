package com.elibrary.backend.genre;

import com.elibrary.backend.book.dto.BookDto;
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
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genres")
    public ResponseEntity<List<GenreDto>> getGenres(
        @RequestParam(name = "starts_with", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) @Min(0) int offset,
        @RequestParam(defaultValue = "100", required = false) @Min(0) @Max(100) int limit
    ) {
        List<GenreDto> genres = genreService.getFilteredGenres(
            startsWith,
            offset,
            limit
        );

        return ResponseEntity.ok(genres);
    }

    @GetMapping("/genres/{genre_id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable("genre_id") Integer genreId) {
        GenreDto genre = genreService.getGenre(genreId);
        return ResponseEntity.ok(genre);
    }

    @GetMapping("/genres/{genre_id}/books")
    public ResponseEntity<List<BookDto>> getGenreBooks(
        @PathVariable("genre_id") Integer genreId,
        @RequestParam(name = "starts_with", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) @Min(0) int offset,
        @RequestParam(defaultValue = "100", required = false) @Min(0) @Max(100) int limit
    ) {
        List<BookDto> books = genreService.getFilteredGenreBooks(
            genreId,
            startsWith,
            offset,
            limit
        );

        return ResponseEntity.ok(books);
    }
}

