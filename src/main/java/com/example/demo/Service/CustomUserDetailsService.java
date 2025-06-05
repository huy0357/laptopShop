package com.example.demo.Service;

import com.example.demo.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {


   private UserService userService;
   public CustomUserDetailsService(UserService userService) {
       this.userService = userService;
   }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       //lấy user ở domain gán vào user ở sercurity
        com.example.demo.domain.User user = this.userService.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
            //logic
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+ user.getRole().getName())));
    }
}
