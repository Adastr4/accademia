package net.accademia.demone.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.accademia.demone.domain.Error;
import net.accademia.demone.repository.ErrorRepository;
import net.accademia.demone.repository.search.ErrorSearchRepository;
import net.accademia.demone.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link net.accademia.demone.domain.Error}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ErrorResource {

    private final Logger log = LoggerFactory.getLogger(ErrorResource.class);

    private static final String ENTITY_NAME = "error";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ErrorRepository errorRepository;

    private final ErrorSearchRepository errorSearchRepository;

    public ErrorResource(ErrorRepository errorRepository, ErrorSearchRepository errorSearchRepository) {
        this.errorRepository = errorRepository;
        this.errorSearchRepository = errorSearchRepository;
    }

    /**
     * {@code POST  /errors} : Create a new error.
     *
     * @param error the error to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new error, or with status {@code 400 (Bad Request)} if the error has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/errors")
    public ResponseEntity<Error> createError(@RequestBody Error error) throws URISyntaxException {
        log.debug("REST request to save Error : {}", error);
        if (error.getId() != null) {
            throw new BadRequestAlertException("A new error cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Error result = errorRepository.save(error);
        errorSearchRepository.save(result);
        return ResponseEntity
            .created(new URI("/api/errors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /errors/:id} : Updates an existing error.
     *
     * @param id the id of the error to save.
     * @param error the error to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated error,
     * or with status {@code 400 (Bad Request)} if the error is not valid,
     * or with status {@code 500 (Internal Server Error)} if the error couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/errors/{id}")
    public ResponseEntity<Error> updateError(@PathVariable(value = "id", required = false) final Long id, @RequestBody Error error)
        throws URISyntaxException {
        log.debug("REST request to update Error : {}, {}", id, error);
        if (error.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, error.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!errorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Error result = errorRepository.save(error);
        errorSearchRepository.save(result);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, error.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /errors/:id} : Partial updates given fields of an existing error, field will ignore if it is null
     *
     * @param id the id of the error to save.
     * @param error the error to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated error,
     * or with status {@code 400 (Bad Request)} if the error is not valid,
     * or with status {@code 404 (Not Found)} if the error is not found,
     * or with status {@code 500 (Internal Server Error)} if the error couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/errors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Error> partialUpdateError(@PathVariable(value = "id", required = false) final Long id, @RequestBody Error error)
        throws URISyntaxException {
        log.debug("REST request to partial update Error partially : {}, {}", id, error);
        if (error.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, error.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!errorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Error> result = errorRepository
            .findById(error.getId())
            .map(existingError -> {
                if (error.getErrorid() != null) {
                    existingError.setErrorid(error.getErrorid());
                }
                if (error.getDescription() != null) {
                    existingError.setDescription(error.getDescription());
                }
                if (error.getData() != null) {
                    existingError.setData(error.getData());
                }

                return existingError;
            })
            .map(errorRepository::save)
            .map(savedError -> {
                errorSearchRepository.save(savedError);

                return savedError;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, error.getId().toString())
        );
    }

    /**
     * {@code GET  /errors} : get all the errors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of errors in body.
     */
    @GetMapping("/errors")
    public List<Error> getAllErrors() {
        log.debug("REST request to get all Errors");
        return errorRepository.findAll();
    }

    /**
     * {@code GET  /errors/:id} : get the "id" error.
     *
     * @param id the id of the error to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the error, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/errors/{id}")
    public ResponseEntity<Error> getError(@PathVariable Long id) {
        log.debug("REST request to get Error : {}", id);
        Optional<Error> error = errorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(error);
    }

    /**
     * {@code DELETE  /errors/:id} : delete the "id" error.
     *
     * @param id the id of the error to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/errors/{id}")
    public ResponseEntity<Void> deleteError(@PathVariable Long id) {
        log.debug("REST request to delete Error : {}", id);
        errorRepository.deleteById(id);
        errorSearchRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/errors?query=:query} : search for the error corresponding
     * to the query.
     *
     * @param query the query of the error search.
     * @return the result of the search.
     */
    @GetMapping("/_search/errors")
    public List<Error> searchErrors(@RequestParam String query) {
        log.debug("REST request to search Errors for query {}", query);
        return StreamSupport.stream(errorSearchRepository.search(query).spliterator(), false).collect(Collectors.toList());
    }
}
