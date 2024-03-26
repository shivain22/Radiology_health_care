package com.radiology.health.care.web.rest;

import com.radiology.health.care.repository.RankRepository;
import com.radiology.health.care.service.RankQueryService;
import com.radiology.health.care.service.RankService;
import com.radiology.health.care.service.criteria.RankCriteria;
import com.radiology.health.care.service.dto.RankDTO;
import com.radiology.health.care.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.radiology.health.care.domain.Rank}.
 */
@RestController
@RequestMapping("/api/ranks")
public class RankResource {

    private final Logger log = LoggerFactory.getLogger(RankResource.class);

    private static final String ENTITY_NAME = "rank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RankService rankService;

    private final RankRepository rankRepository;

    private final RankQueryService rankQueryService;

    public RankResource(RankService rankService, RankRepository rankRepository, RankQueryService rankQueryService) {
        this.rankService = rankService;
        this.rankRepository = rankRepository;
        this.rankQueryService = rankQueryService;
    }

    /**
     * {@code POST  /ranks} : Create a new rank.
     *
     * @param rankDTO the rankDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rankDTO, or with status {@code 400 (Bad Request)} if the rank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RankDTO> createRank(@Valid @RequestBody RankDTO rankDTO) throws URISyntaxException {
        log.debug("REST request to save Rank : {}", rankDTO);
        if (rankDTO.getId() != null) {
            throw new BadRequestAlertException("A new rank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RankDTO result = rankService.save(rankDTO);
        return ResponseEntity
            .created(new URI("/api/ranks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ranks/:id} : Updates an existing rank.
     *
     * @param id the id of the rankDTO to save.
     * @param rankDTO the rankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rankDTO,
     * or with status {@code 400 (Bad Request)} if the rankDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RankDTO> updateRank(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RankDTO rankDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Rank : {}, {}", id, rankDTO);
        if (rankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rankDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RankDTO result = rankService.update(rankDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rankDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ranks/:id} : Partial updates given fields of an existing rank, field will ignore if it is null
     *
     * @param id the id of the rankDTO to save.
     * @param rankDTO the rankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rankDTO,
     * or with status {@code 400 (Bad Request)} if the rankDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rankDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RankDTO> partialUpdateRank(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RankDTO rankDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rank partially : {}, {}", id, rankDTO);
        if (rankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rankDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RankDTO> result = rankService.partialUpdate(rankDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rankDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ranks} : get all the ranks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ranks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RankDTO>> getAllRanks(
        RankCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Ranks by criteria: {}", criteria);

        Page<RankDTO> page = rankQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ranks/count} : count all the ranks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countRanks(RankCriteria criteria) {
        log.debug("REST request to count Ranks by criteria: {}", criteria);
        return ResponseEntity.ok().body(rankQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ranks/:id} : get the "id" rank.
     *
     * @param id the id of the rankDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rankDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RankDTO> getRank(@PathVariable("id") Long id) {
        log.debug("REST request to get Rank : {}", id);
        Optional<RankDTO> rankDTO = rankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rankDTO);
    }

    /**
     * {@code DELETE  /ranks/:id} : delete the "id" rank.
     *
     * @param id the id of the rankDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRank(@PathVariable("id") Long id) {
        log.debug("REST request to delete Rank : {}", id);
        rankService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
