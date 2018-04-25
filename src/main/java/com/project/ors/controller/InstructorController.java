package com.project.ors.controller;

import com.project.ors.entity.Person;
import com.project.ors.entity.Schedule;
import com.project.ors.service.InstructorService;
import com.project.ors.service.PersonService;
import com.project.ors.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping(value = "/instructor")
public class InstructorController {

    @Autowired
    InstructorService instructorService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PersonService personService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public InstructorController(BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //    create instructor
    @RequestMapping(
            value = "/save",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity saveInstructor(@RequestBody Person person){
        String password = person.getPassword();
        if(person != null && person.getUsername() != null && password != null){
            if((personService.findByUsernameAndDeleteFlag(person.getUsername()) != null) || (personService.findByEmailAndDeleteFlag(person.getEmail(), false) != null)){
                return new ResponseEntity(HttpStatus.CONFLICT);
            }else{
                person.setPassword(bCryptPasswordEncoder.encode(password));
                Person p = instructorService.save(person);
                if(p != null){
                    return new ResponseEntity<Person>(p, HttpStatus.OK);
                }else{
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }
        }else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    //    update instructor
    @RequestMapping(
            value = "/update",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity updateInstructor(@RequestBody Person person){
        if(person != null){
            String password = person.getPassword();
            if(password == null){
                password = personService.findByIdAndDeleteFlag(person.getId(), false).getPassword();
                person.setPassword(password);
            }else{
                person.setPassword(bCryptPasswordEncoder.encode(password));
            }
            Person p = instructorService.save(person);
            if(p != null){
                return new ResponseEntity<Person>(p, HttpStatus.OK);
            }else{
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    //    Get assigned schedules
    @RequestMapping(
            value = "/schedules/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Schedule>> getAssigendSchedules(@PathVariable(value = "id") Integer id){
        List<Schedule> schedules = scheduleService.findByPersonIdAndDeleteFlag(id, false);
        if(schedules != null){
            return new ResponseEntity<List<Schedule>>(schedules, HttpStatus.OK);
        }else return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //    Get assigned users
    @RequestMapping(
            value = "/users/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Person>> getAssigendUsers(@PathVariable(value = "id") Integer id){
        List<Person> p = personService.findAssignedUsers(id);
        if(p != null){
            return new ResponseEntity<List<Person>>(p, HttpStatus.OK);
        }else return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
