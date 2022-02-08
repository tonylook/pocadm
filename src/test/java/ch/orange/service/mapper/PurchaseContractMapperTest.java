package ch.orange.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseContractMapperTest {

    private PurchaseContractMapper purchaseContractMapper;

    @BeforeEach
    public void setUp() {
        purchaseContractMapper = new PurchaseContractMapperImpl();
    }
}
