package com.guzman.cmsshoppingcart.model;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guzman.cmsshoppingcart.model.data.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
