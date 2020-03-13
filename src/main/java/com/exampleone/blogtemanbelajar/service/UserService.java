package com.exampleone.blogtemanbelajar.service;

import com.exampleone.blogtemanbelajar.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService{
  List<User> findAll();
  User save(User user);
  void delete(long id);
  Optional<User> findById(Long id);
}