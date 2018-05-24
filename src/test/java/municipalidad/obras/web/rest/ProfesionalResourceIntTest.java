package municipalidad.obras.web.rest;

import municipalidad.obras.ObrasPrivadas4App;

import municipalidad.obras.domain.Profesional;
import municipalidad.obras.repository.ProfesionalRepository;
import municipalidad.obras.service.ProfesionalService;
import municipalidad.obras.web.rest.errors.ExceptionTranslator;

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

import static municipalidad.obras.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProfesionalResource REST controller.
 *
 * @see ProfesionalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasPrivadas4App.class)
public class ProfesionalResourceIntTest {

    private static final String DEFAULT_PROFESION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESION = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULA = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULA = "BBBBBBBBBB";

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private ProfesionalService profesionalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfesionalMockMvc;

    private Profesional profesional;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfesionalResource profesionalResource = new ProfesionalResource(profesionalService);
        this.restProfesionalMockMvc = MockMvcBuilders.standaloneSetup(profesionalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profesional createEntity(EntityManager em) {
        Profesional profesional = new Profesional()
            .profesion(DEFAULT_PROFESION)
            .matricula(DEFAULT_MATRICULA);
        return profesional;
    }

    @Before
    public void initTest() {
        profesional = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfesional() throws Exception {
        int databaseSizeBeforeCreate = profesionalRepository.findAll().size();

        // Create the Profesional
        restProfesionalMockMvc.perform(post("/api/profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesional)))
            .andExpect(status().isCreated());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeCreate + 1);
        Profesional testProfesional = profesionalList.get(profesionalList.size() - 1);
        assertThat(testProfesional.getProfesion()).isEqualTo(DEFAULT_PROFESION);
        assertThat(testProfesional.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
    }

    @Test
    @Transactional
    public void createProfesionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profesionalRepository.findAll().size();

        // Create the Profesional with an existing ID
        profesional.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfesionalMockMvc.perform(post("/api/profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesional)))
            .andExpect(status().isBadRequest());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProfesionals() throws Exception {
        // Initialize the database
        profesionalRepository.saveAndFlush(profesional);

        // Get all the profesionalList
        restProfesionalMockMvc.perform(get("/api/profesionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profesional.getId().intValue())))
            .andExpect(jsonPath("$.[*].profesion").value(hasItem(DEFAULT_PROFESION.toString())))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA.toString())));
    }

    @Test
    @Transactional
    public void getProfesional() throws Exception {
        // Initialize the database
        profesionalRepository.saveAndFlush(profesional);

        // Get the profesional
        restProfesionalMockMvc.perform(get("/api/profesionals/{id}", profesional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profesional.getId().intValue()))
            .andExpect(jsonPath("$.profesion").value(DEFAULT_PROFESION.toString()))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfesional() throws Exception {
        // Get the profesional
        restProfesionalMockMvc.perform(get("/api/profesionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfesional() throws Exception {
        // Initialize the database
        profesionalService.save(profesional);

        int databaseSizeBeforeUpdate = profesionalRepository.findAll().size();

        // Update the profesional
        Profesional updatedProfesional = profesionalRepository.findOne(profesional.getId());
        // Disconnect from session so that the updates on updatedProfesional are not directly saved in db
        em.detach(updatedProfesional);
        updatedProfesional
            .profesion(UPDATED_PROFESION)
            .matricula(UPDATED_MATRICULA);

        restProfesionalMockMvc.perform(put("/api/profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfesional)))
            .andExpect(status().isOk());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeUpdate);
        Profesional testProfesional = profesionalList.get(profesionalList.size() - 1);
        assertThat(testProfesional.getProfesion()).isEqualTo(UPDATED_PROFESION);
        assertThat(testProfesional.getMatricula()).isEqualTo(UPDATED_MATRICULA);
    }

    @Test
    @Transactional
    public void updateNonExistingProfesional() throws Exception {
        int databaseSizeBeforeUpdate = profesionalRepository.findAll().size();

        // Create the Profesional

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfesionalMockMvc.perform(put("/api/profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profesional)))
            .andExpect(status().isCreated());

        // Validate the Profesional in the database
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfesional() throws Exception {
        // Initialize the database
        profesionalService.save(profesional);

        int databaseSizeBeforeDelete = profesionalRepository.findAll().size();

        // Get the profesional
        restProfesionalMockMvc.perform(delete("/api/profesionals/{id}", profesional.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Profesional> profesionalList = profesionalRepository.findAll();
        assertThat(profesionalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profesional.class);
        Profesional profesional1 = new Profesional();
        profesional1.setId(1L);
        Profesional profesional2 = new Profesional();
        profesional2.setId(profesional1.getId());
        assertThat(profesional1).isEqualTo(profesional2);
        profesional2.setId(2L);
        assertThat(profesional1).isNotEqualTo(profesional2);
        profesional1.setId(null);
        assertThat(profesional1).isNotEqualTo(profesional2);
    }
}
