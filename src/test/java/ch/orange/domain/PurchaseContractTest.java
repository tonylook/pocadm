package ch.orange.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.orange.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PurchaseContractTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseContract.class);
        PurchaseContract purchaseContract1 = new PurchaseContract();
        purchaseContract1.setId(1L);
        PurchaseContract purchaseContract2 = new PurchaseContract();
        purchaseContract2.setId(purchaseContract1.getId());
        assertThat(purchaseContract1).isEqualTo(purchaseContract2);
        purchaseContract2.setId(2L);
        assertThat(purchaseContract1).isNotEqualTo(purchaseContract2);
        purchaseContract1.setId(null);
        assertThat(purchaseContract1).isNotEqualTo(purchaseContract2);
    }
}
