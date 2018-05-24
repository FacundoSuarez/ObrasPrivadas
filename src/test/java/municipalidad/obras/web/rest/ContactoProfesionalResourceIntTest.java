package municipalidad.obras.web.rest;

import municipalidad.obras.ObrasPrivadas4App;

import municipalidad.obras.domain.ContactoProfesional;
import municipalidad.obras.repository.ContactoProfesionalRepository;
import municipalidad.obras.service.ContactoProfesionalService;
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
 * Test class for the ContactoProfesionalResource REST controller.
 *
 * @see ContactoProfesionalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasPrivadas4App.class)
public class ContactoProfesionalResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private ContactoProfesionalRepository contactoProfesionalRepository;

    @Autowired
    private ContactoProfesionalService contactoProfesionalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactoProfesionalMockMvc;

    private ContactoProfesional contactoProfesional;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactoProfesionalResource contactoProfesionalResource = new ContactoProfesionalResource(contactoProfesionalService);
        this.restContactoProfesionalMockMvc = MockMvcBuilders.standaloneSetup(contactoProfesionalResource)
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
    public static ContactoProfesional createEntity(EntityManager em) {
        ContactoProfesional contactoProfesional = new ContactoProfesional()
            .descripcion(DEFAULT_DESCRIPCION);
        return contactoProfesional;
    }

    @Before
    public void initTest() {
        contactoProfesional = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactoProfesional() throws Exception {
        int databaseSizeBeforeCreate = contactoProfesionalRepository.findAll().size();

        // Create the ContactoProfesional
        restContactoProfesionalMockMvc.perform(post("/api/contacto-profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoProfesional)))
            .andExpect(status().isCreated());

        // Validate the ContactoProfesional in the database
        List<ContactoProfesional> contactoProfesionalList = contactoProfesionalRepository.findAll();
        assertThat(contactoProfesionalList).hasSize(databaseSizeBeforeCreate + 1);
        ContactoProfesional testContactoProfesional = contactoProfesionalList.get(contactoProfesionalList.size() - 1);
        assertThat(testContactoProfesional.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createContactoProfesionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactoProfesionalRepository.findAll().size();

        // Create the ContactoProfesional with an existing ID
        contactoProfesional.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoProfesionalMockMvc.perform(post("/api/contacto-profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoProfesional)))
            .andExpect(status().isBadRequest());

        // Validate the ContactoProfesional in the database
        List<ContactoProfesional> contactoProfesionalList = contactoProfesionalRepository.findAll();
        assertThat(contactoProfesionalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContactoProfesionals() throws Exception {
        // Initialize the database
        contactoProfesionalRepository.saveAndFlush(contactoProfesional);

        // Get all the contactoProfesionalList
        restContactoProfesionalMockMvc.perform(get("/api/contacto-profesionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactoProfesional.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getContactoProfesional() throws Exception {
        // Initialize the database
        contactoProfesionalRepository.saveAndFlush(contactoProfesional);

        // Get the contactoProfesional
        restContactoProfesionalMockMvc.perform(get("/api/contacto-profesionals/{id}", contactoProfesional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactoProfesional.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactoProfesional() throws Exception {
        // Get the contactoProfesional
        restContactoProfesionalMockMvc.perform(get("/api/contacto-profesionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactoProfesional() throws Exception {
        // Initialize the database
        contactoProfesionalService.save(contactoProfesional);

        int databaseSizeBeforeUpdate = contactoProfesionalRepository.findAll().size();

        // Update the contactoProfesional
        ContactoProfesional updatedContactoProfesional = contactoProfesionalRepository.findOne(contactoProfesional.getId());
        // Disconnect from session so that the updates on updatedContactoProfesional are not directly saved in db
        em.detach(updatedContactoProfesional);
        updatedContactoProfesional
            .descripcion(UPDATED_DESCRIPCION);

        restContactoProfesionalMockMvc.perform(put("/api/contacto-profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactoProfesional)))
            .andExpect(status().isOk());

        // Validate the ContactoProfesional in the database
        List<ContactoProfesional> contactoProfesionalList = contactoProfesionalRepository.findAll();
        assertThat(contactoProfesionalList).hasSize(databaseSizeBeforeUpdate);
        ContactoProfesional testContactoProfesional = contactoProfesionalList.get(contactoProfesionalList.size() - 1);
        assertThat(testContactoProfesional.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingContactoProfesional() throws Exception {
        int databaseSizeBeforeUpdate = contactoProfesionalRepository.findAll().size();

        // Create the ContactoProfesional

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContactoProfesionalMockMvc.perform(put("/api/contacto-profesionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoProfesional)))
            .andExpect(status().isCreated());

        // Validate the ContactoProfesional in the database
        List<ContactoProfesional> contactoProfesionalList = contactoProfesionalRepository.findAll();
        assertThat(contactoProfesionalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContactoProfesional() throws Exception {
        // Initialize the database
        contactoProfesionalService.save(contactoProfesional);

        int databaseSizeBeforeDelete = contactoProfesionalRepository.findAll().size();

        // Get the contactoProfesional
        restContactoProfesionalMockMvc.perform(delete("/api/contacto-profesionals/{id}", contactoProfesional.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactoProfesional> contactoProfesionalList = contactoProfesionalRepository.findAll();
        assertThat(contactoProfesionalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoProfesional.class);
        ContactoProfesional contactoProfesional1 = new ContactoProfesional();
        contactoProfesional1.setId(1L);
        ContactoProfesional contactoProfesional2 = new ContactoProfesional();
        contactoProfesional2.setId(contactoProfesional1.getId());
        assertThat(contactoProfesional1).isEqualTo(contactoProfesional2);
        contactoProfesional2.setId(2L);
        assertThat(contactoProfesional1).isNotEqualTo(contactoProfesional2);
        contactoProfesional1.setId(null);
        assertThat(contactoProfesional1).isNotEqualTo(contactoProfesional2);
    }
}
