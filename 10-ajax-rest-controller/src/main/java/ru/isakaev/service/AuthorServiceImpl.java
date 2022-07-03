package ru.isakaev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.config.exception.MyException;
import ru.isakaev.model.Author;
import ru.isakaev.repo.AuthorRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
    public Author getAuthor(Long id) throws EntityNotFoundException, MyException {
        Optional<Author> optinalAuthor = repository.findById(id);
        if (optinalAuthor.isEmpty()) {
            throw new MyException(String.format("Author with id=%s not found", id));
        }
        return optinalAuthor.get();
    }

    @Override
    @Transactional
    public Author saveAuthor(Author newAuthor) throws MyException {
        Author author = repository.findByName(newAuthor.getName());
        if (author != null){
            throw new MyException(String.format("Author with name=%s is exist", newAuthor.getName()));
        }
        return repository.save(newAuthor);
    }

    @Override
    @Transactional
    public Author updateAuthor(Long id, Author newAuthor) throws MyException {
        Optional<Author> optinalAuthor = repository.findById(id);
        if (optinalAuthor.isEmpty()){
            throw new MyException(String.format("Author with id=%s not found", id));
        }
        Author author = optinalAuthor.get();
        author.setName(newAuthor.getName());
        return repository.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) throws MyException {
        Optional<Author> optinalAuthor = repository.findById(id);
        if (optinalAuthor.isEmpty()){
            throw new MyException(String.format("Author with id=%s not found", id));
        }
        repository.deleteById(id);
    }
}
