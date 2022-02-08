package ch.orange.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.orange.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VesselTimeContractDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VesselTimeContractDTO.class);
        VesselTimeContractDTO vesselTimeContractDTO1 = new VesselTimeContractDTO();
        vesselTimeContractDTO1.setId(1L);
        VesselTimeContractDTO vesselTimeContractDTO2 = new VesselTimeContractDTO();
        assertThat(vesselTimeContractDTO1).isNotEqualTo(vesselTimeContractDTO2);
        vesselTimeContractDTO2.setId(vesselTimeContractDTO1.getId());
        assertThat(vesselTimeContractDTO1).isEqualTo(vesselTimeContractDTO2);
        vesselTimeContractDTO2.setId(2L);
        assertThat(vesselTimeContractDTO1).isNotEqualTo(vesselTimeContractDTO2);
        vesselTimeContractDTO1.setId(null);
        assertThat(vesselTimeContractDTO1).isNotEqualTo(vesselTimeContractDTO2);
    }
}
