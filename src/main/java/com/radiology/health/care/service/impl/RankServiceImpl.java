package com.radiology.health.care.service.impl;

import com.radiology.health.care.domain.Rank;
import com.radiology.health.care.repository.RankRepository;
import com.radiology.health.care.repository.UserRepository;
import com.radiology.health.care.security.SecurityUtils;
import com.radiology.health.care.service.RankService;
import com.radiology.health.care.service.dto.AdminUserDTO;
import com.radiology.health.care.service.dto.RankDTO;
import com.radiology.health.care.service.mapper.RankMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.radiology.health.care.domain.Rank}.
 */
@Service
@Transactional
public class RankServiceImpl implements RankService {

    private final Logger log = LoggerFactory.getLogger(RankServiceImpl.class);

    private final RankRepository rankRepository;

    private final RankMapper rankMapper;

    private final UserRepository userRepository;

    public RankServiceImpl(RankRepository rankRepository, RankMapper rankMapper, UserRepository userRepository) {
        this.rankRepository = rankRepository;
        this.rankMapper = rankMapper;
        this.userRepository = userRepository;
    }

    @Override
    public RankDTO save(RankDTO rankDTO) {
        log.debug("Request to save Rank : {}", rankDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        rankDTO.setUserId(adminUser.getId());
        rankDTO.setLogin(adminUser.getLogin());
        Rank rank = rankMapper.toEntity(rankDTO);
        rank = rankRepository.save(rank);
        return rankMapper.toDto(rank);
    }

    @Override
    public RankDTO update(RankDTO rankDTO) {
        log.debug("Request to update Rank : {}", rankDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        rankDTO.setUserId(adminUser.getId());
        rankDTO.setLogin(adminUser.getLogin());
        Rank rank = rankMapper.toEntity(rankDTO);
        rank = rankRepository.save(rank);
        return rankMapper.toDto(rank);
    }

    @Override
    public Optional<RankDTO> partialUpdate(RankDTO rankDTO) {
        log.debug("Request to partially update Rank : {}", rankDTO);
        AdminUserDTO adminUser = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new RuntimeException("User could not be found"));
        rankDTO.setUserId(adminUser.getId());
        rankDTO.setLogin(adminUser.getLogin());

        return rankRepository
            .findById(rankDTO.getId())
            .map(existingRank -> {
                rankMapper.partialUpdate(existingRank, rankDTO);

                return existingRank;
            })
            .map(rankRepository::save)
            .map(rankMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RankDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ranks");
        return rankRepository.findAll(pageable).map(rankMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RankDTO> findOne(Long id) {
        log.debug("Request to get Rank : {}", id);
        return rankRepository.findById(id).map(rankMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rank : {}", id);
        rankRepository.deleteById(id);
    }
}
