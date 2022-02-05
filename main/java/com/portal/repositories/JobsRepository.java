package com.portal.repositories;

import com.portal.models.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface JobsRepository extends JpaRepository<Jobs,Long> {
}