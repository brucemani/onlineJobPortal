package com.portal.repositories;

import com.portal.models.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ApplicantRepository extends JpaRepository<Applicant,Long> {
    @Query("SELECT a from Applicant a WHERE a.id=?1")
    public Applicant getApplicant(Long id);

    @Query("SELECT a from Applicant a WHERE a.email=?1")
    public Applicant mailAlreadyExist(String mail);
    @Query("SELECT a from  Applicant a where a.names.username=?1")
    public Applicant getApplicantByName(String name);

}
