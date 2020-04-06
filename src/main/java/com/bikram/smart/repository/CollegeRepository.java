package com.bikram.smart.repository;

import com.bikram.smart.domain.College;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the College entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollegeRepository extends JpaRepository<College,Long> {
    
    @Query("select distinct college from College college left join fetch college.programs")
    List<College> findAllWithEagerRelationships();

    @Query("select college from College college left join fetch college.programs where college.id =:id")
    College findOneWithEagerRelationships(@Param("id") Long id);
    
}
