package com.project.ors.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ors.entity.enums.Status;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

public class Routine {

    @JsonProperty(value = "id")
    private Integer id;
    @JsonProperty(value = "person")
    private Integer person;
    @JsonProperty(value = "startDate")
    private Date startDate;
    @JsonProperty(value = "endDate")
    private Date endDate;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @JsonProperty(value = "deleteFlag")
    private boolean deleteFlag;
    @JsonProperty(value = "scheduleTimes")
    private List<ScheduleTime> scheduleTimes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public List<ScheduleTime> getScheduleTimes() {
        return scheduleTimes;
    }

    public void setScheduleTimes(List<ScheduleTime> scheduleTimes) {
        this.scheduleTimes = scheduleTimes;
    }
}
