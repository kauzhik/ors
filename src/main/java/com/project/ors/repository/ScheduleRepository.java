package com.project.ors.repository;

import com.project.ors.entity.Person;
import com.project.ors.entity.Schedule;
import com.project.ors.entity.enums.Status;
import org.springframework.data.repository.Repository;
import java.util.List;

public interface ScheduleRepository extends Repository<Schedule, Integer> {
    Schedule save(Schedule schedule);
    Schedule findById(Integer id);
    List<Schedule> findByPersonAndDeleteFlag(Person person, Boolean deleteFlag);
    List<Schedule> findAllByDeleteFlag(Boolean deleteFlag);
    List<Schedule> findAllByStatusAndDeleteFlag(Status status, Boolean deleteFlag);
}
