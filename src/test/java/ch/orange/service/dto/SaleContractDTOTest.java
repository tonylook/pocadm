package ch.orange.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.orange.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaleContractDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleContractDTO.class);
        SaleContractDTO saleContractDTO1 = new SaleContractDTO();
        saleContractDTO1.setId(1L);
        SaleContractDTO saleContractDTO2 = new SaleContractDTO();
        assertThat(saleContractDTO1).isNotEqualTo(saleContractDTO2);
        saleContractDTO2.setId(saleContractDTO1.getId());
        assertThat(saleContractDTO1).isEqualTo(saleContractDTO2);
        saleContractDTO2.setId(2L);
        assertThat(saleContractDTO1).isNotEqualTo(saleContractDTO2);
        saleContractDTO1.setId(null);
        assertThat(saleContractDTO1).isNotEqualTo(saleContractDTO2);
    }
}
