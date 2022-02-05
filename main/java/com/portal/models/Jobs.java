package com.portal.models;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Jobs implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="job_id")
    private Long id;
    private String cName;
    private String cWeb;
    private Integer vacancy;
    private Integer salary;
    private String jobDesc;
    private String location;
    private String mail;
    private String posting;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ivDate;
    private String ivTime;
    private String ivPlace;
    private String skills;
    private String exp;

    public Jobs(String cName, String cWeb, Integer vacancy, Integer salary, String jobDesc, String location, String posting, LocalDate ivDate, String ivTime, String ivPlace, String skills) {
        this.cName = cName;
        this.cWeb = cWeb;
        this.vacancy = vacancy;
        this.salary = salary;
        this.jobDesc = jobDesc;
        this.location = location;
        this.posting = posting;
        this.ivDate = ivDate;
        this.ivTime = ivTime;
        this.ivPlace = ivPlace;
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Jobs jobs = (Jobs) o;

        return Objects.equals(id, jobs.id);
    }

    @Override
    public int hashCode() {
        return 1587332848;
    }
}
