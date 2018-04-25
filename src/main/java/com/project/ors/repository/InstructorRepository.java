package com.project.ors.repository;

import com.project.ors.entity.Address;
import com.project.ors.entity.Person;
import com.project.ors.entity.enums.Role;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface InstructorRepository extends Repository<Person, Integer>{
    List<Person> findAllByRoleAndDeleteFlag(Role role, Boolean flag);
    Person save(Person person);
}
