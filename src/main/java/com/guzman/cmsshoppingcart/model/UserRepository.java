package com.guzman.cmsshoppingcart.model;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guzman.cmsshoppingcart.model.data.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);

}
