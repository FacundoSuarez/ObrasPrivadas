package municipalidad.obras.web.rest;

import municipalidad.obras.ObrasPrivadas4App;

import municipalidad.obras.domain.PlanoDetalle;
import municipalidad.obras.repository.PlanoDetalleRepository;
import municipalidad.obras.service.PlanoDetalleService;
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

import municipalidad.obras.domain.enumeration.EstadoPlano;
/**
 * Test class for the PlanoDetalleResource REST controller.
 *
 * @see PlanoDetalleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasPrivadas4App.class)
public class PlanoDetalleResourceIntTest {

    private static final EstadoPlano DEFAULT_ESTADO = EstadoPlano.ENTREGADO;
    private static final EstadoPlano UPDATED_ESTADO = EstadoPlano.REVISION;

    @Autowired
    private PlanoDetalleRepository planoDetalleRepository;

    @Autowired
    private PlanoDetalleService planoDetalleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlanoDetalleMockMvc;

    private PlanoDetalle planoDetalle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanoDetalleResource planoDetalleResource = new PlanoDetalleResource(planoDetalleService);
        this.restPlanoDetalleMockMvc = MockMvcBuilders.standaloneSetup(planoDetalleResource)
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
    public static PlanoDetalle createEntity(EntityManager em) {
        PlanoDetalle planoDetalle = new PlanoDetalle()
            .estado(DEFAULT_ESTADO);
        return planoDetalle;
    }

    @Before
    public void initTest() {
        planoDetalle = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoDetalle() throws Exception {
        int databaseSizeBeforeCreate = planoDetalleRepository.findAll().size();

        // Create the PlanoDetalle
        restPlanoDetalleMockMvc.perform(post("/api/plano-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoDetalle)))
            .andExpect(status().isCreated());

        // Validate the PlanoDetalle in the database
        List<PlanoDetalle> planoDetalleList = planoDetalleRepository.findAll();
        assertThat(planoDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoDetalle testPlanoDetalle = planoDetalleList.get(planoDetalleList.size() - 1);
        assertThat(testPlanoDetalle.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createPlanoDetalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoDetalleRepository.findAll().size();

        // Create the PlanoDetalle with an existing ID
        planoDetalle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoDetalleMockMvc.perform(post("/api/plano-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoDetalle)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoDetalle in the database
        List<PlanoDetalle> planoDetalleList = planoDetalleRepository.findAll();
        assertThat(planoDetalleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlanoDetalles() throws Exception {
        // Initialize the database
        planoDetalleRepository.saveAndFlush(planoDetalle);

        // Get all the planoDetalleList
        restPlanoDetalleMockMvc.perform(get("/api/plano-detalles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void getPlanoDetalle() throws Exception {
        // Initialize the database
        planoDetalleRepository.saveAndFlush(planoDetalle);

        // Get the planoDetalle
        restPlanoDetalleMockMvc.perform(get("/api/plano-detalles/{id}", planoDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planoDetalle.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanoDetalle() throws Exception {
        // Get the planoDetalle
        restPlanoDetalleMockMvc.perform(get("/api/plano-detalles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoDetalle() throws Exception {
        // Initialize the database
        planoDetalleService.save(planoDetalle);

        int databaseSizeBeforeUpdate = planoDetalleRepository.findAll().size();

        // Update the planoDetalle
        PlanoDetalle updatedPlanoDetalle = planoDetalleRepository.findOne(planoDetalle.getId());
        // Disconnect from session so that the updates on updatedPlanoDetalle are not directly saved in db
        em.detach(updatedPlanoDetalle);
        updatedPlanoDetalle
            .estado(UPDATED_ESTADO);

        restPlanoDetalleMockMvc.perform(put("/api/plano-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlanoDetalle)))
            .andExpect(status().isOk());

        // Validate the PlanoDetalle in the database
        List<PlanoDetalle> planoDetalleList = planoDetalleRepository.findAll();
        assertThat(planoDetalleList).hasSize(databaseSizeBeforeUpdate);
        PlanoDetalle testPlanoDetalle = planoDetalleList.get(planoDetalleList.size() - 1);
        assertThat(testPlanoDetalle.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = planoDetalleRepository.findAll().size();

        // Create the PlanoDetalle

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlanoDetalleMockMvc.perform(put("/api/plano-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoDetalle)))
            .andExpect(status().isCreated());

        // Validate the PlanoDetalle in the database
        List<PlanoDetalle> planoDetalleList = planoDetalleRepository.findAll();
        assertThat(planoDetalleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlanoDetalle() throws Exception {
        // Initialize the database
        planoDetalleService.save(planoDetalle);

        int databaseSizeBeforeDelete = planoDetalleRepository.findAll().size();

        // Get the planoDetalle
        restPlanoDetalleMockMvc.perform(delete("/api/plano-detalles/{id}", planoDetalle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlanoDetalle> planoDetalleList = planoDetalleRepository.findAll();
        assertThat(planoDetalleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoDetalle.class);
        PlanoDetalle planoDetalle1 = new PlanoDetalle();
        planoDetalle1.setId(1L);
        PlanoDetalle planoDetalle2 = new PlanoDetalle();
        planoDetalle2.setId(planoDetalle1.getId());
        assertThat(planoDetalle1).isEqualTo(planoDetalle2);
        planoDetalle2.setId(2L);
        assertThat(planoDetalle1).isNotEqualTo(planoDetalle2);
        planoDetalle1.setId(null);
        assertThat(planoDetalle1).isNotEqualTo(planoDetalle2);
    }
}
