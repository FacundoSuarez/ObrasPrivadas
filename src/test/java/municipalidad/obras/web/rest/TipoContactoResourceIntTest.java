package municipalidad.obras.web.rest;

import municipalidad.obras.ObrasPrivadas4App;

import municipalidad.obras.domain.TipoContacto;
import municipalidad.obras.repository.TipoContactoRepository;
import municipalidad.obras.service.TipoContactoService;
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
 * Test class for the TipoContactoResource REST controller.
 *
 * @see TipoContactoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasPrivadas4App.class)
public class TipoContactoResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoContactoRepository tipoContactoRepository;

    @Autowired
    private TipoContactoService tipoContactoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoContactoMockMvc;

    private TipoContacto tipoContacto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoContactoResource tipoContactoResource = new TipoContactoResource(tipoContactoService);
        this.restTipoContactoMockMvc = MockMvcBuilders.standaloneSetup(tipoContactoResource)
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
    public static TipoContacto createEntity(EntityManager em) {
        TipoContacto tipoContacto = new TipoContacto()
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoContacto;
    }

    @Before
    public void initTest() {
        tipoContacto = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoContacto() throws Exception {
        int databaseSizeBeforeCreate = tipoContactoRepository.findAll().size();

        // Create the TipoContacto
        restTipoContactoMockMvc.perform(post("/api/tipo-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoContacto)))
            .andExpect(status().isCreated());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoContacto testTipoContacto = tipoContactoList.get(tipoContactoList.size() - 1);
        assertThat(testTipoContacto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createTipoContactoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoContactoRepository.findAll().size();

        // Create the TipoContacto with an existing ID
        tipoContacto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoContactoMockMvc.perform(post("/api/tipo-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoContacto)))
            .andExpect(status().isBadRequest());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTipoContactos() throws Exception {
        // Initialize the database
        tipoContactoRepository.saveAndFlush(tipoContacto);

        // Get all the tipoContactoList
        restTipoContactoMockMvc.perform(get("/api/tipo-contactos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoContacto.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getTipoContacto() throws Exception {
        // Initialize the database
        tipoContactoRepository.saveAndFlush(tipoContacto);

        // Get the tipoContacto
        restTipoContactoMockMvc.perform(get("/api/tipo-contactos/{id}", tipoContacto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoContacto.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoContacto() throws Exception {
        // Get the tipoContacto
        restTipoContactoMockMvc.perform(get("/api/tipo-contactos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoContacto() throws Exception {
        // Initialize the database
        tipoContactoService.save(tipoContacto);

        int databaseSizeBeforeUpdate = tipoContactoRepository.findAll().size();

        // Update the tipoContacto
        TipoContacto updatedTipoContacto = tipoContactoRepository.findOne(tipoContacto.getId());
        // Disconnect from session so that the updates on updatedTipoContacto are not directly saved in db
        em.detach(updatedTipoContacto);
        updatedTipoContacto
            .descripcion(UPDATED_DESCRIPCION);

        restTipoContactoMockMvc.perform(put("/api/tipo-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoContacto)))
            .andExpect(status().isOk());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeUpdate);
        TipoContacto testTipoContacto = tipoContactoList.get(tipoContactoList.size() - 1);
        assertThat(testTipoContacto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoContacto() throws Exception {
        int databaseSizeBeforeUpdate = tipoContactoRepository.findAll().size();

        // Create the TipoContacto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoContactoMockMvc.perform(put("/api/tipo-contactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoContacto)))
            .andExpect(status().isCreated());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoContacto() throws Exception {
        // Initialize the database
        tipoContactoService.save(tipoContacto);

        int databaseSizeBeforeDelete = tipoContactoRepository.findAll().size();

        // Get the tipoContacto
        restTipoContactoMockMvc.perform(delete("/api/tipo-contactos/{id}", tipoContacto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoContacto.class);
        TipoContacto tipoContacto1 = new TipoContacto();
        tipoContacto1.setId(1L);
        TipoContacto tipoContacto2 = new TipoContacto();
        tipoContacto2.setId(tipoContacto1.getId());
        assertThat(tipoContacto1).isEqualTo(tipoContacto2);
        tipoContacto2.setId(2L);
        assertThat(tipoContacto1).isNotEqualTo(tipoContacto2);
        tipoContacto1.setId(null);
        assertThat(tipoContacto1).isNotEqualTo(tipoContacto2);
    }
}
