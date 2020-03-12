package com.exampleone.blogtemanbelajar.dao;


import java.util.Optional;
import com.exampleone.blogtemanbelajar.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByFirstname(String firstname);
	Optional<User> findById(Long id);
}
