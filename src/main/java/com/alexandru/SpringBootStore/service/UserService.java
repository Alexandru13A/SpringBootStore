package com.alexandru.SpringBootStore.service;


import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
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
