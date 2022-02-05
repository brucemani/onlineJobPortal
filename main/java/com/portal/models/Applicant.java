package com.portal.models;

import lombok.*;
import org.hibernate.Hibernate;

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
public class Applicant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="id_gen")
    @SequenceGenerator(name="id_gen", allocationSize = 1,initialValue = 1000)
    @Column(name="can_id")
    private Long id;
    @Embedded
    private Names names;
    private String gender;
    private String password;
    private String email;
    private String phone;
    private Integer experience;
    private String role;
    private LocalDate createdAt;
//    @JsonIgnore
//    @ManyToMany(mappedBy="interApplicant",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
//    private Set<Interviews> interviews=new LinkedHashSet<>();

    public Applicant(Names names, String gender, String password, String email, String phone, Integer experience) {
        this.names = names;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.experience = experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Applicant applicant = (Applicant) o;

        return Objects.equals(id, applicant.id);
    }

    @Override
    public int hashCode() {
        return 1248502903;
    }
}
