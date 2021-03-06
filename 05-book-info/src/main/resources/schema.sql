DROP ALL OBJECTS;

DROP TABLE IF EXISTS author;
CREATE TABLE author(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS genre;
CREATE TABLE genre(
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS book;
CREATE TABLE book(
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    author INTEGER,
    genre INTEGER,
    FOREIGN KEY (author) REFERENCES author(id),
    FOREIGN KEY (genre) REFERENCES genre(id)
);