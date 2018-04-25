package com.project.ors.repository;

import com.project.ors.entity.Schedule;
import com.project.ors.entity.ScheduleTime;
import org.springframework.data.repository.Repository;

public interface ScheduleTimeRepository extends Repository<ScheduleTime, Integer> {
    ScheduleTime save(ScheduleTime scheduleTime);
}
