package ch.orange.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link ch.orange.domain.VesselVoyageContract} entity.
 */
public class VesselVoyageContractDTO implements Serializable {

    private Long id;

    private Integer holds;

    private Float holdCapacity;

    private String source;

    private String destination;

    private Long period;

    private BigDecimal cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHolds() {
        return holds;
    }

    public void setHolds(Integer holds) {
        this.holds = holds;
    }

    public Float getHoldCapacity() {
        return holdCapacity;
    }

    public void setHoldCapacity(Float holdCapacity) {
        this.holdCapacity = holdCapacity;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VesselVoyageContractDTO)) {
            return false;
        }

        VesselVoyageContractDTO vesselVoyageContractDTO = (VesselVoyageContractDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vesselVoyageContractDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VesselVoyageContractDTO{" +
            "id=" + getId() +
            ", holds=" + getHolds() +
            ", holdCapacity=" + getHoldCapacity() +
            ", source='" + getSource() + "'" +
            ", destination='" + getDestination() + "'" +
            ", period=" + getPeriod() +
            ", cost=" + getCost() +
            "}";
    }
}
