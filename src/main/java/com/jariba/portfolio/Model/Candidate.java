package com.jariba.portfolio.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(
        uniqueConstraints = {
                    @UniqueConstraint(
                            name = "user_name constraint",
                            columnNames  = "user_name")
        }
)
@ToString(exclude = "profileImageObject")
@EnableTransactionManagement
public class Candidate {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotEmpty(message = "Name Cannot be empty")
    private String fullName;


    @NotEmpty(message = "UserName is Required")
    private String userName;

    @Transient
    @URL
    private String profileImage;

    private String title;
    private String about;


    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH})
    private List<Qualification> qualifications;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH})
    private List<Skill> skills;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH})
    private List<AreasOfInterest> areasOfInterest;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH})
    private List<Project> projects;


    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH})
    private List<Experience> experiences;

    @JsonIgnore
    @OneToOne(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH})
    private ProfileImage profileImageObject;



    private String location;
    private String leetcode;
    private String hackerrank;


    @OneToOne(mappedBy = "candidate",  fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REFRESH} )
    private  Contact contact;


    @JsonIgnore
    private Boolean activeStatus = true;
    @JsonIgnore
    @CreatedDate
    private Date createdDate = new Date(System.currentTimeMillis());

    public void setAreasOfInterest(List<AreasOfInterest> areasOfInterest) {
        for(AreasOfInterest a : areasOfInterest) {
            a.setCandidate(this);
        }
        this.areasOfInterest = areasOfInterest;
    }

    public void setProjects(List<Project>projects) {
        for(Project p : projects) {
            p.setCandidate(this);
        }
        this.projects = projects;
    }

    public void setExperiences(List<Experience>experiences) {
        for(Experience e : experiences) {
            e.setCandidate(this);
        }
        this.experiences = experiences;
    }
    public void setSkills(List<Skill>skills) {
        for(Skill s : skills) {
            s.setCandidate(this);
        }
        this.skills = skills;
    }
    public void setQualifications(List<Qualification>qualifications) {
        for(Qualification q : qualifications) {
            q.setCandidate(this);
        }
        this.qualifications = qualifications;
    }

    public void setProfileImageObject(ProfileImage profileImageObject) {
        this.profileImageObject = profileImageObject;
        profileImageObject.setCandidate(this);
    }
    public void setContact(Contact contact) {
        this.contact = contact;
        contact.setCandidate(this);
    }

}
