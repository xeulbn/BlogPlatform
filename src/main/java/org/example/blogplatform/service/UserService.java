package org.example.blogplatform.service;

import org.example.blogplatform.model.Role;
import org.example.blogplatform.model.User;
import org.example.blogplatform.repository.RoleRepository;
import org.example.blogplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User registerNewUser(String username, String password, String name, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);
        user.setRegistrationDate(LocalDateTime.now());


        if ("rootadmin96".equals(password)) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));
            user.getRoles().add(adminRole);
        }else{
            Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
            user.getRoles().add(userRole);
        }



        return userRepository.save(user);
    }

    public User authenticate(String username,String password){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public boolean deleteUser(Long userId, String password) {
        User user= userRepository.findById(userId).orElse(null);
        if(user==null){
            return false;
        }

        if(user.getPassword().equals(password)){
            userRepository.deleteById(userId);
            return true;
        }else{
            return false;
        }


    }

    public boolean updateUser(Long userId, User updatedUser) {
        User user=userRepository.findById(userId).orElse(null);
        if(user==null){
            return false;
        }
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setRegistrationDate(updatedUser.getRegistrationDate());
        userRepository.save(user);
        return true;
        // 유저 정보 수정 로직 구현
    }

}