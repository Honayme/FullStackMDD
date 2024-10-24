package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserResponseDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing users.
 */
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger log = LogManager.getLogger("UserServiceImpl");

    @Autowired
    private UserRepository userRepository;

    /**
     * Get a user by ID.
     *
     * @param id the user's ID
     * @return the user
     */
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * Get a user by email.
     *
     * @param email the user's email
     * @return the user
     */
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    /**
     * Get all users.
     *
     * @return list of users
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Update a user's information.
     *
     * @param modifiedUser the new user data
     * @return the updated user
     */
    @Override
    public User modifyUser(UserResponseDTO modifiedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = getUserByEmail(userDetails.getUsername());
        user.setName(modifiedUser.getName());
        user.setEmail(modifiedUser.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
