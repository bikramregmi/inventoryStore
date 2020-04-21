package com.bikram.smart.web.rest;

import com.bikram.smart.config.ApplicationProperties;
import com.bikram.smart.service.ImageViewService;
import com.bikram.smart.service.dto.ContentMapperDTO;
import com.codahale.metrics.annotation.Timed;
import com.bikram.smart.service.ContentService;
import com.bikram.smart.web.rest.util.HeaderUtil;
import com.bikram.smart.web.rest.util.PaginationUtil;
import com.bikram.smart.service.dto.ContentDTO;
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
 * REST controller for managing Content.
 */
@RestController
@RequestMapping("/api")
public class ContentResource {

    private final Logger log = LoggerFactory.getLogger(ContentResource.class);

    private static final String ENTITY_NAME = "content";

    private final ContentService contentService;

    private final ApplicationProperties applicationProperties;

    private final ImageViewService imageViewService;

    public ContentResource(ContentService contentService, ApplicationProperties applicationProperties, ImageViewService imageViewService) {
        this.contentService = contentService;
        this.applicationProperties = applicationProperties;
        this.imageViewService = imageViewService;
    }

    /**
     * POST  /contents : Create a new content.
     *
     * @param contentDTO the contentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentDTO, or with status 400 (Bad Request) if the content has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contents")
    @Timed
    public ResponseEntity<ContentDTO> createContent(@RequestParam(value="file",required=false) MultipartFile file,@RequestParam(value="content",required = false) String contentDTO) throws URISyntaxException, IOException {
        log.debug("REST request to save Content : {}", contentDTO);
        if (contentDTO== null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "NULL", "A new content cannot be Null")).body(null);
        }
        ContentDTO result;
        ObjectMapper mapper = new ObjectMapper();
        ContentMapperDTO content = mapper.readValue(contentDTO, ContentMapperDTO.class);
        result = contentService.save(content,file);
        return ResponseEntity.created(new URI("/api/contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contents : Updates an existing content.
     *
     * @param contentDTO the contentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentDTO,
     * or with status 400 (Bad Request) if the contentDTO is not valid,
     * or with status 500 (Internal Server Error) if the contentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contents")
    @Timed
    public ResponseEntity<ContentDTO> updateContent(@RequestBody ContentDTO contentDTO) throws URISyntaxException {
        log.debug("REST request to update Content : {}", contentDTO);
     /*   if (contentDTO.getId() == null) {
            return createContent(contentDTO);
        }
        ContentDTO result = contentService.save(contentDTO);*/
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contentDTO.getId().toString()))
            .body(null);
    }

    /**
     * GET  /contents : get all the contents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contents in body
     */
    @GetMapping("/contents")
    @Timed
    public ResponseEntity<List<ContentDTO>> getAllContents(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Contents");
        Page<ContentDTO> page = contentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contents/:id : get the "id" content.
     *
     * @param id the id of the contentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contents/{id}")
    @Timed
    public ResponseEntity<ContentDTO> getContent(@PathVariable Long id) {
        log.debug("REST request to get Content : {}", id);
        ContentDTO contentDTO = contentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contentDTO));
    }

    /**
     * DELETE  /contents/:id : delete the "id" content.
     *
     * @param id the id of the contentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contents/{id}")
    @Timed
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        log.debug("REST request to delete Content : {}", id);
        contentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get Top Two Recent News
     */
    @GetMapping("/news/recent")
    @Timed
    public ResponseEntity<List<ContentDTO>> getRecentNews() {
        log.debug("REST request to get a page of Recent News");
        Page<ContentDTO> page = contentService.getRecentNews();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/news/recent");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * Get Top Two Recent Events
     */
    @GetMapping("/events/recent")
    @Timed
    public ResponseEntity<List<ContentDTO>> getRecentEvents() {
        log.debug("REST request to get a page of Recent Events");
        Page<ContentDTO> page = contentService.getRecentEvents();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/events/recent");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/view/content")
    public void viewBannerImage(@RequestParam("fileName") String fileName, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (!StringUtils.isEmpty(fileName)) {
            fileName = URLDecoder.decode(fileName, "UTF-8");
            File imagePath = new File(applicationProperties.getFileBasePath() + "/content/" + fileName);
            try {
                imageViewService.imageViewService(fileName, imagePath, response);
            }catch(Exception e){
                log.debug("Exception",e);
            }
        }
    }
}
