package com.project.ors.repository;

import com.project.ors.entity.Person;
import com.project.ors.entity.enums.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PersonRepository extends Repository<Person, Integer>{
    Person findByUsernameAndDeleteFlag(String username, Boolean deleteFlag);

    @Query(value = "SELECT p.id, " +
            "g.id, " +
            "a.id," +
            "u.id, " +
            "schedule.id, "+
            "st.id," +
            "schedule.delete_flag, schedule.end_date, schedule.start_date, schedule.name, schedule.status," +
            "st.day, st.delete_flag, st.end_time, st.start_time," +
            "u.contact_method, u.contact_next_spring, u.contact_next_summer, u.emergency_contact, u.form_no," +
            "p.delete_flag, p.email, p.first_name, p.last_name, p.password, p.role, p.username," +
            "g.first_name as g_first_name, g.last_name as g_last_name," +
            "g.primary_contact, g.secondary_contact, " +
            "a.mailing_address, a.city, a.state, a.zip_code" +
            " FROM person p JOIN user u ON p.id = u.person_id LEFT OUTER JOIN address a ON p.id = a.person_id  LEFT OUTER JOIN schedule  ON p.id = schedule.person_id LEFT OUTER JOIN schedule_time st ON schedule.id = st.schedule_id LEFT OUTER JOIN guardian g ON u.id = g.user_id WHERE p.role = 'ROLE_USER' AND u.enrolled = ?1", nativeQuery = true)
    List<Person> findByEnrolled(Boolean enrolled);

    @Query(value = "SELECT p.id,u.id,s.id," +
            "p.delete_flag, p.email, p.first_name, p.last_name, p.password, p.role, p.username," +
            "u.contact_method, u.contact_next_spring, u.contact_next_summer, u.emergency_contact, u.form_no," +
            "s.delete_flag, s.end_date, s.start_date, s.name, s.status " +
            "FROM person p JOIN user u ON p.id = u.person_id JOIN schedule s ON u.schedule_id = s.id WHERE p.delete_flag = false AND s.person_id = ?1", nativeQuery = true)
    List<Person> findAssignedUsers(Integer id);

    List<Person> findByDeleteFlagAndRole(Boolean deleteFlag, Role role);

    List<Person> findAllByDeleteFlag(Boolean deleteFlag);

    Person save(Person person);

    Person findByIdAndRoleAndDeleteFlag(Integer id, Role role, Boolean deleteFlag);

    Person findByIdAndDeleteFlag(Integer id, Boolean deleteFlag);

    List<Person> findByRoleAndDeleteFlag(Role role, Boolean deleteFlag);

    Person findByEmailAndDeleteFlag(String email, Boolean deleteFlag);
}
