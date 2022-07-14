package ru.isakaev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.isakaev.repo.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ru.isakaev.model.User u = userRepository.findUserByName(username);
        if (u == null) {
            new UsernameNotFoundException(username);
        }
        return new User(u.getName(), u.getPassword(), List.of(new SimpleGrantedAuthority(u.getAuthority())));

    }
}
