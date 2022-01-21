package ru.isakaev.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Author getAuthor(Long id){
        Author author = authorDao.getById(id).orElse(null);
        return author;
    }

    @Override
    @Transactional
    public Author saveAuthor(String name){
        List<Author> author = authorDao.findByName(name);
        if (!author.isEmpty()){
            return author.get(0);
        }
        return authorDao.save(new Author(name));
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id){
        authorDao.deleteById(id);
    }
}
