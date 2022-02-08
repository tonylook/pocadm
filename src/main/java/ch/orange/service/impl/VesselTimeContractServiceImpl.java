package ch.orange.service.impl;

import ch.orange.domain.VesselTimeContract;
import ch.orange.repository.VesselTimeContractRepository;
import ch.orange.service.VesselTimeContractService;
import ch.orange.service.dto.VesselTimeContractDTO;
import ch.orange.service.mapper.VesselTimeContractMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VesselTimeContract}.
 */
@Service
@Transactional
public class VesselTimeContractServiceImpl implements VesselTimeContractService {

    private final Logger log = LoggerFactory.getLogger(VesselTimeContractServiceImpl.class);

    private final VesselTimeContractRepository vesselTimeContractRepository;

    private final VesselTimeContractMapper vesselTimeContractMapper;

    public VesselTimeContractServiceImpl(
        VesselTimeContractRepository vesselTimeContractRepository,
        VesselTimeContractMapper vesselTimeContractMapper
    ) {
        this.vesselTimeContractRepository = vesselTimeContractRepository;
        this.vesselTimeContractMapper = vesselTimeContractMapper;
    }

    @Override
    public VesselTimeContractDTO save(VesselTimeContractDTO vesselTimeContractDTO) {
        log.debug("Request to save VesselTimeContract : {}", vesselTimeContractDTO);
        VesselTimeContract vesselTimeContract = vesselTimeContractMapper.toEntity(vesselTimeContractDTO);
        vesselTimeContract = vesselTimeContractRepository.save(vesselTimeContract);
        return vesselTimeContractMapper.toDto(vesselTimeContract);
    }

    @Override
    public Optional<VesselTimeContractDTO> partialUpdate(VesselTimeContractDTO vesselTimeContractDTO) {
        log.debug("Request to partially update VesselTimeContract : {}", vesselTimeContractDTO);

        return vesselTimeContractRepository
            .findById(vesselTimeContractDTO.getId())
            .map(existingVesselTimeContract -> {
                vesselTimeContractMapper.partialUpdate(existingVesselTimeContract, vesselTimeContractDTO);

                return existingVesselTimeContract;
            })
            .map(vesselTimeContractRepository::save)
            .map(vesselTimeContractMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VesselTimeContractDTO> findAll() {
        log.debug("Request to get all VesselTimeContracts");
        return vesselTimeContractRepository
            .findAll()
            .stream()
            .map(vesselTimeContractMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VesselTimeContractDTO> findOne(Long id) {
        log.debug("Request to get VesselTimeContract : {}", id);
        return vesselTimeContractRepository.findById(id).map(vesselTimeContractMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VesselTimeContract : {}", id);
        vesselTimeContractRepository.deleteById(id);
    }
}
