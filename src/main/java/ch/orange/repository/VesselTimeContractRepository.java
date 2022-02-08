package ch.orange.repository;

import ch.orange.domain.VesselTimeContract;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VesselTimeContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VesselTimeContractRepository extends JpaRepository<VesselTimeContract, Long> {}
