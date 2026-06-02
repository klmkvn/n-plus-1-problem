-- ============================================================
-- Seed data — enough rows to make N+1 visible in logs
-- ============================================================

INSERT INTO authors (name, country) VALUES
    ('George Orwell',        'United Kingdom'),
    ('Fyodor Dostoevsky',    'Russia'),
    ('Gabriel García Márquez','Colombia'),
    ('Haruki Murakami',       'Japan'),
    ('Toni Morrison',         'United States'),
    ('Leo Tolstoy',           'Russia'),
    ('Franz Kafka',           'Czechoslovakia'),
    ('Virginia Woolf',        'United Kingdom'),
    ('Ernest Hemingway',      'United States'),
    ('Italo Calvino',         'Italy');

INSERT INTO books (title, year, author_id) VALUES
    ('1984',                             1949, 1),
    ('Animal Farm',                      1945, 1),
    ('Homage to Catalonia',              1938, 1),
    ('Crime and Punishment',             1866, 2),
    ('The Idiot',                        1869, 2),
    ('The Brothers Karamazov',           1880, 2),
    ('Notes from Underground',           1864, 2),
    ('One Hundred Years of Solitude',    1967, 3),
    ('Love in the Time of Cholera',      1985, 3),
    ('Norwegian Wood',                   1987, 4),
    ('Kafka on the Shore',               2002, 4),
    ('The Wind-Up Bird Chronicle',       1994, 4),
    ('Beloved',                          1987, 5),
    ('Song of Solomon',                  1977, 5),
    ('War and Peace',                    1869, 6),
    ('Anna Karenina',                    1878, 6),
    ('The Metamorphosis',                1915, 7),
    ('The Trial',                        1925, 7),
    ('The Castle',                       1926, 7),
    ('Mrs Dalloway',                     1925, 8),
    ('To the Lighthouse',                1927, 8),
    ('The Old Man and the Sea',          1952, 9),
    ('A Farewell to Arms',               1929, 9),
    ('If on a winter''s night a traveler',1979,10),
    ('Invisible Cities',                  1972,10);
