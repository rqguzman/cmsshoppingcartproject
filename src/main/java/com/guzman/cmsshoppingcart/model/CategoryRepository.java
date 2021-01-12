package com.guzman.cmsshoppingcart.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guzman.cmsshoppingcart.model.data.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	public Category findByName(String name);
	
	public List<Category> findAllByOrderBySortingAsc();

	public Category findBySlug(String slug);
}
