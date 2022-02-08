package ch.orange.service.impl;

import ch.orange.domain.PurchaseContract;
import ch.orange.repository.PurchaseContractRepository;
import ch.orange.service.PurchaseContractService;
import ch.orange.service.dto.PurchaseContractDTO;
import ch.orange.service.mapper.PurchaseContractMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PurchaseContract}.
 */
@Service
@Transactional
public class PurchaseContractServiceImpl implements PurchaseContractService {

    private final Logger log = LoggerFactory.getLogger(PurchaseContractServiceImpl.class);

    private final PurchaseContractRepository purchaseContractRepository;

    private final PurchaseContractMapper purchaseContractMapper;

    public PurchaseContractServiceImpl(
        PurchaseContractRepository purchaseContractRepository,
        PurchaseContractMapper purchaseContractMapper
    ) {
        this.purchaseContractRepository = purchaseContractRepository;
        this.purchaseContractMapper = purchaseContractMapper;
    }

    @Override
    public PurchaseContractDTO save(PurchaseContractDTO purchaseContractDTO) {
        log.debug("Request to save PurchaseContract : {}", purchaseContractDTO);
        PurchaseContract purchaseContract = purchaseContractMapper.toEntity(purchaseContractDTO);
        purchaseContract = purchaseContractRepository.save(purchaseContract);
        return purchaseContractMapper.toDto(purchaseContract);
    }

    @Override
    public Optional<PurchaseContractDTO> partialUpdate(PurchaseContractDTO purchaseContractDTO) {
        log.debug("Request to partially update PurchaseContract : {}", purchaseContractDTO);

        return purchaseContractRepository
            .findById(purchaseContractDTO.getId())
            .map(existingPurchaseContract -> {
                purchaseContractMapper.partialUpdate(existingPurchaseContract, purchaseContractDTO);

                return existingPurchaseContract;
            })
            .map(purchaseContractRepository::save)
            .map(purchaseContractMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseContractDTO> findAll() {
        log.debug("Request to get all PurchaseContracts");
        return purchaseContractRepository
            .findAll()
            .stream()
            .map(purchaseContractMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseContractDTO> findOne(Long id) {
        log.debug("Request to get PurchaseContract : {}", id);
        return purchaseContractRepository.findById(id).map(purchaseContractMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseContract : {}", id);
        purchaseContractRepository.deleteById(id);
    }
}
