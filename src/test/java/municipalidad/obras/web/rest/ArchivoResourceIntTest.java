package municipalidad.obras.web.rest;

import municipalidad.obras.ObrasPrivadas4App;

import municipalidad.obras.domain.Archivo;
import municipalidad.obras.repository.ArchivoRepository;
import municipalidad.obras.service.ArchivoService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static municipalidad.obras.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArchivoResource REST controller.
 *
 * @see ArchivoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasPrivadas4App.class)
public class ArchivoResourceIntTest {

    private static final byte[] DEFAULT_ARCHIVO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHIVO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ARCHIVO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHIVO_CONTENT_TYPE = "image/png";

    @Autowired
    private ArchivoRepository archivoRepository;

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArchivoMockMvc;

    private Archivo archivo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArchivoResource archivoResource = new ArchivoResource(archivoService);
        this.restArchivoMockMvc = MockMvcBuilders.standaloneSetup(archivoResource)
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
    public static Archivo createEntity(EntityManager em) {
        Archivo archivo = new Archivo()
            .archivo(DEFAULT_ARCHIVO)
            .archivoContentType(DEFAULT_ARCHIVO_CONTENT_TYPE);
        return archivo;
    }

    @Before
    public void initTest() {
        archivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createArchivo() throws Exception {
//        int databaseSizeBeforeCreate = archivoRepository.findAll().size();
//
//        // Create the Archivo
//        restArchivoMockMvc.perform(post("/api/archivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(archivo)))
//            .andExpect(status().isCreated());
//
//        // Validate the Archivo in the database
//        List<Archivo> archivoList = archivoRepository.findAll();
//        assertThat(archivoList).hasSize(databaseSizeBeforeCreate + 1);
//        Archivo testArchivo = archivoList.get(archivoList.size() - 1);
//        assertThat(testArchivo.getArchivo()).isEqualTo(DEFAULT_ARCHIVO);
//        assertThat(testArchivo.getArchivoContentType()).isEqualTo(DEFAULT_ARCHIVO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createArchivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = archivoRepository.findAll().size();

        // Create the Archivo with an existing ID
        archivo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArchivoMockMvc.perform(post("/api/archivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(archivo)))
            .andExpect(status().isBadRequest());

        // Validate the Archivo in the database
        List<Archivo> archivoList = archivoRepository.findAll();
        assertThat(archivoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArchivos() throws Exception {
        // Initialize the database
        archivoRepository.saveAndFlush(archivo);

        // Get all the archivoList
        restArchivoMockMvc.perform(get("/api/archivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].archivoContentType").value(hasItem(DEFAULT_ARCHIVO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archivo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHIVO))));
    }

    @Test
    @Transactional
    public void getArchivo() throws Exception {
        // Initialize the database
        archivoRepository.saveAndFlush(archivo);

        // Get the archivo
        restArchivoMockMvc.perform(get("/api/archivos/{id}", archivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(archivo.getId().intValue()))
            .andExpect(jsonPath("$.archivoContentType").value(DEFAULT_ARCHIVO_CONTENT_TYPE))
            .andExpect(jsonPath("$.archivo").value(Base64Utils.encodeToString(DEFAULT_ARCHIVO)));
    }

    @Test
    @Transactional
    public void getNonExistingArchivo() throws Exception {
        // Get the archivo
        restArchivoMockMvc.perform(get("/api/archivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArchivo() throws Exception {
        // Initialize the database
//        archivoService.save(archivo);
//
//        int databaseSizeBeforeUpdate = archivoRepository.findAll().size();
//
//        // Update the archivo
//        Archivo updatedArchivo = archivoRepository.findOne(archivo.getId());
//        // Disconnect from session so that the updates on updatedArchivo are not directly saved in db
//        em.detach(updatedArchivo);
//        updatedArchivo
//            .archivo(UPDATED_ARCHIVO)
//            .archivoContentType(UPDATED_ARCHIVO_CONTENT_TYPE);
//
//        restArchivoMockMvc.perform(put("/api/archivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedArchivo)))
//            .andExpect(status().isOk());
//
//        // Validate the Archivo in the database
//        List<Archivo> archivoList = archivoRepository.findAll();
//        assertThat(archivoList).hasSize(databaseSizeBeforeUpdate);
//        Archivo testArchivo = archivoList.get(archivoList.size() - 1);
//        assertThat(testArchivo.getArchivo()).isEqualTo(UPDATED_ARCHIVO);
//        assertThat(testArchivo.getArchivoContentType()).isEqualTo(UPDATED_ARCHIVO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingArchivo() throws Exception {
//        int databaseSizeBeforeUpdate = archivoRepository.findAll().size();
//
//        // Create the Archivo
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restArchivoMockMvc.perform(put("/api/archivos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(archivo)))
//            .andExpect(status().isCreated());
//
//        // Validate the Archivo in the database
//        List<Archivo> archivoList = archivoRepository.findAll();
//        assertThat(archivoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArchivo() throws Exception {
        // Initialize the database
//        archivoService.save(archivo);
//
//        int databaseSizeBeforeDelete = archivoRepository.findAll().size();
//
//        // Get the archivo
//        restArchivoMockMvc.perform(delete("/api/archivos/{id}", archivo.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<Archivo> archivoList = archivoRepository.findAll();
//        assertThat(archivoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Archivo.class);
        Archivo archivo1 = new Archivo();
        archivo1.setId(1L);
        Archivo archivo2 = new Archivo();
        archivo2.setId(archivo1.getId());
        assertThat(archivo1).isEqualTo(archivo2);
        archivo2.setId(2L);
        assertThat(archivo1).isNotEqualTo(archivo2);
        archivo1.setId(null);
        assertThat(archivo1).isNotEqualTo(archivo2);
    }
}
