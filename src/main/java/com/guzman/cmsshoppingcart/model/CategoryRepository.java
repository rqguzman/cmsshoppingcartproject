package com.guzman.cmsshoppingcart.model;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guzman.cmsshoppingcart.model.data.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
