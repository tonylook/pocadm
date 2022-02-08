package ch.orange.service.impl;

import ch.orange.domain.SaleContract;
import ch.orange.repository.SaleContractRepository;
import ch.orange.service.SaleContractService;
import ch.orange.service.dto.SaleContractDTO;
import ch.orange.service.mapper.SaleContractMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SaleContract}.
 */
@Service
@Transactional
public class SaleContractServiceImpl implements SaleContractService {

    private final Logger log = LoggerFactory.getLogger(SaleContractServiceImpl.class);

    private final SaleContractRepository saleContractRepository;

    private final SaleContractMapper saleContractMapper;

    public SaleContractServiceImpl(SaleContractRepository saleContractRepository, SaleContractMapper saleContractMapper) {
        this.saleContractRepository = saleContractRepository;
        this.saleContractMapper = saleContractMapper;
    }

    @Override
    public SaleContractDTO save(SaleContractDTO saleContractDTO) {
        log.debug("Request to save SaleContract : {}", saleContractDTO);
        SaleContract saleContract = saleContractMapper.toEntity(saleContractDTO);
        saleContract = saleContractRepository.save(saleContract);
        return saleContractMapper.toDto(saleContract);
    }

    @Override
    public Optional<SaleContractDTO> partialUpdate(SaleContractDTO saleContractDTO) {
        log.debug("Request to partially update SaleContract : {}", saleContractDTO);

        return saleContractRepository
            .findById(saleContractDTO.getId())
            .map(existingSaleContract -> {
                saleContractMapper.partialUpdate(existingSaleContract, saleContractDTO);

                return existingSaleContract;
            })
            .map(saleContractRepository::save)
            .map(saleContractMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleContractDTO> findAll() {
        log.debug("Request to get all SaleContracts");
        return saleContractRepository.findAll().stream().map(saleContractMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SaleContractDTO> findOne(Long id) {
        log.debug("Request to get SaleContract : {}", id);
        return saleContractRepository.findById(id).map(saleContractMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SaleContract : {}", id);
        saleContractRepository.deleteById(id);
    }
}
