package com.radiology.health.care.web.rest;

import com.radiology.health.care.domain.ShiftTimings;
import com.radiology.health.care.repository.OfficeTimingsRepository;
import com.radiology.health.care.service.OfficeTimingsQueryService;
import com.radiology.health.care.service.OfficeTimingsService;
import com.radiology.health.care.service.criteria.OfficeTimingsCriteria;
import com.radiology.health.care.service.dto.DefaultOfficeTimingsDTO;
import com.radiology.health.care.service.dto.OfficeTimesDTO;
import com.radiology.health.care.service.dto.OfficeTimingsDTO;
import com.radiology.health.care.web.rest.errors.BadRequestAlertException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.xml.crypto.Data;
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
 * REST controller for managing {@link com.radiology.health.care.domain.OfficeTimings}.
 */
@RestController
@RequestMapping("/api/office-timings")
public class OfficeTimingsResource {

    private final Logger log = LoggerFactory.getLogger(OfficeTimingsResource.class);

    private static final String ENTITY_NAME = "officeTimings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficeTimingsService officeTimingsService;

    private final OfficeTimingsRepository officeTimingsRepository;

    private final OfficeTimingsQueryService officeTimingsQueryService;

    public OfficeTimingsResource(
        OfficeTimingsService officeTimingsService,
        OfficeTimingsRepository officeTimingsRepository,
        OfficeTimingsQueryService officeTimingsQueryService
    ) {
        this.officeTimingsService = officeTimingsService;
        this.officeTimingsRepository = officeTimingsRepository;
        this.officeTimingsQueryService = officeTimingsQueryService;
    }

    /**
     * {@code POST  /office-timings} : Create a new officeTimings.
     *
     * @param officeTimesDTO the officeTimingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officeTimingsDTO, or with status {@code 400 (Bad Request)} if the officeTimings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("")
    public ResponseEntity<String> createOfficeTimings(@Valid @RequestBody OfficeTimesDTO officeTimesDTO) throws URISyntaxException {
        log.debug("REST request to save OfficeTimings : {}", officeTimesDTO);
        if (officeTimesDTO.getId() != null) {
            throw new BadRequestAlertException("A new officeTimings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<ShiftTimings> shiftTimings = officeTimesDTO.getShiftTimes();
        boolean validShiftTimes = true;
        for (int i = 0; i < shiftTimings.size(); i++) {
            String shiftStartTime = shiftTimings.get(i).getStartTime();
            String shiftEndTime = shiftTimings.get(i).getEndTime();
            try {
                LocalTime shiftStartTiming = LocalTime.parse(shiftStartTime);
                LocalTime shiftEndTiming = LocalTime.parse(shiftEndTime);
            } catch (Exception e) {
                validShiftTimes = false;
                return ResponseEntity.internalServerError().body("Invalid Time Format. Give valid times.");
            }
        }
        OfficeTimingsDTO officeTimingsDTO = new OfficeTimingsDTO();
        OfficeTimingsDTO result = new OfficeTimingsDTO();
        if (officeTimingsRepository.findByDate(officeTimesDTO.getDate()).isEmpty() && validShiftTimes) {
            for (int i = 0; i < shiftTimings.size(); i++) {
                officeTimingsDTO.setDate(officeTimesDTO.getDate());
                officeTimingsDTO.setShiftStart(shiftTimings.get(i).getStartTime());
                officeTimingsDTO.setShiftEnd(shiftTimings.get(i).getEndTime());
                officeTimingsDTO.setDefaultTimings(false);
                result = officeTimingsService.save(officeTimingsDTO);
            }

            return ResponseEntity
                .created(new URI("/api/office-timings/"))
                .headers(
                    HeaderUtil.createEntityCreationAlert(
                        applicationName,
                        false,
                        ENTITY_NAME,
                        "Office Timings got updated for the " + officeTimesDTO.getDate()
                    )
                )
                .body("Office Timings got updated for the " + officeTimesDTO.getDate());
        } else {
            return ResponseEntity
                .internalServerError()
                .body("the given date is might be invalid or already exist with shift times. You can update it.");
        }
    }

    /**
     * {@code POST  /office-timings} : Create a new officeTimings.
     *
     * @param defaultOfficeTimingsDTO the officeTimingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officeTimingsDTO, or with status {@code 400 (Bad Request)} if the officeTimings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Transactional
    @PostMapping("/default-office-timings")
    public ResponseEntity<String> createDefaultOfficeTimings(@Valid @RequestBody DefaultOfficeTimingsDTO defaultOfficeTimingsDTO)
        throws URISyntaxException {
        log.debug("REST request to save DefaultOfficeTimings: {}", defaultOfficeTimingsDTO);
        if (defaultOfficeTimingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new officeTimings cannot already have an ID", ENTITY_NAME, "idexists");
        }

        List<ShiftTimings> shiftTimings = defaultOfficeTimingsDTO.getShiftTimes();

        OfficeTimingsDTO officeTimingsDTO = new OfficeTimingsDTO();
        boolean validShiftTimes = true;
        for (int i = 0; i < shiftTimings.size(); i++) {
            String shiftStartTime = shiftTimings.get(i).getStartTime();
            String shiftEndTime = shiftTimings.get(i).getEndTime();
            try {
                LocalTime shiftStartTiming = LocalTime.parse(shiftStartTime);
                LocalTime shiftEndTiming = LocalTime.parse(shiftEndTime);
            } catch (Exception e) {
                validShiftTimes = false;
                return ResponseEntity.internalServerError().body("Invalid Time Format. Give valid times.");
            }
        }
        if (validShiftTimes) {
            officeTimingsRepository.deleteByDateIsNull();
        }

        OfficeTimingsDTO result = new OfficeTimingsDTO();
        for (int i = 0; i < shiftTimings.size(); i++) {
            officeTimingsDTO.setDate(null);
            officeTimingsDTO.setShiftStart(shiftTimings.get(i).getStartTime());
            officeTimingsDTO.setShiftEnd(shiftTimings.get(i).getEndTime());
            officeTimingsDTO.setDefaultTimings(true);
            result = officeTimingsService.save(officeTimingsDTO);
        }

        return ResponseEntity
            .created(new URI("/api/office-timings/"))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, "Default Office Timings got updated."))
            .body("Default Office Timings got updated.");
    }

    /**
     * {@code PUT  /office-timings/:id} : Updates an existing officeTimings.
     *
     * @param id the id of the officeTimingsDTO to save.
     * @param officeTimingsDTO the officeTimingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officeTimingsDTO,
     * or with status {@code 400 (Bad Request)} if the officeTimingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officeTimingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OfficeTimingsDTO> updateOfficeTimings(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OfficeTimingsDTO officeTimingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OfficeTimings : {}, {}", id, officeTimingsDTO);
        if (officeTimingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, officeTimingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officeTimingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OfficeTimingsDTO result = officeTimingsService.update(officeTimingsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, officeTimingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /office-timings/:id} : Partial updates given fields of an existing officeTimings, field will ignore if it is null
     *
     * @param id the id of the officeTimingsDTO to save.
     * @param officeTimingsDTO the officeTimingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officeTimingsDTO,
     * or with status {@code 400 (Bad Request)} if the officeTimingsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the officeTimingsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the officeTimingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OfficeTimingsDTO> partialUpdateOfficeTimings(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OfficeTimingsDTO officeTimingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OfficeTimings partially : {}, {}", id, officeTimingsDTO);
        if (officeTimingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, officeTimingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!officeTimingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OfficeTimingsDTO> result = officeTimingsService.partialUpdate(officeTimingsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, officeTimingsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /office-timings} : get all the officeTimings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officeTimings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OfficeTimingsDTO>> getAllOfficeTimings(
        OfficeTimingsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OfficeTimings by criteria: {}", criteria);

        Page<OfficeTimingsDTO> page = officeTimingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /office-timings/count} : count all the officeTimings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countOfficeTimings(OfficeTimingsCriteria criteria) {
        log.debug("REST request to count OfficeTimings by criteria: {}", criteria);
        return ResponseEntity.ok().body(officeTimingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /office-timings/:id} : get the "id" officeTimings.
     *
     * @param id the id of the officeTimingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officeTimingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OfficeTimingsDTO> getOfficeTimings(@PathVariable("id") Long id) {
        log.debug("REST request to get OfficeTimings : {}", id);
        Optional<OfficeTimingsDTO> officeTimingsDTO = officeTimingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(officeTimingsDTO);
    }

    /**
     * {@code DELETE  /office-timings/:id} : delete the "id" officeTimings.
     *
     * @param id the id of the officeTimingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOfficeTimings(@PathVariable("id") Long id) {
        log.debug("REST request to delete OfficeTimings : {}", id);
        officeTimingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
