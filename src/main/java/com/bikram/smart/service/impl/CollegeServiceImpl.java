package com.bikram.smart.service.impl;

import com.bikram.smart.config.ApplicationProperties;
import com.bikram.smart.service.CollegeService;
import com.bikram.smart.domain.College;
import com.bikram.smart.repository.CollegeRepository;
import com.bikram.smart.service.dto.CollegeDTO;
import com.bikram.smart.service.mapper.CollegeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * Service Implementation for managing College.
 */
@Service
@Transactional
public class CollegeServiceImpl implements CollegeService{

    private final Logger log = LoggerFactory.getLogger(CollegeServiceImpl.class);

    private final CollegeRepository collegeRepository;

    private final CollegeMapper collegeMapper;

    private final ApplicationProperties applicationProperties;

    public CollegeServiceImpl(CollegeRepository collegeRepository, CollegeMapper collegeMapper, ApplicationProperties applicationProperties) {
        this.collegeRepository = collegeRepository;
        this.collegeMapper = collegeMapper;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Save a college.
     *
     * @param collegeDTO the entity to save
     * @param file
     * @return the persisted entity
     */
    @Override
    public CollegeDTO save(CollegeDTO collegeDTO, MultipartFile file) throws IOException {
        log.debug("Request to save College : {}", collegeDTO);
        String courseImagePath;
        if(file!=null) {
            courseImagePath = getImagePath(file);
            collegeDTO.setImage(courseImagePath);
        }
        College college = collegeMapper.toEntity(collegeDTO);
        college = collegeRepository.save(college);
        return collegeMapper.toDto(college);
    }

    /**
     *  Get all the colleges.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CollegeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Colleges");
        return collegeRepository.findAll(pageable)
            .map(collegeMapper::toDto);
    }

    /**
     *  Get one college by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CollegeDTO findOne(Long id) {
        log.debug("Request to get College : {}", id);
        College college = collegeRepository.findOneWithEagerRelationships(id);
        return collegeMapper.toDto(college);
    }

    /**
     *  Delete the  college by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete College : {}", id);
        collegeRepository.delete(id);
    }

    private String getImagePath(MultipartFile file) throws IOException {
        String basePath = applicationProperties.getFileBasePath();
        createDirectory(basePath + "/college/");
        String uniqueName = String.valueOf(System.currentTimeMillis());
        File imgFile = new File(basePath + "/college/" + uniqueName + file.getOriginalFilename());
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
