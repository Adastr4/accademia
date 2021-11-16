package net.accademia.demone.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import net.accademia.demone.IntegrationTest;
import net.accademia.demone.domain.Error;
import net.accademia.demone.repository.ErrorRepository;
import net.accademia.demone.repository.search.ErrorSearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ErrorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ErrorResourceIT {

    private static final Integer DEFAULT_ERRORID = 1;
    private static final Integer UPDATED_ERRORID = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/errors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/errors";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ErrorRepository errorRepository;

    /**
     * This repository is mocked in the net.accademia.demone.repository.search test package.
     *
     * @see net.accademia.demone.repository.search.ErrorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ErrorSearchRepository mockErrorSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restErrorMockMvc;

    private Error error;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Error createEntity(EntityManager em) {
        Error error = new Error().errorid(DEFAULT_ERRORID).description(DEFAULT_DESCRIPTION).data(DEFAULT_DATA);
        return error;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Error createUpdatedEntity(EntityManager em) {
        Error error = new Error().errorid(UPDATED_ERRORID).description(UPDATED_DESCRIPTION).data(UPDATED_DATA);
        return error;
    }

    @BeforeEach
    public void initTest() {
        error = createEntity(em);
    }

    @Test
    @Transactional
    void createError() throws Exception {
        int databaseSizeBeforeCreate = errorRepository.findAll().size();
        // Create the Error
        restErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(error)))
            .andExpect(status().isCreated());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeCreate + 1);
        Error testError = errorList.get(errorList.size() - 1);
        assertThat(testError.getErrorid()).isEqualTo(DEFAULT_ERRORID);
        assertThat(testError.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testError.getData()).isEqualTo(DEFAULT_DATA);

        // Validate the Error in Elasticsearch
        verify(mockErrorSearchRepository, times(1)).save(testError);
    }

    @Test
    @Transactional
    void createErrorWithExistingId() throws Exception {
        // Create the Error with an existing ID
        error.setId(1L);

        int databaseSizeBeforeCreate = errorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restErrorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(error)))
            .andExpect(status().isBadRequest());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Error in Elasticsearch
        verify(mockErrorSearchRepository, times(0)).save(error);
    }

    @Test
    @Transactional
    void getAllErrors() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        // Get all the errorList
        restErrorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(error.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorid").value(hasItem(DEFAULT_ERRORID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }

    @Test
    @Transactional
    void getError() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        // Get the error
        restErrorMockMvc
            .perform(get(ENTITY_API_URL_ID, error.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(error.getId().intValue()))
            .andExpect(jsonPath("$.errorid").value(DEFAULT_ERRORID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingError() throws Exception {
        // Get the error
        restErrorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewError() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        int databaseSizeBeforeUpdate = errorRepository.findAll().size();

        // Update the error
        Error updatedError = errorRepository.findById(error.getId()).get();
        // Disconnect from session so that the updates on updatedError are not directly saved in db
        em.detach(updatedError);
        updatedError.errorid(UPDATED_ERRORID).description(UPDATED_DESCRIPTION).data(UPDATED_DATA);

        restErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedError.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedError))
            )
            .andExpect(status().isOk());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);
        Error testError = errorList.get(errorList.size() - 1);
        assertThat(testError.getErrorid()).isEqualTo(UPDATED_ERRORID);
        assertThat(testError.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testError.getData()).isEqualTo(UPDATED_DATA);

        // Validate the Error in Elasticsearch
        verify(mockErrorSearchRepository).save(testError);
    }

    @Test
    @Transactional
    void putNonExistingError() throws Exception {
        int databaseSizeBeforeUpdate = errorRepository.findAll().size();
        error.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, error.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(error))
            )
            .andExpect(status().isBadRequest());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Error in Elasticsearch
        verify(mockErrorSearchRepository, times(0)).save(error);
    }

    @Test
    @Transactional
    void putWithIdMismatchError() throws Exception {
        int databaseSizeBeforeUpdate = errorRepository.findAll().size();
        error.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(error))
            )
            .andExpect(status().isBadRequest());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Error in Elasticsearch
        verify(mockErrorSearchRepository, times(0)).save(error);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamError() throws Exception {
        int databaseSizeBeforeUpdate = errorRepository.findAll().size();
        error.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(error)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Error in Elasticsearch
        verify(mockErrorSearchRepository, times(0)).save(error);
    }

    @Test
    @Transactional
    void partialUpdateErrorWithPatch() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        int databaseSizeBeforeUpdate = errorRepository.findAll().size();

        // Update the error using partial update
        Error partialUpdatedError = new Error();
        partialUpdatedError.setId(error.getId());

        partialUpdatedError.errorid(UPDATED_ERRORID).description(UPDATED_DESCRIPTION);

        restErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedError.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedError))
            )
            .andExpect(status().isOk());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);
        Error testError = errorList.get(errorList.size() - 1);
        assertThat(testError.getErrorid()).isEqualTo(UPDATED_ERRORID);
        assertThat(testError.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testError.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    void fullUpdateErrorWithPatch() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        int databaseSizeBeforeUpdate = errorRepository.findAll().size();

        // Update the error using partial update
        Error partialUpdatedError = new Error();
        partialUpdatedError.setId(error.getId());

        partialUpdatedError.errorid(UPDATED_ERRORID).description(UPDATED_DESCRIPTION).data(UPDATED_DATA);

        restErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedError.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedError))
            )
            .andExpect(status().isOk());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);
        Error testError = errorList.get(errorList.size() - 1);
        assertThat(testError.getErrorid()).isEqualTo(UPDATED_ERRORID);
        assertThat(testError.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testError.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingError() throws Exception {
        int databaseSizeBeforeUpdate = errorRepository.findAll().size();
        error.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, error.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(error))
            )
            .andExpect(status().isBadRequest());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Error in Elasticsearch
        verify(mockErrorSearchRepository, times(0)).save(error);
    }

    @Test
    @Transactional
    void patchWithIdMismatchError() throws Exception {
        int databaseSizeBeforeUpdate = errorRepository.findAll().size();
        error.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(error))
            )
            .andExpect(status().isBadRequest());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Error in Elasticsearch
        verify(mockErrorSearchRepository, times(0)).save(error);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamError() throws Exception {
        int databaseSizeBeforeUpdate = errorRepository.findAll().size();
        error.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(error)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Error in Elasticsearch
        verify(mockErrorSearchRepository, times(0)).save(error);
    }

    @Test
    @Transactional
    void deleteError() throws Exception {
        // Initialize the database
        errorRepository.saveAndFlush(error);

        int databaseSizeBeforeDelete = errorRepository.findAll().size();

        // Delete the error
        restErrorMockMvc
            .perform(delete(ENTITY_API_URL_ID, error.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Error> errorList = errorRepository.findAll();
        assertThat(errorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Error in Elasticsearch
        verify(mockErrorSearchRepository, times(1)).deleteById(error.getId());
    }

    @Test
    @Transactional
    void searchError() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        errorRepository.saveAndFlush(error);
        when(mockErrorSearchRepository.search("id:" + error.getId())).thenReturn(Stream.of(error));

        // Search the error
        restErrorMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + error.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(error.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorid").value(hasItem(DEFAULT_ERRORID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
}
