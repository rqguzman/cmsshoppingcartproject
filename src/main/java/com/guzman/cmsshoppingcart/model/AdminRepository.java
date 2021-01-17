package com.guzman.cmsshoppingcart.model;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guzman.cmsshoppingcart.model.data.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	public Admin findByUsername(String username);

}
