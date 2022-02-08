package ch.orange.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VesselVoyageContractMapperTest {

    private VesselVoyageContractMapper vesselVoyageContractMapper;

    @BeforeEach
    public void setUp() {
        vesselVoyageContractMapper = new VesselVoyageContractMapperImpl();
    }
}
