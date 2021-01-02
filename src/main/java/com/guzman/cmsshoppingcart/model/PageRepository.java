package com.guzman.cmsshoppingcart.model;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guzman.cmsshoppingcart.model.data.Page;

public interface PageRepository extends JpaRepository<Page, Integer> {

}
