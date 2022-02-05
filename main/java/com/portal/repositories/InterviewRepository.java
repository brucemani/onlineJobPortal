package com.portal.repositories;

import com.portal.models.Interviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface InterviewRepository extends JpaRepository<Interviews,Long> {
}
