package ch.orange.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.orange.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PurchaseContractDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseContractDTO.class);
        PurchaseContractDTO purchaseContractDTO1 = new PurchaseContractDTO();
        purchaseContractDTO1.setId(1L);
        PurchaseContractDTO purchaseContractDTO2 = new PurchaseContractDTO();
        assertThat(purchaseContractDTO1).isNotEqualTo(purchaseContractDTO2);
        purchaseContractDTO2.setId(purchaseContractDTO1.getId());
        assertThat(purchaseContractDTO1).isEqualTo(purchaseContractDTO2);
        purchaseContractDTO2.setId(2L);
        assertThat(purchaseContractDTO1).isNotEqualTo(purchaseContractDTO2);
        purchaseContractDTO1.setId(null);
        assertThat(purchaseContractDTO1).isNotEqualTo(purchaseContractDTO2);
    }
}
