package com.project.ors.service;

import com.project.ors.entity.Person;
import com.project.ors.entity.enums.Role;
import com.project.ors.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class PersonServiceImpl implements UserDetailsService, PersonService{

    @Autowired
    PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Person person = personRepository.findByUsernameAndDeleteFlag(username, false);
        if(person == null){
            throw new UsernameNotFoundException(username);
        }
//        GrantedAuthority authority = new SimpleGrantedAuthority(""+person.getRole());
//        UserDetails userDetails = new User(username,
//                person.getPassword(), Arrays.asList(authority));
//        return userDetails;
        return new User(person.getUsername(), person.getPassword(), emptyList());
    }

//    Already used above from PersonRepository Interface. So not needed beyond this
    @Override
    public Person findByUsernameAndDeleteFlag(String username){
        Person person = personRepository.findByUsernameAndDeleteFlag(username, false);
        if(person != null){
            return person;
        }else return null;
    }

    @Override
    public List<Person> findByDeleteFlagAndRole(Boolean deleteFlag, Role role) {
        List<Person> person =  personRepository.findByDeleteFlagAndRole(false, Role.ROLE_USER);
        return person;
    }

    @Override
    public Person findByIdAndDeleteFlag(Integer id, Boolean deleteFlag) {
        Person person = personRepository.findByIdAndDeleteFlag(id, false);
        if(person != null){
            return person;
        }else return null;
    }

    @Override
    public List<Person> findByRoleAndDeleteFlag(Role role, Boolean deleteFlag) {
        List<Person> personList = personRepository.findByRoleAndDeleteFlag(role, false);
        if(personList != null){
            return personList;
        }else return null;
    }

    @Override
    public Person findByEmailAndDeleteFlag(String email, Boolean deleteFlag) {
        return personRepository.findByEmailAndDeleteFlag(email, false);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAllByDeleteFlag(false);
    }

    @Override
    public List<Person> findAssignedUsers(Integer id) {
        return personRepository.findAssignedUsers(id);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }





}

