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
                     author_id INTEGER,
                     genre_id INTEGER,
                     FOREIGN KEY (author_id) REFERENCES author(id),
                     FOREIGN KEY (genre_id) REFERENCES genre(id)
);

DROP TABLE IF EXISTS comment;
CREATE TABLE comment(
                        id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        book_id INTEGER,
                        name VARCHAR(255) NOT NULL,
                        FOREIGN KEY (book_id) REFERENCES book(id)
);