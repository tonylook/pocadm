package ch.orange.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link ch.orange.domain.VesselTimeContract} entity.
 */
public class VesselTimeContractDTO implements Serializable {

    private Long id;

    private Integer holds;

    private Float holdCapacity;

    private Long period;

    private BigDecimal costPerDay;

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

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public BigDecimal getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(BigDecimal costPerDay) {
        this.costPerDay = costPerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VesselTimeContractDTO)) {
            return false;
        }

        VesselTimeContractDTO vesselTimeContractDTO = (VesselTimeContractDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vesselTimeContractDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VesselTimeContractDTO{" +
            "id=" + getId() +
            ", holds=" + getHolds() +
            ", holdCapacity=" + getHoldCapacity() +
            ", period=" + getPeriod() +
            ", costPerDay=" + getCostPerDay() +
            "}";
    }
}
