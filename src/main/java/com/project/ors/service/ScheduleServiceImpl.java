package com.project.ors.service;

import com.project.ors.entity.Person;
import com.project.ors.entity.Routine;
import com.project.ors.entity.Schedule;
import com.project.ors.entity.ScheduleTime;
import com.project.ors.entity.enums.Role;
import com.project.ors.entity.enums.Status;
import com.project.ors.repository.PersonRepository;
import com.project.ors.repository.ScheduleRepository;
import com.project.ors.repository.ScheduleTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleTimeRepository scheduleTimeRepository;

    @Autowired
    PersonRepository personRepository;

    @Override
    public Routine save(Schedule schedule) {
//        Check if instructor exists
        Integer personId = schedule.getPerson().getId();
        if(personRepository.findByIdAndRoleAndDeleteFlag(personId, Role.ROLE_INSTRUCTOR, false) != null){
            Schedule s = scheduleRepository.save(schedule);
            if(s != null){
                for(ScheduleTime scheduleTime: schedule.getScheduleTimes()){
//                Set schedule's id in ScheduleTime and save it
                    scheduleTime.setSchedule(s);
                    scheduleTimeRepository.save(scheduleTime);
                }
                Routine routine = new Routine();
                routine.setId(s.getId());
                routine.setPerson(s.getPerson().getId());
                routine.setDeleteFlag(s.isDeleteFlag());
                routine.setStartDate(s.getStartDate());
                routine.setEndDate(s.getEndDate());
                routine.setName(s.getName());
                routine.setStatus(s.getStatus());
                routine.setScheduleTimes(s.getScheduleTimes());
                return routine;
            }else return null;
        }else return null;
    }

    @Override
    public List<Schedule> findByPersonIdAndDeleteFlag(Integer id, Boolean deleteFlag) {
        Person person = personRepository.findByIdAndDeleteFlag(id, false);
        List<Schedule> schedules = scheduleRepository.findByPersonAndDeleteFlag(person, false);
        if(schedules != null){
            return schedules;
        }else return null;
    }

    @Override
    public List<Routine> findAllByDeleteFlag(Boolean deleteFlag) {
        List<Schedule> scheduleList =  scheduleRepository.findAllByDeleteFlag(false);
        List<Person> person = personRepository.findByRoleAndDeleteFlag(Role.ROLE_INSTRUCTOR, deleteFlag);
        ArrayList<Routine> routines = new ArrayList<Routine>();
        if(scheduleList != null && person != null){
                for(Person p: person){
                    Integer pId = p.getId();
                    List<Schedule> schedules = scheduleRepository.findByPersonAndDeleteFlag(p, false);
                    if(schedules != null){
                        for(Schedule personSchedule: schedules){
                            Routine routine = new Routine();

                            routine.setId(personSchedule.getId());
                                routine.setPerson(personSchedule.getPerson().getId());
                                routine.setDeleteFlag(personSchedule.isDeleteFlag());
                                routine.setStartDate(personSchedule.getStartDate());
                                routine.setEndDate(personSchedule.getEndDate());
                                routine.setName(personSchedule.getName());
                                routine.setStatus(personSchedule.getStatus());
                                routine.setScheduleTimes(personSchedule.getScheduleTimes());

                            routines.add(routine);
                        }

                    }
            }
            return routines;
        }else return null;
    }

    @Override
    public List<Schedule> findAllByStatusAndDeleteFlag(Status status, Boolean deleteFlag) {
        List<Schedule> scheduleList =  scheduleRepository.findAllByStatusAndDeleteFlag(Status.ACTIVE, false);
        if(scheduleList != null){
            return scheduleList;
        }else return null;
    }

}
