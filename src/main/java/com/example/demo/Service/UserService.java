package com.example.demo.Service;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.domain.dto.RegisterDTO;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void deleteById(long id) {
        this.userRepository.deleteById(id);
    }
    public User handleSaveUser(User user) {
        User result = userRepository.save(user);
        return result;
   }

public User getUserById(long id) {
        return this.userRepository.findById(id);
}
    public String handleHello() {
        return "hello";
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }


    public User registerDTOtoUser(RegisterDTO registerDTO ){
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;

    }

    public boolean checkEmailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
