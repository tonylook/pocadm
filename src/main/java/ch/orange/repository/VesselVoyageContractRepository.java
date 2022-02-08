package ch.orange.repository;

import ch.orange.domain.VesselVoyageContract;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VesselVoyageContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VesselVoyageContractRepository extends JpaRepository<VesselVoyageContract, Long> {}
