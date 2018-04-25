package com.project.ors.service;

import com.project.ors.entity.Person;
import com.project.ors.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<Person> save(Person person);
}
