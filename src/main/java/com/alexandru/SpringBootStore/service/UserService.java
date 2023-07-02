package com.alexandru.SpringBootStore.service;


import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user)
        );
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Add user roles as authorities if necessary
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }


    public void createUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public int updateUserEmail(Long id, String email) {
        return userRepository.updateUserEmail(id, email);
    }

    public int updateFirstName(Long id, String firstName) {
        return userRepository.updateUserFirstName(id, firstName);
    }

    public int updateLastName(Long id, String lastName) {
        return userRepository.updateUserLastName(id, lastName);
    }


}
