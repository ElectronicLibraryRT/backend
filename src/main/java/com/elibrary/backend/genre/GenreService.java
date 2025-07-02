package com.elibrary.backend.genre;

import com.elibrary.backend.book.Book;
import com.elibrary.backend.book.dto.BookDto;
import com.elibrary.backend.genre.dto.GenreDto;
import com.elibrary.backend.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public List<GenreDto> getFilteredGenres(String startsWith, int offset, int limit) {
        List<Genre> genres = genreRepository.findGenres(startsWith, offset, limit);

        return genres.stream()
            .map(genre -> GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build()
            )
            .toList();
    }

    public GenreDto getGenre(Integer genreId) {
        Optional<Genre> genreOpt = genreRepository.findById(genreId);
        if (genreOpt.isEmpty()) {
            throw new ResourceNotFoundException("genre", "id", genreId);
        }

        Genre genre = genreOpt.get();
        return GenreDto.builder()
            .id(genre.getId())
            .name(genre.getName())
            .build();
    }

    public List<BookDto> getFilteredGenreBooks(Integer genreId, String startsWith, int offset, int limit) {
        Optional<Genre> genreOpt = genreRepository.findById(genreId);
        if (genreOpt.isEmpty()) {
            throw new ResourceNotFoundException("genre", "id", genreId);
        }

        List<Book> books = genreRepository.findBooksByGenreId(genreId, startsWith, offset, limit);

        return books.stream()
            .map(book -> BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .yearWritten(book.getYearWritten())
                .build()
            )
            .toList();
    }
}
