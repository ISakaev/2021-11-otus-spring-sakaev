package ru.isakaev.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isakaev.model.Author;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;

    private String title;

    private Author author;

}
