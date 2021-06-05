package ru.skillbox.team13.service;


import ru.skillbox.team13.entity.User;

import java.util.List;

public interface UserService {
    //на входе должен быть userDto
    User register(User user);

    List<User> getAll();

    User findByName(String username);

    User findById(Long id);

    void delete(Long id);
}
