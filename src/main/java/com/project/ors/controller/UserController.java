package com.project.ors.controller;

import com.project.ors.entity.EmailAddress;
import com.project.ors.entity.Person;
import com.project.ors.entity.Schedule;
import com.project.ors.entity.enums.Status;
import com.project.ors.service.PersonService;
import com.project.ors.service.ScheduleService;
import com.project.ors.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PersonService personService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(
            value = "save",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Person>> saveUser(@RequestBody Person person){
        String password = person.getPassword();
        if(person != null && person.getUsername() != null && person.getEmail() != null && password != null) {
            if((personService.findByUsernameAndDeleteFlag(person.getUsername()) != null) || (personService.findByEmailAndDeleteFlag(person.getEmail(), false) != null)){
                return new ResponseEntity(HttpStatus.CONFLICT);
            }else{
                person.setPassword(bCryptPasswordEncoder.encode(password));
                List<Person> p = userService.save(person);
                if (p != null) {
                    return new ResponseEntity<List<Person>>(p, HttpStatus.OK);
                } else
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(
            value = "update",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Person>> updateUser(@RequestBody Person person){
        String password = person.getPassword();
        if( password != null) {
            person.setPassword(bCryptPasswordEncoder.encode(password));
        }
        else{
//            Get the old password and save it after encryption
        }
            List<Person> p = userService.save(person);
            if (p != null) {
                return new ResponseEntity<List<Person>>(p, HttpStatus.OK);
            } else
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(
            value = "schedules/active",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Schedule>> getActiveSchedules(){
        List<Schedule> s = scheduleService.findAllByStatusAndDeleteFlag(Status.ACTIVE, false);
        if(s != null){
            return new ResponseEntity<List<Schedule>>(s, HttpStatus.OK);
        }else
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
