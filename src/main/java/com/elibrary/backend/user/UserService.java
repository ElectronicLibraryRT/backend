package com.elibrary.backend.user;

import com.elibrary.backend.book.BookRepository;
import com.elibrary.backend.book.dto.BookDto;
import com.elibrary.backend.favouritebooks.FavouriteBook;
import com.elibrary.backend.favouritebooks.FavouriteBookRepository;
import com.elibrary.backend.favouritebooks.dto.AddedDto;
import com.elibrary.backend.favouritebooks.dto.FavouriteBookDto;
import com.elibrary.backend.favouritebooks.id.FavouriteBookId;
import com.elibrary.backend.readhistory.ReadHistory;
import com.elibrary.backend.readhistory.ReadHistoryRepository;
import com.elibrary.backend.readhistory.dto.LastReadDto;
import com.elibrary.backend.readhistory.dto.ReadHistoryDto;
import com.elibrary.backend.readhistory.id.ReadHistoryId;
import com.elibrary.backend.user.dto.UserDto;
import com.elibrary.backend.book.Book;
import com.elibrary.backend.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReadHistoryRepository readHistoryRepository;
    private final FavouriteBookRepository favouriteBookRepository;

    public List<UserDto> getFilteredUsers(String startsWith, int offset, int limit) {
        List<User> users = userRepository.findUsers(startsWith, offset, limit);

        return users.stream()
            .map(user -> UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build()
            )
            .toList();
    }

    public UserDto getUser(Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("user", "id", userId);
        }

        User user = userOpt.get();
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .build();
    }

    public List<ReadHistoryDto> getFilteredUserReadHistory(Integer userId, String startsWith, int offset, int limit) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("user", "id", userId);
        }

        List<ReadHistory> readHistory = readHistoryRepository.findByUserId(userId, startsWith, offset, limit);

        return readHistory.stream()
            .map(rh -> ReadHistoryDto.builder()
                .book(BookDto.builder()
                    .id(rh.getBook().getId())
                    .title(rh.getBook().getTitle())
                    .yearWritten(rh.getBook().getYearWritten())
                    .build()
                )
                .lastReadTs(rh.getLastReadTs())
                .build()
            )
            .toList();
    }

    public LastReadDto getUserBookLastReadTimestamp(Integer userId, Integer bookId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("user", "id", userId);
        }

        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new ResourceNotFoundException("book", "id", bookId);
        }

        ReadHistoryId readHistoryId = new ReadHistoryId(userId, bookId);
        Optional<ReadHistory> readHistoryOpt = readHistoryRepository.findById(readHistoryId);

        if (readHistoryOpt.isEmpty()) {
            throw new ResourceNotFoundException(
                "readHistory",
                "userId, bookId",
                userId + "/" + bookId
            );
        }

        ReadHistory readHistory = readHistoryOpt.get();
        return new LastReadDto(readHistory.getLastReadTs());
    }

    public List<FavouriteBookDto> getFilteredUserFavouriteBooks(
        Integer userId,
        String startsWith,
        int offset,
        int limit
    ) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("user", "id", userId);
        }

        List<FavouriteBook> favouriteBooks = favouriteBookRepository.findByUserId(userId, startsWith, offset, limit);

        return favouriteBooks.stream()
            .map(fb -> FavouriteBookDto.builder()
                .book(BookDto.builder()
                    .id(fb.getBook().getId())
                    .title(fb.getBook().getTitle())
                    .yearWritten(fb.getBook().getYearWritten())
                    .build()
                )
                .addedTs(fb.getAddedTs())
                .build()
            )
            .toList();
    }

    public AddedDto getUserBookAddedToFavouritesTimestamp(Integer userId, Integer bookId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("user", "id", userId);
        }

        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new ResourceNotFoundException("book", "id", bookId);
        }

        FavouriteBookId favouriteBookId = new FavouriteBookId(userId, bookId);
        Optional<FavouriteBook> favouriteBookOpt = favouriteBookRepository.findById(favouriteBookId);

        if (favouriteBookOpt.isEmpty()) {
            throw new ResourceNotFoundException(
                "favouriteBook",
                "userId, bookId",
                userId + "/" + bookId
            );
        }

        FavouriteBook favouriteBook = favouriteBookOpt.get();
        return new AddedDto(favouriteBook.getAddedTs());
    }
}
