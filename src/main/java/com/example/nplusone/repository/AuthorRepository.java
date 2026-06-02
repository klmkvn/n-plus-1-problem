package com.example.nplusone.repository;

import com.example.nplusone.model.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // -------------------------------------------------------------------------
    // PROBLEM: plain findAll() — Hibernate issues 1 query for authors, then
    // N separate queries to load books for each author (when accessed).
    // -------------------------------------------------------------------------
    // (inherited from JpaRepository — no annotation needed here, just call it)

    // -------------------------------------------------------------------------
    // SOLUTION 1: JOIN FETCH — single query with a JOIN, loads books eagerly.
    // NOTE: cannot combine JOIN FETCH with Pageable (use countQuery instead).
    // -------------------------------------------------------------------------
    @Query("SELECT DISTINCT a FROM Author a JOIN FETCH a.books")
    List<Author> findAllWithBooksFetch();

    // -------------------------------------------------------------------------
    // SOLUTION 2: @EntityGraph — declarative, reuses Spring Data method naming.
    // Equivalent to JOIN FETCH but expressed as an annotation.
    // -------------------------------------------------------------------------
    @EntityGraph(attributePaths = "books")
    @Query("SELECT a FROM Author a")
    List<Author> findAllWithEntityGraph();
}
