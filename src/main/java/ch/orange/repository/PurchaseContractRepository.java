package ch.orange.repository;

import ch.orange.domain.PurchaseContract;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PurchaseContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseContractRepository extends JpaRepository<PurchaseContract, Long> {}
