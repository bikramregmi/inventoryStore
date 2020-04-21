package com.bikram.smart.service;

import com.bikram.smart.service.dto.ContentDTO;
import com.bikram.smart.service.dto.ContentMapperDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service Interface for managing Content.
 */
public interface ContentService {

    /**
     * Save a content.
     *
     * @param contentDTO the entity to save
     * @param file
     * @return the persisted entity
     */
    ContentDTO save(ContentMapperDTO contentDTO, MultipartFile file) throws IOException;

    /**
     *  Get all the contents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContentDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" content.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContentDTO findOne(Long id);

    /**
     *  Delete the "id" content.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Page<ContentDTO> getRecentNews();

    Page<ContentDTO> getRecentEvents();


}
