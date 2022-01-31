package ru.isakaev.service;

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
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Author> findByNameContainText(String text) {
        return repository.findByNameContains(text);
    }

    @Override
    public Author getAuthor(Long id) {
        return repository.getById(id);
    }

    @Override
    @Transactional
    public Author saveAuthor(Author newAuthor) {
        Author author = repository.findByName(newAuthor.getName());
        if (author != null){
            return author;
        }
        return repository.save(newAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        repository.deleteById(id);
    }
}
