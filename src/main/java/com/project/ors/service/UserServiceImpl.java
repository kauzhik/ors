package com.project.ors.service;

import com.project.ors.entity.*;
import com.project.ors.entity.enums.Role;
import com.project.ors.repository.*;
import org.hibernate.dialect.Ingres9Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    GuardianRepository guardianRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public List<Person> save(Person person) {
//        Check if schedule exists
        Integer scheduleId = person.getUser().getSchedule().getId();
        if(scheduleRepository.findById(scheduleId) != null){
            Person p = personRepository.save(person);
            if( p != null){
//            Set saved person's id in User and save User
                User user = person.getUser();
                user.setPerson(p);
                user = userRepository.save(user);

//            Set saved user's id in guardian and save it
                Guardian guardian = person.getUser().getGuardian();
                guardian.setUser(user);
                guardian = guardianRepository.save(guardian);

                for(Address add: person.getAddresses()){
//                Set person's id in Address and save it
                    add.setPerson(p);
                    addressRepository.save(add);
                }
                return personRepository.findByDeleteFlagAndRole(false, Role.ROLE_USER);
            }else return null;
        }else return null;
    }
}
