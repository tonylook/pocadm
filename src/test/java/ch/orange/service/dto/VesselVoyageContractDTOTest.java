package ch.orange.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.orange.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VesselVoyageContractDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VesselVoyageContractDTO.class);
        VesselVoyageContractDTO vesselVoyageContractDTO1 = new VesselVoyageContractDTO();
        vesselVoyageContractDTO1.setId(1L);
        VesselVoyageContractDTO vesselVoyageContractDTO2 = new VesselVoyageContractDTO();
        assertThat(vesselVoyageContractDTO1).isNotEqualTo(vesselVoyageContractDTO2);
        vesselVoyageContractDTO2.setId(vesselVoyageContractDTO1.getId());
        assertThat(vesselVoyageContractDTO1).isEqualTo(vesselVoyageContractDTO2);
        vesselVoyageContractDTO2.setId(2L);
        assertThat(vesselVoyageContractDTO1).isNotEqualTo(vesselVoyageContractDTO2);
        vesselVoyageContractDTO1.setId(null);
        assertThat(vesselVoyageContractDTO1).isNotEqualTo(vesselVoyageContractDTO2);
    }
}
