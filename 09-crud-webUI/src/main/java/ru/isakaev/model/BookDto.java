package ru.isakaev.model;

import lombok.Data;

import javax.persistence.*;

@Data
public class BookDto {


    private String title;

    private Long author;

    private Long genre;
}
