package com.bikram.smart.service.impl;

import com.bikram.smart.config.ApplicationProperties;
import com.bikram.smart.service.ProgramService;
import com.bikram.smart.domain.Program;
import com.bikram.smart.repository.ProgramRepository;
import com.bikram.smart.service.dto.ProgramDTO;
import com.bikram.smart.service.mapper.ProgramMapper;
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
 * Service Implementation for managing Program.
 */
@Service
@Transactional
public class ProgramServiceImpl implements ProgramService{

    private final Logger log = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final ProgramRepository programRepository;

    private final ProgramMapper programMapper;

    private final ApplicationProperties applicationProperties;

    public ProgramServiceImpl(ProgramRepository programRepository, ProgramMapper programMapper, ApplicationProperties applicationProperties) {
        this.programRepository = programRepository;
        this.programMapper = programMapper;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Save a program.
     *
     * @param programDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProgramDTO save(ProgramDTO programDTO, MultipartFile file) throws IOException {
        log.debug("Request to save Program : {}", programDTO);
        String courseImagePath;
        if(file!=null) {
            courseImagePath = getImagePath(file);
            programDTO.setImage(courseImagePath);
        }
        Program program = programMapper.toEntity(programDTO);
        program = programRepository.save(program);
        return programMapper.toDto(program);
    }

    /**
     *  Get all the programs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProgramDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Programs");
        return programRepository.findAll(pageable)
            .map(programMapper::toDto);
    }

    /**
     *  Get one program by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProgramDTO findOne(Long id) {
        log.debug("Request to get Program : {}", id);
        Program program = programRepository.findOneWithEagerRelationships(id);
        return programMapper.toDto(program);
    }

    /**
     *  Delete the  program by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Program : {}", id);
        programRepository.delete(id);
    }
    private String getImagePath(MultipartFile file) throws IOException {
        String basePath = applicationProperties.getFileBasePath();
        createDirectory(basePath + "/program/");
        String uniqueName = String.valueOf(System.currentTimeMillis());
        File imgFile = new File(basePath + "/program/" + uniqueName + file.getOriginalFilename());
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
