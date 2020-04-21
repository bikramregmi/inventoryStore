package com.bikram.smart.service.impl;

import com.bikram.smart.config.ApplicationProperties;
import com.bikram.smart.service.CourseService;
import com.bikram.smart.domain.Course;
import com.bikram.smart.repository.CourseRepository;
import com.bikram.smart.service.dto.CourseDTO;
import com.bikram.smart.service.mapper.CourseMapper;
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
 * Service Implementation for managing Course.
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService{

    private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final ApplicationProperties applicationProperties;


    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper, ApplicationProperties applicationProperties) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Save a course.
     *
     * @param courseDTO the entity to save
     * @param file
     * @return the persisted entity
     */
    @Override
    public CourseDTO save(CourseDTO courseDTO, MultipartFile file) throws IOException {
        log.debug("Request to save Course : {}", courseDTO);
        String courseImagePath;
        if(file!=null) {
            courseImagePath = getImagePath(file);
            courseDTO.setImage(courseImagePath);
        }
        Course course = courseMapper.toEntity(courseDTO);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    /**
     *  Get all the courses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CourseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Courses");
        return courseRepository.findAll(pageable)
            .map(courseMapper::toDto);
    }

    /**
     *  Get one course by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CourseDTO findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        Course course = courseRepository.findOne(id);
        return courseMapper.toDto(course);
    }

    /**
     *  Delete the  course by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.delete(id);
    }

    @Override
    public Page<CourseDTO> getRecentCourses(Pageable pageable1) {
        Pageable pageable = new PageRequest(0,2);
        return  courseRepository.findTop2ByCreatedDate(pageable)
            .map(courseMapper::toDto);
    }


    private String getImagePath(MultipartFile file) throws IOException {
        String basePath = applicationProperties.getFileBasePath();
        createDirectory(basePath + "/course/");
        String uniqueName = String.valueOf(System.currentTimeMillis());
        File imgFile = new File(basePath + "/course/" + uniqueName + file.getOriginalFilename());
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
