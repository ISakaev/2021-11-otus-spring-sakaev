package ru.isakaev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.isakaev.model.Author;
import ru.isakaev.repo.AuthorRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

//    //Чтобы не усложнять пример, делать так нельзя :)
//    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
//    @Autowired
//    private AuthorRepository repository;
//
//    @PostConstruct
//    public void init() {
//        repository.save(new Author("Pushkin"));
//        repository.save(new Author("Lermontov"));
//    }
}
