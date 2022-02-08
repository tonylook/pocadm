package ch.orange.repository;

import ch.orange.domain.SaleContract;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SaleContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleContractRepository extends JpaRepository<SaleContract, Long> {}
