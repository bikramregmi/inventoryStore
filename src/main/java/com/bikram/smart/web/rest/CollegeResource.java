package com.bikram.smart.web.rest;

import com.bikram.smart.config.ApplicationProperties;
import com.bikram.smart.service.ImageViewService;
import com.codahale.metrics.annotation.Timed;
import com.bikram.smart.service.CollegeService;
import com.bikram.smart.web.rest.util.HeaderUtil;
import com.bikram.smart.web.rest.util.PaginationUtil;
import com.bikram.smart.service.dto.CollegeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import liquibase.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing College.
 */
@RestController
@RequestMapping("/api")
public class CollegeResource {

    private final Logger log = LoggerFactory.getLogger(CollegeResource.class);

    private static final String ENTITY_NAME = "college";

    private final CollegeService collegeService;

    private final ApplicationProperties applicationProperties;

    private final ImageViewService imageViewService;

    public CollegeResource(CollegeService collegeService, ApplicationProperties applicationProperties, ImageViewService imageViewService) {
        this.collegeService = collegeService;
        this.applicationProperties = applicationProperties;
        this.imageViewService = imageViewService;
    }

    /**
     * POST  /colleges : Create a new college.
     *
     * @param collegeDTO the collegeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collegeDTO, or with status 400 (Bad Request) if the college has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/colleges")
    @Timed
    public ResponseEntity<CollegeDTO> createCollege(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "college", required = false) String collegeDTO) throws URISyntaxException, IOException {
        log.debug("REST request to save College : {}", collegeDTO);
        if (collegeDTO == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new college cannot already have an ID")).body(null);
        }
        CollegeDTO result;
        ObjectMapper mapper = new ObjectMapper();
        CollegeDTO college = mapper.readValue(collegeDTO, CollegeDTO.class);
        result = collegeService.save(college, file);
        return ResponseEntity.created(new URI("/api/colleges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /colleges : Updates an existing college.
     *
     * @param collegeDTO the collegeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collegeDTO,
     * or with status 400 (Bad Request) if the collegeDTO is not valid,
     * or with status 500 (Internal Server Error) if the collegeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/colleges")
    @Timed
    public ResponseEntity<CollegeDTO> updateCollege(@RequestBody CollegeDTO collegeDTO) throws URISyntaxException {
        log.debug("REST request to update College : {}", collegeDTO);
     /*   if (collegeDTO.getId() == null) {
            return createCollege(collegeDTO);
        }
        CollegeDTO result = collegeService.save(collegeDTO);*/
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collegeDTO.getId().toString()))
            .body(null);
    }

    /**
     * GET  /colleges : get all the colleges.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of colleges in body
     */
    @GetMapping("/colleges")
    @Timed
    public ResponseEntity<List<CollegeDTO>> getAllColleges(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Colleges");
        Page<CollegeDTO> page = collegeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/colleges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /colleges/:id : get the "id" college.
     *
     * @param id the id of the collegeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collegeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/colleges/{id}")
    @Timed
    public ResponseEntity<CollegeDTO> getCollege(@PathVariable Long id) {
        log.debug("REST request to get College : {}", id);
        CollegeDTO collegeDTO = collegeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collegeDTO));
    }

    /**
     * DELETE  /colleges/:id : delete the "id" college.
     *
     * @param id the id of the collegeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/colleges/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollege(@PathVariable Long id) {
        log.debug("REST request to delete College : {}", id);
        collegeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/view/college")
    public void viewBannerImage(@RequestParam("fileName") String fileName, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (!StringUtils.isEmpty(fileName)) {
            fileName = URLDecoder.decode(fileName, "UTF-8");
            File imagePath = new File(applicationProperties.getFileBasePath() + "/college/" + fileName);
            try {
                imageViewService.imageViewService(fileName, imagePath, response);
            } catch (Exception e) {
                log.debug("Exception", e);
            }
        }
    }
}
