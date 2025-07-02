package com.example.systemLogin.controller;

import org.springframework.data.repository.CrudRepository;

public interface userRepository extends CrudRepository<userRepository, Long> {

	userRepository findById(long id);
}
