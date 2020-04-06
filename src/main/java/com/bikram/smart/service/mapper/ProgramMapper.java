package com.bikram.smart.service.mapper;

import com.bikram.smart.domain.*;
import com.bikram.smart.service.dto.ProgramDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Program and its DTO ProgramDTO.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class, })
public interface ProgramMapper extends EntityMapper <ProgramDTO, Program> {
    
    
    default Program fromId(Long id) {
        if (id == null) {
            return null;
        }
        Program program = new Program();
        program.setId(id);
        return program;
    }
}
