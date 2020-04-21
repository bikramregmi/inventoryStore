package com.bikram.smart.service.mapper;

import com.bikram.smart.domain.*;
import com.bikram.smart.service.dto.ContentDTO;

import com.bikram.smart.service.dto.ContentMapperDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity Content and its DTO ContentDTO.
 */
@Mapper(componentModel = "spring", uses = {CollegeMapper.class, })
public interface ContentMapper extends EntityMapper <ContentDTO, Content> {

    @Mapping(source = "college.id", target = "collegeId")
//    @Mapping(source = "college.title", target = "collegeTitle")
    ContentDTO toDto(Content content); 

    @Mapping(source = "collegeId", target = "college")
    Content toEntity(ContentMapperDTO contentDTO);
    default Content fromId(Long id) {
        if (id == null) {
            return null;
        }
        Content content = new Content();
        content.setId(id);
        return content;
    }
}
