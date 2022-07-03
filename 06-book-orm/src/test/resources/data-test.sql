INSERT INTO author(id, name) VALUES (1, 'Первый автор');
INSERT INTO author(id, name) VALUES (2, 'Второй автор');
INSERT INTO author(id, name) VALUES (3, 'Третий автор');
INSERT INTO author(id, name) VALUES (4, 'Четвертый автор');
INSERT INTO genre(id, name) VALUES (1, 'Первый жанр');
INSERT INTO genre(id, name) VALUES (2, 'Второй жанр');
INSERT INTO genre(id, name) VALUES (3, 'Третий жанр');
INSERT INTO genre(id, name) VALUES (4, 'Четвертый жанр');
INSERT INTO book(id, name, author_id, genre_id) VALUES (1, 'Первая книга', 1, 1);
INSERT INTO book(id, name, author_id, genre_id) VALUES (2, 'Вторая книга', 2, 2);
INSERT INTO book(id, name, author_id, genre_id) VALUES (3, 'Третья книга', 3, 3);
INSERT INTO comment(id, book_id, name) VALUES (1, 1, 'Первый коментарий');
INSERT INTO comment(id, book_id, name) VALUES (2, 1, 'Второй коментарий');
INSERT INTO comment(id, book_id, name) VALUES (3, 2, 'Третий коментарий');
INSERT INTO comment(id, book_id, name) VALUES (4, 2, 'Четвертый коментарий');
INSERT INTO comment(id, book_id, name) VALUES (5, 3, 'Пятый коментарий');