package com.project.ors.service;

import com.project.ors.entity.Person;
import com.project.ors.entity.enums.Role;

import java.util.List;

public interface InstructorService {
    List<Person> findAllByRoleAndDeleteFlag(Role role, Boolean flag);
    Person save(Person person);
}
