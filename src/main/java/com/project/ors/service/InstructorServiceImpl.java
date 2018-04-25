package com.project.ors.service;

import com.project.ors.entity.Address;
import com.project.ors.entity.Person;
import com.project.ors.entity.enums.Role;
import com.project.ors.repository.AddressRepository;
import com.project.ors.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService{
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<Person> findAllByRoleAndDeleteFlag(Role role, Boolean flag) {
        return instructorRepository.findAllByRoleAndDeleteFlag(Role.ROLE_INSTRUCTOR, false);
    }

    @Override
    public Person save(Person person) {
        List<Address> savedAdd = new ArrayList<Address>();

        Person p1 = new Person();
//        First save the person and get it's id
        p1 = instructorRepository.save(person);

        if( p1 != null) {
            for(Address add: person.getAddresses()){
//                Set person's id in Address and save it
                add.setPerson(p1);
                savedAdd.add(addressRepository.save(add));
            }
//            **Set saved list of Address id in Person to be returned
            p1.setAddresses(savedAdd);
            return p1;
        }
        else return null;
    }
}
