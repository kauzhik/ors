package com.project.ors.service;

import com.project.ors.entity.Person;
import com.project.ors.entity.Routine;
import com.project.ors.entity.Schedule;
import com.project.ors.entity.enums.Status;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface ScheduleService {
    Routine save(Schedule schedule);
    List<Schedule> findByPersonIdAndDeleteFlag(Integer id, Boolean deleteFlag);
    List<Routine> findAllByDeleteFlag(Boolean deleteFlag);
    List<Schedule> findAllByStatusAndDeleteFlag(Status status, Boolean deleteFlag);
}
