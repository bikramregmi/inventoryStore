package com.bikram.smart.repository;

import com.bikram.smart.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.Instant;

/**
 * Spring Data JPA repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    @Query("select c from Course c order by c.createdDate DESC ")
    Page<Course> findTop2ByCreatedDate( Pageable pageable);

}
