package com.example.tanmeyah.product.repository;

import com.example.tanmeyah.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
