package com.elibrary.backend.user;

import com.elibrary.backend.favouritebooks.dto.AddedDto;
import com.elibrary.backend.favouritebooks.dto.FavouriteBookDto;
import com.elibrary.backend.readhistory.dto.LastReadDto;
import com.elibrary.backend.readhistory.dto.ReadHistoryDto;
import com.elibrary.backend.user.dto.UserDto;
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
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(
        @RequestParam(name = "starts_with", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) @Min(0) int offset,
        @RequestParam(defaultValue = "100", required = false) @Min(0) @Max(100) int limit
    ) {
        List<UserDto> users = userService.getFilteredUsers(
            startsWith,
            offset,
            limit
        );

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("user_id") Integer userId) {
        UserDto user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/{user_id}/read_history")
    public ResponseEntity<List<ReadHistoryDto>> getUserReadHistory(
        @PathVariable("user_id") Integer userId,
        @RequestParam(name = "starts_with", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) @Min(0) int offset,
        @RequestParam(defaultValue = "100", required = false) @Min(0) @Max(100) int limit
    ) {
        List<ReadHistoryDto> readHistory = userService.getFilteredUserReadHistory(
            userId,
            startsWith,
            offset,
            limit
        );

        return ResponseEntity.ok(readHistory);
    }

    @GetMapping("/users/{user_id}/read_history/{book_id}")
    public ResponseEntity<LastReadDto> getUserBookLastReadTimestamp(
        @PathVariable("user_id") Integer userId,
        @PathVariable("book_id") Integer bookId
    ) {
        LastReadDto lastRead = userService.getUserBookLastReadTimestamp(
            userId,
            bookId
        );

        return ResponseEntity.ok(lastRead);
    }

    @GetMapping("/users/{user_id}/favourite_books")
    public ResponseEntity<List<FavouriteBookDto>> getUserFavouriteBooks(
        @PathVariable("user_id") Integer userId,
        @RequestParam(name = "starts_with", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) @Min(0) int offset,
        @RequestParam(defaultValue = "100", required = false) @Min(0) @Max(100) int limit
    ) {
        List<FavouriteBookDto> favouriteBooks = userService.getFilteredUserFavouriteBooks(
            userId,
            startsWith,
            offset,
            limit
        );

        return ResponseEntity.ok(favouriteBooks);
    }

    @GetMapping("/users/{user_id}/favourite_books/{book_id}")
    public ResponseEntity<AddedDto> getUserBookAddedToFavouritesTimestamp(
        @PathVariable("user_id") Integer userId,
        @PathVariable("book_id") Integer bookId
    ) {
        AddedDto added = userService.getUserBookAddedToFavouritesTimestamp(
            userId,
            bookId
        );

        return ResponseEntity.ok(added);
    }
}
