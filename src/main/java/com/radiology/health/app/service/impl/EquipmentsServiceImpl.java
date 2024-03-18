package com.radiology.health.app.service.impl;

import com.radiology.health.app.domain.Equipments;
import com.radiology.health.app.repository.EquipmentsRepository;
import com.radiology.health.app.service.EquipmentsService;
import com.radiology.health.app.service.dto.EquipmentsDTO;
import com.radiology.health.app.service.mapper.EquipmentsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.app.domain.Equipments}.
 */
@Service
@Transactional
public class EquipmentsServiceImpl implements EquipmentsService {

    private final Logger log = LoggerFactory.getLogger(EquipmentsServiceImpl.class);

    private final EquipmentsRepository equipmentsRepository;

    private final EquipmentsMapper equipmentsMapper;

    public EquipmentsServiceImpl(EquipmentsRepository equipmentsRepository, EquipmentsMapper equipmentsMapper) {
        this.equipmentsRepository = equipmentsRepository;
        this.equipmentsMapper = equipmentsMapper;
    }

    @Override
    public EquipmentsDTO save(EquipmentsDTO equipmentsDTO) {
        log.debug("Request to save Equipments : {}", equipmentsDTO);
        Equipments equipments = equipmentsMapper.toEntity(equipmentsDTO);
        equipments = equipmentsRepository.save(equipments);
        return equipmentsMapper.toDto(equipments);
    }

    @Override
    public EquipmentsDTO update(EquipmentsDTO equipmentsDTO) {
        log.debug("Request to update Equipments : {}", equipmentsDTO);
        Equipments equipments = equipmentsMapper.toEntity(equipmentsDTO);
        equipments = equipmentsRepository.save(equipments);
        return equipmentsMapper.toDto(equipments);
    }

    @Override
    public Optional<EquipmentsDTO> partialUpdate(EquipmentsDTO equipmentsDTO) {
        log.debug("Request to partially update Equipments : {}", equipmentsDTO);

        return equipmentsRepository
            .findById(equipmentsDTO.getId())
            .map(existingEquipments -> {
                equipmentsMapper.partialUpdate(existingEquipments, equipmentsDTO);

                return existingEquipments;
            })
            .map(equipmentsRepository::save)
            .map(equipmentsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipmentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Equipments");
        return equipmentsRepository.findAll(pageable).map(equipmentsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EquipmentsDTO> findOne(Long id) {
        log.debug("Request to get Equipments : {}", id);
        return equipmentsRepository.findById(id).map(equipmentsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipments : {}", id);
        equipmentsRepository.deleteById(id);
    }
}
