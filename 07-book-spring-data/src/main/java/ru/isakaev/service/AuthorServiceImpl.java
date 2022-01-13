package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isakaev.dao.AuthorRepository;
import ru.isakaev.model.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll(){
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthor(int id){
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Author> findByNameContainText(String text) {
        return authorRepository.findByNameContains(text);
    }

    @Override
    @Transactional
    public Author saveAuthor(String name){
        Author author = authorRepository.findByName(name);
        if (author != null){
            return author;
        }
        return authorRepository.save(new Author(name));
    }

    @Override
    public void deleteAuthor(int id){
        authorRepository.deleteById(id);
    }
}
