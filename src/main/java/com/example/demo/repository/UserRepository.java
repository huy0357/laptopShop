package com.example.demo.repository;


import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User hoidanit);

    List<User> findOneByEmail(String email);

    List<User> findAll();

   User findById(long id);

   User deleteById(long id);

   boolean existsByEmail(String email);

   User findByEmail(String email);
}
