package com.example.systemLogin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.systemLogin.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findById(long id);
	
	@Query(value = "SELECT * FROM systemlogin.user WHERE email = :email AND password = :password", nativeQuery = true)
	public User login(String email, String password);
}
