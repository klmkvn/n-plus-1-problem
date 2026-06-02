package com.example.nplusone.dto;

import java.util.List;

public record AuthorDto(Long id, String name, String country, List<BookDto> books) {}
