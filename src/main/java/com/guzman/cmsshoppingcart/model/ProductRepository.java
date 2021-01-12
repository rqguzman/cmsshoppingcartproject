package com.guzman.cmsshoppingcart.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.guzman.cmsshoppingcart.model.data.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	public Product findBySlug(String theSlug);

	public Product findBySlugAndIdNot(String theSlug, int id);

	public Page<Product> findAll(Pageable pageable);

	public List<Product> findAllByCategoryId(String theCategoryId, Pageable pageable);

	public long countByCategoryId(String theCategoryId);

}
