package ch.orange.service.impl;

import ch.orange.domain.Port;
import ch.orange.repository.PortRepository;
import ch.orange.service.PortService;
import ch.orange.service.dto.PortDTO;
import ch.orange.service.mapper.PortMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Port}.
 */
@Service
@Transactional
public class PortServiceImpl implements PortService {

    private final Logger log = LoggerFactory.getLogger(PortServiceImpl.class);

    private final PortRepository portRepository;

    private final PortMapper portMapper;

    public PortServiceImpl(PortRepository portRepository, PortMapper portMapper) {
        this.portRepository = portRepository;
        this.portMapper = portMapper;
    }

    @Override
    public PortDTO save(PortDTO portDTO) {
        log.debug("Request to save Port : {}", portDTO);
        Port port = portMapper.toEntity(portDTO);
        port = portRepository.save(port);
        return portMapper.toDto(port);
    }

    @Override
    public Optional<PortDTO> partialUpdate(PortDTO portDTO) {
        log.debug("Request to partially update Port : {}", portDTO);

        return portRepository
            .findById(portDTO.getId())
            .map(existingPort -> {
                portMapper.partialUpdate(existingPort, portDTO);

                return existingPort;
            })
            .map(portRepository::save)
            .map(portMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PortDTO> findAll() {
        log.debug("Request to get all Ports");
        return portRepository.findAll().stream().map(portMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PortDTO> findOne(Long id) {
        log.debug("Request to get Port : {}", id);
        return portRepository.findById(id).map(portMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Port : {}", id);
        portRepository.deleteById(id);
    }
}
