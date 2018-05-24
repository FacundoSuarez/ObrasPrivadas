package municipalidad.obras.web.rest;

import municipalidad.obras.ObrasPrivadas4App;

import municipalidad.obras.domain.TipoPlano;
import municipalidad.obras.repository.TipoPlanoRepository;
import municipalidad.obras.service.TipoPlanoService;
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
 * Test class for the TipoPlanoResource REST controller.
 *
 * @see TipoPlanoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasPrivadas4App.class)
public class TipoPlanoResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoPlanoRepository tipoPlanoRepository;

    @Autowired
    private TipoPlanoService tipoPlanoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoPlanoMockMvc;

    private TipoPlano tipoPlano;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoPlanoResource tipoPlanoResource = new TipoPlanoResource(tipoPlanoService);
        this.restTipoPlanoMockMvc = MockMvcBuilders.standaloneSetup(tipoPlanoResource)
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
    public static TipoPlano createEntity(EntityManager em) {
        TipoPlano tipoPlano = new TipoPlano()
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoPlano;
    }

    @Before
    public void initTest() {
        tipoPlano = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoPlano() throws Exception {
        int databaseSizeBeforeCreate = tipoPlanoRepository.findAll().size();

        // Create the TipoPlano
        restTipoPlanoMockMvc.perform(post("/api/tipo-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPlano)))
            .andExpect(status().isCreated());

        // Validate the TipoPlano in the database
        List<TipoPlano> tipoPlanoList = tipoPlanoRepository.findAll();
        assertThat(tipoPlanoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoPlano testTipoPlano = tipoPlanoList.get(tipoPlanoList.size() - 1);
        assertThat(testTipoPlano.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createTipoPlanoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoPlanoRepository.findAll().size();

        // Create the TipoPlano with an existing ID
        tipoPlano.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoPlanoMockMvc.perform(post("/api/tipo-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPlano)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPlano in the database
        List<TipoPlano> tipoPlanoList = tipoPlanoRepository.findAll();
        assertThat(tipoPlanoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTipoPlanos() throws Exception {
        // Initialize the database
        tipoPlanoRepository.saveAndFlush(tipoPlano);

        // Get all the tipoPlanoList
        restTipoPlanoMockMvc.perform(get("/api/tipo-planos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPlano.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getTipoPlano() throws Exception {
        // Initialize the database
        tipoPlanoRepository.saveAndFlush(tipoPlano);

        // Get the tipoPlano
        restTipoPlanoMockMvc.perform(get("/api/tipo-planos/{id}", tipoPlano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoPlano.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoPlano() throws Exception {
        // Get the tipoPlano
        restTipoPlanoMockMvc.perform(get("/api/tipo-planos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoPlano() throws Exception {
        // Initialize the database
        tipoPlanoService.save(tipoPlano);

        int databaseSizeBeforeUpdate = tipoPlanoRepository.findAll().size();

        // Update the tipoPlano
        TipoPlano updatedTipoPlano = tipoPlanoRepository.findOne(tipoPlano.getId());
        // Disconnect from session so that the updates on updatedTipoPlano are not directly saved in db
        em.detach(updatedTipoPlano);
        updatedTipoPlano
            .descripcion(UPDATED_DESCRIPCION);

        restTipoPlanoMockMvc.perform(put("/api/tipo-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoPlano)))
            .andExpect(status().isOk());

        // Validate the TipoPlano in the database
        List<TipoPlano> tipoPlanoList = tipoPlanoRepository.findAll();
        assertThat(tipoPlanoList).hasSize(databaseSizeBeforeUpdate);
        TipoPlano testTipoPlano = tipoPlanoList.get(tipoPlanoList.size() - 1);
        assertThat(testTipoPlano.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoPlano() throws Exception {
        int databaseSizeBeforeUpdate = tipoPlanoRepository.findAll().size();

        // Create the TipoPlano

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoPlanoMockMvc.perform(put("/api/tipo-planos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPlano)))
            .andExpect(status().isCreated());

        // Validate the TipoPlano in the database
        List<TipoPlano> tipoPlanoList = tipoPlanoRepository.findAll();
        assertThat(tipoPlanoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoPlano() throws Exception {
        // Initialize the database
        tipoPlanoService.save(tipoPlano);

        int databaseSizeBeforeDelete = tipoPlanoRepository.findAll().size();

        // Get the tipoPlano
        restTipoPlanoMockMvc.perform(delete("/api/tipo-planos/{id}", tipoPlano.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoPlano> tipoPlanoList = tipoPlanoRepository.findAll();
        assertThat(tipoPlanoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoPlano.class);
        TipoPlano tipoPlano1 = new TipoPlano();
        tipoPlano1.setId(1L);
        TipoPlano tipoPlano2 = new TipoPlano();
        tipoPlano2.setId(tipoPlano1.getId());
        assertThat(tipoPlano1).isEqualTo(tipoPlano2);
        tipoPlano2.setId(2L);
        assertThat(tipoPlano1).isNotEqualTo(tipoPlano2);
        tipoPlano1.setId(null);
        assertThat(tipoPlano1).isNotEqualTo(tipoPlano2);
    }
}
