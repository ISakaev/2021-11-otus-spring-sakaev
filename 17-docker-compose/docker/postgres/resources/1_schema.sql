-- DROP ALL OBJECTS;

DROP TABLE IF EXISTS author;
CREATE TABLE author(
                       id SERIAL UNIQUE ,
                       name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS genre;
CREATE TABLE genre(
                      id SERIAL UNIQUE ,
                      name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS book;
CREATE TABLE book(
                     id SERIAL UNIQUE ,
                     name VARCHAR(255) NOT NULL,
                     author_id INTEGER,
                     genre_id INTEGER,
                     FOREIGN KEY (author_id) REFERENCES author(id),
                     FOREIGN KEY (genre_id) REFERENCES genre(id)
);

DROP TABLE IF EXISTS comment;
CREATE TABLE comment(
                    id SERIAL UNIQUE ,
                    book_id INTEGER,
                    name VARCHAR(255) NOT NULL,
                    FOREIGN KEY (book_id) REFERENCES book(id)
);

DROP TABLE IF EXISTS person;
CREATE TABLE person(
                    id SERIAL UNIQUE ,
                    name VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    authority VARCHAR(255) NOT NULL
);