package ru.isakaev.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
//@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "book-author-entity-graph",
        attributeNodes = {@NamedAttributeNode("author")})
//        ,
//        @NamedEntityGraph(name = "book-genre-entity-graph",
//                attributeNodes = {@NamedAttributeNode("genre")})})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String title;


    @OneToOne(targetEntity = Author.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne(targetEntity = Genre.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private Genre genre;
// @Fetch(FetchMode.SUBSELECT)
    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private List<Comment> comment;

    public Book(Integer id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title, Author author, Genre genre, List<Comment> comment) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comment = comment;
    }

    public Book() {
    }
}
