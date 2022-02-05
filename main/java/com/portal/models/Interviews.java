package com.portal.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Interviews implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="in_id")
    private Long id;
    private Long userId;
    private Long jobId;
    public Interviews(Long userId, Long jobId) {
        this.userId = userId;
        this.jobId = jobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Interviews that = (Interviews) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 516721508;
    }
}
