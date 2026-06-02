package com.example.nplusone.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * One Author → many Books.
 *
 * Default fetch type for @OneToMany is LAZY, which is exactly what causes
 * the N+1 problem: loading N authors triggers N additional queries to load
 * each author's books when the collection is accessed outside a JOIN.
 */
@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String country;

    // LAZY fetch (default) — root cause of the N+1 problem
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    public Author(String name, String country) {
        this.name    = name;
        this.country = country;
    }
}
