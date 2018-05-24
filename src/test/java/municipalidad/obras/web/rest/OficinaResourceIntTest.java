package municipalidad.obras.web.rest;

import municipalidad.obras.ObrasPrivadas4App;

import municipalidad.obras.domain.Oficina;
import municipalidad.obras.repository.OficinaRepository;
import municipalidad.obras.service.OficinaService;
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
 * Test class for the OficinaResource REST controller.
 *
 * @see OficinaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasPrivadas4App.class)
public class OficinaResourceIntTest {

    private static final String DEFAULT_PROFESIONAL = "AAAAAAAAAA";
    private static final String UPDATED_PROFESIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_CORRECCIONES = "AAAAAAAAAA";
    private static final String UPDATED_CORRECCIONES = "BBBBBBBBBB";

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private OficinaService oficinaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOficinaMockMvc;

    private Oficina oficina;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OficinaResource oficinaResource = new OficinaResource(oficinaService);
        this.restOficinaMockMvc = MockMvcBuilders.standaloneSetup(oficinaResource)
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
    public static Oficina createEntity(EntityManager em) {
        Oficina oficina = new Oficina()
            .profesional(DEFAULT_PROFESIONAL)
            .correcciones(DEFAULT_CORRECCIONES);
        return oficina;
    }

    @Before
    public void initTest() {
        oficina = createEntity(em);
    }

    @Test
    @Transactional
    public void createOficina() throws Exception {
        int databaseSizeBeforeCreate = oficinaRepository.findAll().size();

        // Create the Oficina
        restOficinaMockMvc.perform(post("/api/oficinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oficina)))
            .andExpect(status().isCreated());

        // Validate the Oficina in the database
        List<Oficina> oficinaList = oficinaRepository.findAll();
        assertThat(oficinaList).hasSize(databaseSizeBeforeCreate + 1);
        Oficina testOficina = oficinaList.get(oficinaList.size() - 1);
        assertThat(testOficina.getProfesional()).isEqualTo(DEFAULT_PROFESIONAL);
        assertThat(testOficina.getCorrecciones()).isEqualTo(DEFAULT_CORRECCIONES);
    }

    @Test
    @Transactional
    public void createOficinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oficinaRepository.findAll().size();

        // Create the Oficina with an existing ID
        oficina.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOficinaMockMvc.perform(post("/api/oficinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oficina)))
            .andExpect(status().isBadRequest());

        // Validate the Oficina in the database
        List<Oficina> oficinaList = oficinaRepository.findAll();
        assertThat(oficinaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOficinas() throws Exception {
        // Initialize the database
        oficinaRepository.saveAndFlush(oficina);

        // Get all the oficinaList
        restOficinaMockMvc.perform(get("/api/oficinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oficina.getId().intValue())))
            .andExpect(jsonPath("$.[*].profesional").value(hasItem(DEFAULT_PROFESIONAL.toString())))
            .andExpect(jsonPath("$.[*].correcciones").value(hasItem(DEFAULT_CORRECCIONES.toString())));
    }

    @Test
    @Transactional
    public void getOficina() throws Exception {
        // Initialize the database
        oficinaRepository.saveAndFlush(oficina);

        // Get the oficina
        restOficinaMockMvc.perform(get("/api/oficinas/{id}", oficina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oficina.getId().intValue()))
            .andExpect(jsonPath("$.profesional").value(DEFAULT_PROFESIONAL.toString()))
            .andExpect(jsonPath("$.correcciones").value(DEFAULT_CORRECCIONES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOficina() throws Exception {
        // Get the oficina
        restOficinaMockMvc.perform(get("/api/oficinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOficina() throws Exception {
        // Initialize the database
        oficinaService.save(oficina);

        int databaseSizeBeforeUpdate = oficinaRepository.findAll().size();

        // Update the oficina
        Oficina updatedOficina = oficinaRepository.findOne(oficina.getId());
        // Disconnect from session so that the updates on updatedOficina are not directly saved in db
        em.detach(updatedOficina);
        updatedOficina
            .profesional(UPDATED_PROFESIONAL)
            .correcciones(UPDATED_CORRECCIONES);

        restOficinaMockMvc.perform(put("/api/oficinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOficina)))
            .andExpect(status().isOk());

        // Validate the Oficina in the database
        List<Oficina> oficinaList = oficinaRepository.findAll();
        assertThat(oficinaList).hasSize(databaseSizeBeforeUpdate);
        Oficina testOficina = oficinaList.get(oficinaList.size() - 1);
        assertThat(testOficina.getProfesional()).isEqualTo(UPDATED_PROFESIONAL);
        assertThat(testOficina.getCorrecciones()).isEqualTo(UPDATED_CORRECCIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingOficina() throws Exception {
        int databaseSizeBeforeUpdate = oficinaRepository.findAll().size();

        // Create the Oficina

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOficinaMockMvc.perform(put("/api/oficinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oficina)))
            .andExpect(status().isCreated());

        // Validate the Oficina in the database
        List<Oficina> oficinaList = oficinaRepository.findAll();
        assertThat(oficinaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOficina() throws Exception {
        // Initialize the database
        oficinaService.save(oficina);

        int databaseSizeBeforeDelete = oficinaRepository.findAll().size();

        // Get the oficina
        restOficinaMockMvc.perform(delete("/api/oficinas/{id}", oficina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Oficina> oficinaList = oficinaRepository.findAll();
        assertThat(oficinaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Oficina.class);
        Oficina oficina1 = new Oficina();
        oficina1.setId(1L);
        Oficina oficina2 = new Oficina();
        oficina2.setId(oficina1.getId());
        assertThat(oficina1).isEqualTo(oficina2);
        oficina2.setId(2L);
        assertThat(oficina1).isNotEqualTo(oficina2);
        oficina1.setId(null);
        assertThat(oficina1).isNotEqualTo(oficina2);
    }
}
