package com.example.nplusone.service;

import com.example.nplusone.dto.AuthorDto;
import com.example.nplusone.dto.BookDto;
import com.example.nplusone.model.Author;
import com.example.nplusone.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    // -------------------------------------------------------------------------
    // PROBLEM endpoint — N+1 in action.
    //
    // Hibernate fires:
    //   SELECT * FROM authors                        → 1 query
    //   SELECT * FROM books WHERE author_id = 1      → query for author 1
    //   SELECT * FROM books WHERE author_id = 2      → query for author 2
    //   ... and so on for every author               → N queries
    //
    // Watch the logs (spring.jpa.show-sql=true) — you'll see N+1 queries.
    // -------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<AuthorDto> findAllNPlusOne() {
        log.info("=== N+1 PROBLEM: loading authors (lazy, no fetch) ===");
        List<Author> authors = authorRepository.findAll();

        // Accessing .getBooks() OUTSIDE a JOIN triggers a new SELECT per author
        return authors.stream()
                .map(a -> {
                    log.info("  → loading books for author id={}", a.getId());
                    return toDto(a); // triggers lazy load here
                })
                .toList();
    }

    // -------------------------------------------------------------------------
    // SOLUTION 1 — JOIN FETCH
    // Single query: SELECT DISTINCT a FROM Author a JOIN FETCH a.books
    // -------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<AuthorDto> findAllJoinFetch() {
        log.info("=== SOLUTION 1: JOIN FETCH ===");
        return authorRepository.findAllWithBooksFetch()
                .stream()
                .map(this::toDto)
                .toList();
    }

    // -------------------------------------------------------------------------
    // SOLUTION 2 — @EntityGraph
    // Same result as JOIN FETCH, different declaration style.
    // -------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<AuthorDto> findAllEntityGraph() {
        log.info("=== SOLUTION 2: @EntityGraph ===");
        return authorRepository.findAllWithEntityGraph()
                .stream()
                .map(this::toDto)
                .toList();
    }

    // -------------------------------------------------------------------------
    // SOLUTION 3 — @BatchSize (configured on the entity itself)
    //
    // Instead of 1 query per author, Hibernate batches the IN clause:
    //   SELECT * FROM books WHERE author_id IN (1, 2, 3, ...)
    //
    // Enable by adding @BatchSize(size = 25) on Author.books collection,
    // or globally via hibernate.default_batch_fetch_size in properties.
    // This endpoint uses plain findAll() — the batching is transparent.
    // -------------------------------------------------------------------------
    @Transactional(readOnly = true)
    public List<AuthorDto> findAllBatchSize() {
        log.info("=== SOLUTION 3: batch fetch size (see application.properties) ===");
        return authorRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    // -----------------------------------------------------------------------

    private AuthorDto toDto(Author a) {
        List<BookDto> books = a.getBooks().stream()
                .map(b -> new BookDto(b.getId(), b.getTitle(), b.getYear()))
                .toList();
        return new AuthorDto(a.getId(), a.getName(), a.getCountry(), books);
    }
}
