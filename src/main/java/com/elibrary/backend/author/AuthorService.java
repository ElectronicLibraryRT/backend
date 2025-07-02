package com.elibrary.backend.author;

import com.elibrary.backend.author.dto.AuthorDto;
import com.elibrary.backend.book.Book;
import com.elibrary.backend.book.dto.BookDto;
import com.elibrary.backend.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<AuthorDto> getFilteredAuthors(String startsWith, int offset, int limit) {
        List<Author> authors = authorRepository.findAuthors(startsWith, offset, limit);

        return authors.stream()
            .map(author -> AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .build()
            )
            .toList();
    }

    public AuthorDto getAuthor(Integer authorId) {
        Optional<Author> authorOpt = authorRepository.findById(authorId);
        if (authorOpt.isEmpty()) {
            throw new ResourceNotFoundException("author", "id", authorId);
        }

        Author author = authorOpt.get();
        return AuthorDto.builder()
            .id(author.getId())
            .name(author.getName())
            .build();
    }

    public List<BookDto> getFilteredAuthorBooks(Integer authorId, String startsWith, int offset, int limit) {
        Optional<Author> authorOpt = authorRepository.findById(authorId);
        if (authorOpt.isEmpty()) {
            throw new ResourceNotFoundException("author", "id", authorId);
        }

        List<Book> books = authorRepository.findBooksByAuthorId(authorId, startsWith, offset, limit);

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
