package com.bikram.smart.service.mapper;

import com.bikram.smart.domain.*;
import com.bikram.smart.service.dto.SubjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Subject and its DTO SubjectDTO.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class, })
public interface SubjectMapper extends EntityMapper <SubjectDTO, Subject> {
    
    
    default Subject fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(id);
        return subject;
    }
}
