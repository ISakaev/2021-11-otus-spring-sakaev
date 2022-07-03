package ru.isakaev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isakaev.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByName(String name);
}
