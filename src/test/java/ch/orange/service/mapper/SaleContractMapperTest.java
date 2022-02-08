package ch.orange.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SaleContractMapperTest {

    private SaleContractMapper saleContractMapper;

    @BeforeEach
    public void setUp() {
        saleContractMapper = new SaleContractMapperImpl();
    }
}
