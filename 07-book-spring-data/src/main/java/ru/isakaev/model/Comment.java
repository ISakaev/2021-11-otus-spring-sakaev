package ru.isakaev.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String text;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }

    public Comment(Integer id, String text) {
        this.id = id;
        this.text = text;
    }
}
