package com.example.demo.repository;

import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {
    Product save(Product hoidanit);

    List<Product> findByName(String name);

    List<Product> findAll();

    Optional<Product> findById(long id);

    Product deleteById(long id);
}
