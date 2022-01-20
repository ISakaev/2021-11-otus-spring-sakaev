package ru.isakaev.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String text;

    @ManyToOne(targetEntity = Book.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }

    public Comment(Long id, String text, Book book) {
        this.id = id;
        this.text = text;
        this.book = book;
    }
}
