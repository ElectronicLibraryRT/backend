package com.elibrary.backend.book;

import com.elibrary.backend.author.Author;
import com.elibrary.backend.author.dto.AuthorDto;
import com.elibrary.backend.book.dto.BookDto;
import com.elibrary.backend.booklocation.BookLocation;
import com.elibrary.backend.booklocation.BookLocationRepository;
import com.elibrary.backend.booklocation.dto.BookLocationDto;
import com.elibrary.backend.booklocation.dto.LocationDto;
import com.elibrary.backend.extensions.Extension;
import com.elibrary.backend.extensions.ExtensionRepository;
import com.elibrary.backend.extensions.dto.ExtensionDto;
import com.elibrary.backend.genre.Genre;
import com.elibrary.backend.genre.dto.GenreDto;
import com.elibrary.backend.shared.exceptions.ResourceNotFoundException;
import com.elibrary.backend.booklocation.id.BookLocationId;
import com.elibrary.backend.util.minio.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service 
@RequiredArgsConstructor
public class BookService {
    @Value("${uriprefix.storage}")
    private String storagePrefix;

    private final BookRepository bookRepository;
    private final ExtensionRepository extensionRepository;
    private final BookLocationRepository bookLocationRepository;

    private final MinioService minioService;

    public List<BookDto> getFilteredBooks(String startsWith, int offset, int limit) {
        List<Book> books = bookRepository.findBooks(startsWith, offset, limit);

        return books.stream()
            .map(book -> BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .yearWritten(book.getYearWritten())
                .build()
            )
            .toList();
    }

    public BookDto getBook(Integer bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new ResourceNotFoundException("book", "id", bookId);
        }

        Book book = bookOpt.get();
        return BookDto.builder()
            .id(book.getId())
            .title(book.getTitle())
            .yearWritten(book.getYearWritten())
            .build();
    }

    public List<GenreDto> getFilteredBookGenres(Integer bookId, String startsWith, int offset, int limit) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new ResourceNotFoundException("book", "id", bookId);
        }

        List<Genre> genres = bookRepository.findGenresByBookId(bookId, startsWith, offset, limit);

        return genres.stream()
            .map(genre -> GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build()
            )
            .toList();
    }

    public List<AuthorDto> getFilteredBookAuthors(Integer bookId, String startsWith, int offset, int limit) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new ResourceNotFoundException("book", "id", bookId);
        }

        List<Author> authors = bookRepository.findAuthorsByBookId(bookId, startsWith, offset, limit);

        return authors.stream()
            .map(author -> AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .build()
            )
            .toList();
    }

    public List<BookLocationDto> getFilteredBookExtensions(Integer bookId, String startsWith, int offset, int limit) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new ResourceNotFoundException("book", "id", bookId);
        }

        List<BookLocation> bookLocations = bookLocationRepository.findByBookId(bookId, startsWith, offset, limit);

        return bookLocations.stream()
            .map(bl -> BookLocationDto.builder()
                .extension(ExtensionDto.builder()
                    .id(bl.getExtension().getId())
                    .name(bl.getExtension().getName())
                    .build()
                )
                .location(modifyUrl(minioService.getSharedLink(bl.getLocation())))
                .build()
            )
            .toList();
    }

    public LocationDto getBookLink(Integer bookId, Integer extensionId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new ResourceNotFoundException("book", "id", bookId);
        }

        Optional<Extension> extensionOpt = extensionRepository.findById(extensionId);
        if (extensionOpt.isEmpty()) {
            throw new ResourceNotFoundException("extension", "id", extensionId);
        }

        BookLocationId locationId = new BookLocationId(bookId, extensionId);
        Optional<BookLocation> bookLocationOpt = bookLocationRepository.findById(locationId);

        if (bookLocationOpt.isEmpty()) {
            throw new ResourceNotFoundException(
                "bookLocation",
                "bookId, extensionId",
                bookId + "/" + extensionId
            );
        }

        String location = bookLocationOpt.get().getLocation();

        String url = minioService.getSharedLink(location);

        return new LocationDto(modifyUrl(url));
    }

    private String modifyUrl(String originalUrl) {
        if (originalUrl == null || originalUrl.isEmpty()) {
            return originalUrl;
        }

        try {
            URI uri = new URI(originalUrl);

            URI newUri = new URI(
                null,
                null,
                null,
                -1,
                storagePrefix + uri.getPath(),
                uri.getQuery(),
                uri.getFragment()
            );
            return newUri.toString();

        } catch (URISyntaxException e) {
            return originalUrl;
        }
    }
}

