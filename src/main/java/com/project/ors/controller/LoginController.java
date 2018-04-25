package com.project.ors.controller;

import com.project.ors.entity.Person;
import com.project.ors.entity.Schedule;
import com.project.ors.entity.UserCredential;
import com.project.ors.service.PersonService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import static com.project.ors.config.SecurityConstants.EXPIRATION_TIME;
import static com.project.ors.config.SecurityConstants.SECRET;
import static com.project.ors.config.SecurityConstants.TOKEN_PREFIX;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    PersonService personService;

    @RequestMapping(
            value = "validate",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Person> validateToken(@RequestBody UserCredential userCredential){

        String sentToken = userCredential.getPassword();

        String user = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(sentToken.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        if (user != null) {
            Person person = personService.findByUsernameAndDeleteFlag(userCredential.getUsername());
            if(person != null){
                return new ResponseEntity<Person>(person, HttpStatus.OK);
            }else return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
