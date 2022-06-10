package ru.isakaev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "book")
@NamedEntityGraph( name = "full-book-entity-graph",
                    attributeNodes ={@NamedAttributeNode("genre"),
                                    @NamedAttributeNode("comment")})
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String title;

    @ManyToOne(targetEntity = Author.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(targetEntity = Genre.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY , orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private List<Comment> comment;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(Long id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addComment(Comment c) {
        comment.add(c);
    }

    public void deleteGenre(Comment c) {
        comment.remove(c);
    }
}
