package com.bikram.smart.service.impl;

import com.bikram.smart.config.ApplicationProperties;
import com.bikram.smart.domain.enumeration.ContentType;
import com.bikram.smart.service.ContentService;
import com.bikram.smart.domain.Content;
import com.bikram.smart.repository.ContentRepository;
import com.bikram.smart.service.dto.ContentDTO;
import com.bikram.smart.service.dto.ContentMapperDTO;
import com.bikram.smart.service.mapper.ContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * Service Implementation for managing Content.
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    private final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    private final ApplicationProperties applicationProperties;

    public ContentServiceImpl(ContentRepository contentRepository, ContentMapper contentMapper, ApplicationProperties applicationProperties) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Save a content.
     *
     * @param contentDTO the entity to save
     * @param file
     * @return the persisted entity
     */
    @Override
    public ContentDTO save(ContentMapperDTO contentDTO, MultipartFile file) throws IOException {
        log.debug("Request to save Content : {}", contentDTO);
        String courseImagePath;
        if (file != null) {
            courseImagePath = getImagePath(file);
            contentDTO.setImage(courseImagePath);
        }
        Content content = contentMapper.toEntity(contentDTO);
        content = contentRepository.save(content);
        return contentMapper.toDto(content);
    }

    /**
     * Get all the contents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contents");
        return contentRepository.findAll(pageable)
            .map(contentMapper::toDto);
    }

    /**
     * Get one content by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContentDTO findOne(Long id) {
        log.debug("Request to get Content : {}", id);
        Content content = contentRepository.findOne(id);
        return contentMapper.toDto(content);
    }

    /**
     * Delete the  content by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Content : {}", id);
        contentRepository.delete(id);
    }

    @Override
    public Page<ContentDTO> getRecentNews() {
        Pageable pageable = new PageRequest(0, 2);
        return contentRepository.findTop2ByCreatedDate(ContentType.News, pageable)
            .map(contentMapper::toDto);
    }

    @Override
    public Page<ContentDTO> getRecentEvents() {
        Pageable pageable = new PageRequest(0, 5);
        return contentRepository.findTop2ByCreatedDate(ContentType.Events, pageable)
            .map(contentMapper::toDto);
    }
    private String getImagePath(MultipartFile file) throws IOException {
        String basePath = applicationProperties.getFileBasePath();
        createDirectory(basePath + "/content/");
        String uniqueName = String.valueOf(System.currentTimeMillis());
        File imgFile = new File(basePath + "/content/" + uniqueName + file.getOriginalFilename());
        file.transferTo(imgFile);
        return uniqueName+file.getOriginalFilename();
    }

    private void createDirectory(String basePath) {
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

    }
}
