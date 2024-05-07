package com.radiology.health.care.service.impl;

import com.radiology.health.care.domain.Equipment;
import com.radiology.health.care.repository.EquipmentRepository;
import com.radiology.health.care.service.EquipmentService;
import com.radiology.health.care.service.dto.EquipmentDTO;
import com.radiology.health.care.service.mapper.EquipmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.care.domain.Equipment}.
 */
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    private final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    private final EquipmentRepository equipmentRepository;

    private final EquipmentMapper equipmentMapper;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, EquipmentMapper equipmentMapper) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentMapper = equipmentMapper;
    }

    @Override
    public EquipmentDTO save(EquipmentDTO equipmentDTO) {
        log.debug("Request to save Equipment : {}", equipmentDTO);
        Equipment equipment = equipmentMapper.toEntity(equipmentDTO);
        equipment = equipmentRepository.save(equipment);
        return equipmentMapper.toDto(equipment);
    }

    @Override
    public EquipmentDTO update(EquipmentDTO equipmentDTO) {
        log.debug("Request to update Equipment : {}", equipmentDTO);
        Equipment equipment = equipmentMapper.toEntity(equipmentDTO);
        equipment = equipmentRepository.save(equipment);
        return equipmentMapper.toDto(equipment);
    }

    @Override
    public Optional<EquipmentDTO> partialUpdate(EquipmentDTO equipmentDTO) {
        log.debug("Request to partially update Equipment : {}", equipmentDTO);

        return equipmentRepository
            .findById(equipmentDTO.getId())
            .map(existingEquipment -> {
                equipmentMapper.partialUpdate(existingEquipment, equipmentDTO);

                return existingEquipment;
            })
            .map(equipmentRepository::save)
            .map(equipmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Equipment");
        return equipmentRepository.findAll(pageable).map(equipmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EquipmentDTO> findOne(Long id) {
        log.debug("Request to get Equipment : {}", id);
        return equipmentRepository.findById(id).map(equipmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipment : {}", id);
        equipmentRepository.deleteById(id);
    }
}
