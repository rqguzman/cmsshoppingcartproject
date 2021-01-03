package com.guzman.cmsshoppingcart.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guzman.cmsshoppingcart.model.data.Page;

public interface PageRepository extends JpaRepository<Page, Integer> {

	public Page findBySlug(String slug);
	
	// @Query("SELECT p FROM Page p WHERE p.id != :theId AND p.slug = :theSlug")
	// public Page findBySlug(int theId, String theSlug);
	
	public Page findBySlugAndIdNot(String theSlug, int theId);
	
	public List<Page> findAllByOrderBySortingAsc();
	
}
