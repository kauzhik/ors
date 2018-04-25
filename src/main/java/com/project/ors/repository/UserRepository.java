package com.project.ors.repository;

import com.project.ors.entity.Person;
import com.project.ors.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<User, Integer>{
    User save(User user);

}
