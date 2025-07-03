package com.elibrary.backend.extensions;


import com.elibrary.backend.booklocation.BookLocation;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "extensions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Extension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 8)
    private String name;

    @OneToMany(mappedBy = "extension", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookLocation> booksLocations = new HashSet<>();
}

