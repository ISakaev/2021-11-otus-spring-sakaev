INSERT INTO author(id, name) VALUES (1, 'Первый автор');
INSERT INTO author(id, name) VALUES (2, 'Второй автор');
INSERT INTO author(id, name) VALUES (3, 'Автор три');
INSERT INTO genre(id, name) VALUES (1, 'Первый жанр');
INSERT INTO genre(id, name) VALUES (2, 'Второй жанр');
INSERT INTO book(id, name, author_id, genre_id) VALUES (1, 'Первая книга', 1, 1);
INSERT INTO book(id, name, author_id, genre_id) VALUES (2, 'Вторая книга', 2, 2);
INSERT INTO person(id, name, password, authority) VALUES (1, 'user', 'user', 'ROLE_USER');
INSERT INTO person(id, name, password, authority) VALUES (2, 'admin', 'admin', 'CAN_DELETE');