package com.bikram.smart.repository;

import com.bikram.smart.domain.Subject;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Subject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    
    @Query("select distinct subject from Subject subject left join fetch subject.courses")
    List<Subject> findAllWithEagerRelationships();

    @Query("select subject from Subject subject left join fetch subject.courses where subject.id =:id")
    Subject findOneWithEagerRelationships(@Param("id") Long id);
    
}
