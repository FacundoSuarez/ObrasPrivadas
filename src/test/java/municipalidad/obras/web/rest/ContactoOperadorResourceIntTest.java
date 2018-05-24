package municipalidad.obras.web.rest;

import municipalidad.obras.ObrasPrivadas4App;

import municipalidad.obras.domain.ContactoOperador;
import municipalidad.obras.repository.ContactoOperadorRepository;
import municipalidad.obras.service.ContactoOperadorService;
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
 * Test class for the ContactoOperadorResource REST controller.
 *
 * @see ContactoOperadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObrasPrivadas4App.class)
public class ContactoOperadorResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private ContactoOperadorRepository contactoOperadorRepository;

    @Autowired
    private ContactoOperadorService contactoOperadorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactoOperadorMockMvc;

    private ContactoOperador contactoOperador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactoOperadorResource contactoOperadorResource = new ContactoOperadorResource(contactoOperadorService);
        this.restContactoOperadorMockMvc = MockMvcBuilders.standaloneSetup(contactoOperadorResource)
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
    public static ContactoOperador createEntity(EntityManager em) {
        ContactoOperador contactoOperador = new ContactoOperador()
            .descripcion(DEFAULT_DESCRIPCION);
        return contactoOperador;
    }

    @Before
    public void initTest() {
        contactoOperador = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactoOperador() throws Exception {
        int databaseSizeBeforeCreate = contactoOperadorRepository.findAll().size();

        // Create the ContactoOperador
        restContactoOperadorMockMvc.perform(post("/api/contacto-operadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoOperador)))
            .andExpect(status().isCreated());

        // Validate the ContactoOperador in the database
        List<ContactoOperador> contactoOperadorList = contactoOperadorRepository.findAll();
        assertThat(contactoOperadorList).hasSize(databaseSizeBeforeCreate + 1);
        ContactoOperador testContactoOperador = contactoOperadorList.get(contactoOperadorList.size() - 1);
        assertThat(testContactoOperador.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createContactoOperadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactoOperadorRepository.findAll().size();

        // Create the ContactoOperador with an existing ID
        contactoOperador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoOperadorMockMvc.perform(post("/api/contacto-operadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoOperador)))
            .andExpect(status().isBadRequest());

        // Validate the ContactoOperador in the database
        List<ContactoOperador> contactoOperadorList = contactoOperadorRepository.findAll();
        assertThat(contactoOperadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContactoOperadors() throws Exception {
        // Initialize the database
        contactoOperadorRepository.saveAndFlush(contactoOperador);

        // Get all the contactoOperadorList
        restContactoOperadorMockMvc.perform(get("/api/contacto-operadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactoOperador.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getContactoOperador() throws Exception {
        // Initialize the database
        contactoOperadorRepository.saveAndFlush(contactoOperador);

        // Get the contactoOperador
        restContactoOperadorMockMvc.perform(get("/api/contacto-operadors/{id}", contactoOperador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactoOperador.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactoOperador() throws Exception {
        // Get the contactoOperador
        restContactoOperadorMockMvc.perform(get("/api/contacto-operadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactoOperador() throws Exception {
        // Initialize the database
        contactoOperadorService.save(contactoOperador);

        int databaseSizeBeforeUpdate = contactoOperadorRepository.findAll().size();

        // Update the contactoOperador
        ContactoOperador updatedContactoOperador = contactoOperadorRepository.findOne(contactoOperador.getId());
        // Disconnect from session so that the updates on updatedContactoOperador are not directly saved in db
        em.detach(updatedContactoOperador);
        updatedContactoOperador
            .descripcion(UPDATED_DESCRIPCION);

        restContactoOperadorMockMvc.perform(put("/api/contacto-operadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactoOperador)))
            .andExpect(status().isOk());

        // Validate the ContactoOperador in the database
        List<ContactoOperador> contactoOperadorList = contactoOperadorRepository.findAll();
        assertThat(contactoOperadorList).hasSize(databaseSizeBeforeUpdate);
        ContactoOperador testContactoOperador = contactoOperadorList.get(contactoOperadorList.size() - 1);
        assertThat(testContactoOperador.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingContactoOperador() throws Exception {
        int databaseSizeBeforeUpdate = contactoOperadorRepository.findAll().size();

        // Create the ContactoOperador

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContactoOperadorMockMvc.perform(put("/api/contacto-operadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoOperador)))
            .andExpect(status().isCreated());

        // Validate the ContactoOperador in the database
        List<ContactoOperador> contactoOperadorList = contactoOperadorRepository.findAll();
        assertThat(contactoOperadorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContactoOperador() throws Exception {
        // Initialize the database
        contactoOperadorService.save(contactoOperador);

        int databaseSizeBeforeDelete = contactoOperadorRepository.findAll().size();

        // Get the contactoOperador
        restContactoOperadorMockMvc.perform(delete("/api/contacto-operadors/{id}", contactoOperador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactoOperador> contactoOperadorList = contactoOperadorRepository.findAll();
        assertThat(contactoOperadorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoOperador.class);
        ContactoOperador contactoOperador1 = new ContactoOperador();
        contactoOperador1.setId(1L);
        ContactoOperador contactoOperador2 = new ContactoOperador();
        contactoOperador2.setId(contactoOperador1.getId());
        assertThat(contactoOperador1).isEqualTo(contactoOperador2);
        contactoOperador2.setId(2L);
        assertThat(contactoOperador1).isNotEqualTo(contactoOperador2);
        contactoOperador1.setId(null);
        assertThat(contactoOperador1).isNotEqualTo(contactoOperador2);
    }
}
