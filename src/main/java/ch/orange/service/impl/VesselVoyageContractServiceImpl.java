package ch.orange.service.impl;

import ch.orange.domain.VesselVoyageContract;
import ch.orange.repository.VesselVoyageContractRepository;
import ch.orange.service.VesselVoyageContractService;
import ch.orange.service.dto.VesselVoyageContractDTO;
import ch.orange.service.mapper.VesselVoyageContractMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VesselVoyageContract}.
 */
@Service
@Transactional
public class VesselVoyageContractServiceImpl implements VesselVoyageContractService {

    private final Logger log = LoggerFactory.getLogger(VesselVoyageContractServiceImpl.class);

    private final VesselVoyageContractRepository vesselVoyageContractRepository;

    private final VesselVoyageContractMapper vesselVoyageContractMapper;

    public VesselVoyageContractServiceImpl(
        VesselVoyageContractRepository vesselVoyageContractRepository,
        VesselVoyageContractMapper vesselVoyageContractMapper
    ) {
        this.vesselVoyageContractRepository = vesselVoyageContractRepository;
        this.vesselVoyageContractMapper = vesselVoyageContractMapper;
    }

    @Override
    public VesselVoyageContractDTO save(VesselVoyageContractDTO vesselVoyageContractDTO) {
        log.debug("Request to save VesselVoyageContract : {}", vesselVoyageContractDTO);
        VesselVoyageContract vesselVoyageContract = vesselVoyageContractMapper.toEntity(vesselVoyageContractDTO);
        vesselVoyageContract = vesselVoyageContractRepository.save(vesselVoyageContract);
        return vesselVoyageContractMapper.toDto(vesselVoyageContract);
    }

    @Override
    public Optional<VesselVoyageContractDTO> partialUpdate(VesselVoyageContractDTO vesselVoyageContractDTO) {
        log.debug("Request to partially update VesselVoyageContract : {}", vesselVoyageContractDTO);

        return vesselVoyageContractRepository
            .findById(vesselVoyageContractDTO.getId())
            .map(existingVesselVoyageContract -> {
                vesselVoyageContractMapper.partialUpdate(existingVesselVoyageContract, vesselVoyageContractDTO);

                return existingVesselVoyageContract;
            })
            .map(vesselVoyageContractRepository::save)
            .map(vesselVoyageContractMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VesselVoyageContractDTO> findAll() {
        log.debug("Request to get all VesselVoyageContracts");
        return vesselVoyageContractRepository
            .findAll()
            .stream()
            .map(vesselVoyageContractMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VesselVoyageContractDTO> findOne(Long id) {
        log.debug("Request to get VesselVoyageContract : {}", id);
        return vesselVoyageContractRepository.findById(id).map(vesselVoyageContractMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VesselVoyageContract : {}", id);
        vesselVoyageContractRepository.deleteById(id);
    }
}
