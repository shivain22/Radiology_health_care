package com.radiology.health.app.service.impl;

import com.radiology.health.app.domain.Rooms;
import com.radiology.health.app.repository.RoomsRepository;
import com.radiology.health.app.service.RoomsService;
import com.radiology.health.app.service.dto.RoomsDTO;
import com.radiology.health.app.service.mapper.RoomsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.app.domain.Rooms}.
 */
@Service
@Transactional
public class RoomsServiceImpl implements RoomsService {

    private final Logger log = LoggerFactory.getLogger(RoomsServiceImpl.class);

    private final RoomsRepository roomsRepository;

    private final RoomsMapper roomsMapper;

    public RoomsServiceImpl(RoomsRepository roomsRepository, RoomsMapper roomsMapper) {
        this.roomsRepository = roomsRepository;
        this.roomsMapper = roomsMapper;
    }

    @Override
    public RoomsDTO save(RoomsDTO roomsDTO) {
        log.debug("Request to save Rooms : {}", roomsDTO);
        Rooms rooms = roomsMapper.toEntity(roomsDTO);
        rooms = roomsRepository.save(rooms);
        return roomsMapper.toDto(rooms);
    }

    @Override
    public RoomsDTO update(RoomsDTO roomsDTO) {
        log.debug("Request to update Rooms : {}", roomsDTO);
        Rooms rooms = roomsMapper.toEntity(roomsDTO);
        rooms = roomsRepository.save(rooms);
        return roomsMapper.toDto(rooms);
    }

    @Override
    public Optional<RoomsDTO> partialUpdate(RoomsDTO roomsDTO) {
        log.debug("Request to partially update Rooms : {}", roomsDTO);

        return roomsRepository
            .findById(roomsDTO.getId())
            .map(existingRooms -> {
                roomsMapper.partialUpdate(existingRooms, roomsDTO);

                return existingRooms;
            })
            .map(roomsRepository::save)
            .map(roomsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rooms");
        return roomsRepository.findAll(pageable).map(roomsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomsDTO> findOne(Long id) {
        log.debug("Request to get Rooms : {}", id);
        return roomsRepository.findById(id).map(roomsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rooms : {}", id);
        roomsRepository.deleteById(id);
    }
}
