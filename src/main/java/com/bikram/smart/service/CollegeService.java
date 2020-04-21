package com.bikram.smart.service;

import com.bikram.smart.service.dto.CollegeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service Interface for managing College.
 */
public interface CollegeService {

    /**
     * Save a college.
     *
     * @param collegeDTO the entity to save
     * @param file
     * @return the persisted entity
     */
    CollegeDTO save(CollegeDTO collegeDTO, MultipartFile file) throws IOException;

    /**
     *  Get all the colleges.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CollegeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" college.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CollegeDTO findOne(Long id);

    /**
     *  Delete the "id" college.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
