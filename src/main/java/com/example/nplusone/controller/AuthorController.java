package com.example.nplusone.controller;

import com.example.nplusone.dto.AuthorDto;
import com.example.nplusone.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Each endpoint returns the same data but uses a different loading strategy.
 * Compare the number of SQL queries in the logs for each call.
 *
 *  GET /api/authors/nplusone      → PROBLEM  : 1 + N queries
 *  GET /api/authors/join-fetch    → SOLUTION1: 1 query  (JOIN FETCH)
 *  GET /api/authors/entity-graph  → SOLUTION2: 1 query  (@EntityGraph)
 *  GET /api/authors/batch-size    → SOLUTION3: 1+ceil(N/batchSize) queries
 */
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    /** Demonstrates the N+1 problem — watch the SQL log. */
    @GetMapping("/nplusone")
    public ResponseEntity<List<AuthorDto>> nplusone() {
        return ResponseEntity.ok(authorService.findAllNPlusOne());
    }

    /** Fix: JOIN FETCH in JPQL. */
    @GetMapping("/join-fetch")
    public ResponseEntity<List<AuthorDto>> joinFetch() {
        return ResponseEntity.ok(authorService.findAllJoinFetch());
    }

    /** Fix: @EntityGraph attribute paths. */
    @GetMapping("/entity-graph")
    public ResponseEntity<List<AuthorDto>> entityGraph() {
        return ResponseEntity.ok(authorService.findAllEntityGraph());
    }

    /**
     * Fix: hibernate.default_batch_fetch_size — no code change required,
     * configured in application.properties.
     */
    @GetMapping("/batch-size")
    public ResponseEntity<List<AuthorDto>> batchSize() {
        return ResponseEntity.ok(authorService.findAllBatchSize());
    }
}
