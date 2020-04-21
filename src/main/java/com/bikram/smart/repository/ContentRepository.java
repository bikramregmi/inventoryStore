package com.bikram.smart.repository;

import com.bikram.smart.domain.Content;
import com.bikram.smart.domain.Course;
import com.bikram.smart.domain.enumeration.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Content entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentRepository extends JpaRepository<Content,Long> {

    @Query("select c from Content c where c.contentType=?1 order by c.createdDate DESC ")
    Page<Content> findTop2ByCreatedDate(ContentType contentType,Pageable pageable);
    
}
