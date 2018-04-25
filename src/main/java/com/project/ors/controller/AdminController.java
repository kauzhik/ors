package com.project.ors.controller;

import com.project.ors.config.EmailService;
import com.project.ors.entity.*;
import com.project.ors.entity.enums.Role;
import com.project.ors.service.InstructorService;
import com.project.ors.service.PersonService;
import com.project.ors.service.ScheduleService;
import com.project.ors.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import static javafx.scene.AccessibleAttribute.ROLE;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private String instructorRegUrl = "http://192.168.43.204:4200/instructor-registration";

    @Autowired
    UserService userService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    PersonService personService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    public SimpleMailMessage template;

    @Autowired
    EmailService emailService;

//    Get enrolled user list
    @RequestMapping(
            value = "/users",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Person>> getUserList(){
        List<Person> userList = personService.findByDeleteFlagAndRole(false, Role.ROLE_USER);
        if(userList != null){
            return ResponseEntity.ok(userList);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

//    Get instructor list
    @RequestMapping(
            value = "/instructor/list",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity getInstructorList(){
        List<Person> instructorList = instructorService.findAllByRoleAndDeleteFlag(Role.ROLE_INSTRUCTOR ,false );
        if(instructorList == null){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Person>>(instructorList, HttpStatus.OK);
    }

    //    Save instructor
    @RequestMapping(
            value = "/schedule/save",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Routine> saveSchedule(@RequestBody Schedule schedule){
        Routine s = scheduleService.save(schedule);
        if(s != null){
            return new ResponseEntity<Routine>(s, HttpStatus.OK);
        }else return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //    Get all schedule list
    @RequestMapping(
            value = "/schedule/list",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity getScheduleList(){
        List<Routine> scheduleList = scheduleService.findAllByDeleteFlag(false);
        if(scheduleList == null){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Routine>>(scheduleList, HttpStatus.OK);
    }

    //    Get all  schedule list with no instructor
    @RequestMapping(
            value = "/schedule/no_instructor/list",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity getNoInstructorScheduleList(){
        List<Routine> scheduleList = scheduleService.findAllByDeleteFlag(true);
        if(scheduleList == null){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Routine>>(scheduleList, HttpStatus.OK);
    }

    //    Save instructor
    @RequestMapping(
            value = "/instructor/mail",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity sendInvitation(@RequestBody EmailAddress emailAddress){

        String body = String.format(template.getText(),instructorRegUrl);
        String subject = "Registration";
        emailService.sendSimpleMessage(emailAddress.getEmail(), subject, body);
        if(true){
            return new ResponseEntity<Schedule>(HttpStatus.OK);
        }else return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);


    }



}
