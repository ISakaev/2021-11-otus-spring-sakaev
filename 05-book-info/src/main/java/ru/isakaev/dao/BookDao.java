package ru.isakaev.dao;

import jdk.dynalink.linker.LinkerServices;
import ru.isakaev.model.Book;

import java.util.List;

public interface BookDao {

    List<Book> getAll();

    Book getById(int id);

    void save(Book book);

    void deleteById(int id);

}
