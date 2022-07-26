package ru.isakaev.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.model.Author;
import ru.isakaev.repo.AuthorRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultAuthorsFindAll")
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultAuthorsfindByNameContainText")
    public List<Author> findByNameContainText(String text) {
        return repository.findByNameContains(text);
    }

    @Override
    @HystrixCommand(fallbackMethod = "defaultAuthorGet")
    public Author getAuthor(Long id) {
        return repository.getById(id);
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "defaultAuthorSave")
    public Author saveAuthor(Author newAuthor) {
        Author author = repository.findByName(newAuthor.getName());
        if (author != null){
            return author;
        }
        return repository.save(newAuthor);
    }

    @Override
    @HystrixCommand(fallbackMethod = "deleteAuthorHystrix")
    public void deleteAuthor(Long id) {
        repository.deleteById(id);
    }

    public List<Author> defaultAuthorsFindAll() {
       return List.of(new Author(-1L,"Hystrix"));
    }

    public List<Author> defaultAuthorsfindByNameContainText(String text) {
        return List.of(new Author(-1L,"Hystrix"));
    }

    public Author defaultAuthorGet(Long id) {
        return new Author(-1L,"Hystrix");
    }

    public Author defaultAuthorSave(Author newAuthor) {
        return new Author(-1L,"Hystrix");
    }

    public void deleteAuthorHystrix(Long id) {

    }

}
