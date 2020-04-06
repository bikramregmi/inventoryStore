package com.bikram.smart.web.rest;

import com.bikram.smart.SmartWebApp;

import com.bikram.smart.domain.College;
import com.bikram.smart.repository.CollegeRepository;
import com.bikram.smart.service.CollegeService;
import com.bikram.smart.service.dto.CollegeDTO;
import com.bikram.smart.service.mapper.CollegeMapper;
import com.bikram.smart.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CollegeResource REST controller.
 *
 * @see CollegeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartWebApp.class)
public class CollegeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_FIELD = "BBBBBBBBBB";

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollegeMockMvc;

    private College college;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CollegeResource collegeResource = new CollegeResource(collegeService);
        this.restCollegeMockMvc = MockMvcBuilders.standaloneSetup(collegeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static College createEntity(EntityManager em) {
        College college = new College()
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .description(DEFAULT_DESCRIPTION)
            .extraField(DEFAULT_EXTRA_FIELD);
        return college;
    }

    @Before
    public void initTest() {
        college = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollege() throws Exception {
        int databaseSizeBeforeCreate = collegeRepository.findAll().size();

        // Create the College
        CollegeDTO collegeDTO = collegeMapper.toDto(college);
        restCollegeMockMvc.perform(post("/api/colleges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collegeDTO)))
            .andExpect(status().isCreated());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeCreate + 1);
        College testCollege = collegeList.get(collegeList.size() - 1);
        assertThat(testCollege.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollege.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCollege.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCollege.getExtraField()).isEqualTo(DEFAULT_EXTRA_FIELD);
    }

    @Test
    @Transactional
    public void createCollegeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collegeRepository.findAll().size();

        // Create the College with an existing ID
        college.setId(1L);
        CollegeDTO collegeDTO = collegeMapper.toDto(college);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollegeMockMvc.perform(post("/api/colleges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collegeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllColleges() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        // Get all the collegeList
        restCollegeMockMvc.perform(get("/api/colleges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(college.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].extraField").value(hasItem(DEFAULT_EXTRA_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getCollege() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        // Get the college
        restCollegeMockMvc.perform(get("/api/colleges/{id}", college.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(college.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.extraField").value(DEFAULT_EXTRA_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCollege() throws Exception {
        // Get the college
        restCollegeMockMvc.perform(get("/api/colleges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollege() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);
        int databaseSizeBeforeUpdate = collegeRepository.findAll().size();

        // Update the college
        College updatedCollege = collegeRepository.findOne(college.getId());
        updatedCollege
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .description(UPDATED_DESCRIPTION)
            .extraField(UPDATED_EXTRA_FIELD);
        CollegeDTO collegeDTO = collegeMapper.toDto(updatedCollege);

        restCollegeMockMvc.perform(put("/api/colleges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collegeDTO)))
            .andExpect(status().isOk());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeUpdate);
        College testCollege = collegeList.get(collegeList.size() - 1);
        assertThat(testCollege.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollege.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCollege.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCollege.getExtraField()).isEqualTo(UPDATED_EXTRA_FIELD);
    }

    @Test
    @Transactional
    public void updateNonExistingCollege() throws Exception {
        int databaseSizeBeforeUpdate = collegeRepository.findAll().size();

        // Create the College
        CollegeDTO collegeDTO = collegeMapper.toDto(college);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollegeMockMvc.perform(put("/api/colleges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collegeDTO)))
            .andExpect(status().isCreated());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollege() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);
        int databaseSizeBeforeDelete = collegeRepository.findAll().size();

        // Get the college
        restCollegeMockMvc.perform(delete("/api/colleges/{id}", college.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(College.class);
        College college1 = new College();
        college1.setId(1L);
        College college2 = new College();
        college2.setId(college1.getId());
        assertThat(college1).isEqualTo(college2);
        college2.setId(2L);
        assertThat(college1).isNotEqualTo(college2);
        college1.setId(null);
        assertThat(college1).isNotEqualTo(college2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollegeDTO.class);
        CollegeDTO collegeDTO1 = new CollegeDTO();
        collegeDTO1.setId(1L);
        CollegeDTO collegeDTO2 = new CollegeDTO();
        assertThat(collegeDTO1).isNotEqualTo(collegeDTO2);
        collegeDTO2.setId(collegeDTO1.getId());
        assertThat(collegeDTO1).isEqualTo(collegeDTO2);
        collegeDTO2.setId(2L);
        assertThat(collegeDTO1).isNotEqualTo(collegeDTO2);
        collegeDTO1.setId(null);
        assertThat(collegeDTO1).isNotEqualTo(collegeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collegeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collegeMapper.fromId(null)).isNull();
    }
}
