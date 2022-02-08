package ch.orange.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.orange.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VesselVoyageContractTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VesselVoyageContract.class);
        VesselVoyageContract vesselVoyageContract1 = new VesselVoyageContract();
        vesselVoyageContract1.setId(1L);
        VesselVoyageContract vesselVoyageContract2 = new VesselVoyageContract();
        vesselVoyageContract2.setId(vesselVoyageContract1.getId());
        assertThat(vesselVoyageContract1).isEqualTo(vesselVoyageContract2);
        vesselVoyageContract2.setId(2L);
        assertThat(vesselVoyageContract1).isNotEqualTo(vesselVoyageContract2);
        vesselVoyageContract1.setId(null);
        assertThat(vesselVoyageContract1).isNotEqualTo(vesselVoyageContract2);
    }
}
