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
public class Company implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="com_id")
    @SequenceGenerator(name="cim_id",allocationSize = 1,initialValue = 1000)
    private Long id;
    private String cName;
    private String cWeb;
    private String cLocation;
    @Column(unique = true)
    private String cMail;
    private String password;
    private LocalDate createdAt;
    private String roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Company company = (Company) o;

        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return 56842787;
    }
}
