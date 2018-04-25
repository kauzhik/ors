package com.project.ors.service;

import com.project.ors.entity.Person;
import com.project.ors.entity.enums.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface PersonService {
    Person findByUsernameAndDeleteFlag(String username);

    List<Person> findAll();

    List<Person> findAssignedUsers(Integer id);

    Person save(Person person);

    List<Person> findByDeleteFlagAndRole( Boolean deleteFlag, Role role);

    Person findByIdAndDeleteFlag(Integer id, Boolean deleteFlag);

    List<Person> findByRoleAndDeleteFlag(Role role, Boolean deleteFlag);

    Person findByEmailAndDeleteFlag(String email, Boolean deleteFlag);
}
