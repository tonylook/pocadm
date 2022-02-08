package ch.orange.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VesselTimeContractMapperTest {

    private VesselTimeContractMapper vesselTimeContractMapper;

    @BeforeEach
    public void setUp() {
        vesselTimeContractMapper = new VesselTimeContractMapperImpl();
    }
}
