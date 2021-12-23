package ru.isakaev.service;

import org.springframework.stereotype.Service;
import ru.isakaev.dao.AuthorDao;
import ru.isakaev.model.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public List<Author> getAll(){
        return authorDao.getAll();
    }

    @Override
    public Author getAuthor(int id){
        Author author = authorDao.getById(id).orElse(null);
        return author;
    }

    @Override
    public Author saveAuthor(String name){
        Author author = authorDao.getAll().stream().filter(a -> a.getName().equalsIgnoreCase(name)).findAny().orElse(null);
        if (author != null){
            return author;
        }
        return authorDao.save(new Author(name));
    }

    @Override
    public void deleteAuthor(int id){
        authorDao.deleteById(id);
    }
}
