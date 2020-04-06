package com.bikram.smart.service.mapper;

import com.bikram.smart.domain.*;
import com.bikram.smart.service.dto.CourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Course and its DTO CourseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourseMapper extends EntityMapper <CourseDTO, Course> {
    
    
    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
