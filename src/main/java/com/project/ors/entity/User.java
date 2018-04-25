package com.project.ors.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ors.entity.enums.Contact;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;
    @Column(name = "form_no")
    private String formNo;
    @Column(name = "emergency_contact")
    private String emergencyContact;
    @Column(name = "contact_next_spring")
    private boolean contactNextSpring;
    @Column(name = "contact_next_summer")
    private boolean contactNextSummer;
    @Column(name = "contact_method")
    @Enumerated(EnumType.STRING)
    private Contact contact;
    private boolean enrolled;
    @Column(name = "delete_flag")
    private boolean deleteFlag;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
//    Below annotation was needed because serialization of schedule {id} happened before lazy loading as not all the properties are being passed here..
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Schedule schedule;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "schedule_id")
////    https://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    private Schedule schedule;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Guardian guardian;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    @JsonIgnore
    public void setPerson(Person person) {
        this.person = person;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public boolean isContactNextSpring() {
        return contactNextSpring;
    }

    public void setContactNextSpring(boolean contactNextSpring) {
        this.contactNextSpring = contactNextSpring;
    }

    public boolean isContactNextSummer() {
        return contactNextSummer;
    }

    public void setContactNextSummer(boolean contactNextSummer) {
        this.contactNextSummer = contactNextSummer;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public boolean isEnrolled() {
        return enrolled;
    }

    public void setEnrolled(boolean enrolled) {
        this.enrolled = enrolled;
    }

    public Guardian getGuardian() {
        return guardian;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
