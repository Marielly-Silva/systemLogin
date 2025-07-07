package com.example.systemLogin.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.systemLogin.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findById(long id);

	User findByEmail(String email);
}
