package ch.orange.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.orange.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VesselTimeContractTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VesselTimeContract.class);
        VesselTimeContract vesselTimeContract1 = new VesselTimeContract();
        vesselTimeContract1.setId(1L);
        VesselTimeContract vesselTimeContract2 = new VesselTimeContract();
        vesselTimeContract2.setId(vesselTimeContract1.getId());
        assertThat(vesselTimeContract1).isEqualTo(vesselTimeContract2);
        vesselTimeContract2.setId(2L);
        assertThat(vesselTimeContract1).isNotEqualTo(vesselTimeContract2);
        vesselTimeContract1.setId(null);
        assertThat(vesselTimeContract1).isNotEqualTo(vesselTimeContract2);
    }
}
