package com.bikram.smart.service.mapper;

import com.bikram.smart.domain.*;
import com.bikram.smart.service.dto.CollegeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity College and its DTO CollegeDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, ContactMapper.class, ProgramMapper.class, })
public interface CollegeMapper extends EntityMapper <CollegeDTO, College> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "address.city", target = "addressName")
    @Mapping(source = "contact.mobileNumber", target = "contactName")
    CollegeDTO toDto(College college);



    @Mapping(source = "addressId", target = "address")

    @Mapping(source = "contactId", target = "contact")
    College toEntity(CollegeDTO collegeDTO); 
    default College fromId(Long id) {
        if (id == null) {
            return null;
        }
        College college = new College();
        college.setId(id);
        return college;
    }
}
