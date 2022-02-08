package ch.orange.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * A VesselTimeContract.
 */
@Entity
@Table(name = "vessel_time_contract")
public class VesselTimeContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "holds")
    private Integer holds;

    @Column(name = "hold_capacity")
    private Float holdCapacity;

    @Column(name = "period")
    private Long period;

    @Column(name = "cost_per_day", precision = 21, scale = 2)
    private BigDecimal costPerDay;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VesselTimeContract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHolds() {
        return this.holds;
    }

    public VesselTimeContract holds(Integer holds) {
        this.setHolds(holds);
        return this;
    }

    public void setHolds(Integer holds) {
        this.holds = holds;
    }

    public Float getHoldCapacity() {
        return this.holdCapacity;
    }

    public VesselTimeContract holdCapacity(Float holdCapacity) {
        this.setHoldCapacity(holdCapacity);
        return this;
    }

    public void setHoldCapacity(Float holdCapacity) {
        this.holdCapacity = holdCapacity;
    }

    public Long getPeriod() {
        return this.period;
    }

    public VesselTimeContract period(Long period) {
        this.setPeriod(period);
        return this;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public BigDecimal getCostPerDay() {
        return this.costPerDay;
    }

    public VesselTimeContract costPerDay(BigDecimal costPerDay) {
        this.setCostPerDay(costPerDay);
        return this;
    }

    public void setCostPerDay(BigDecimal costPerDay) {
        this.costPerDay = costPerDay;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VesselTimeContract)) {
            return false;
        }
        return id != null && id.equals(((VesselTimeContract) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VesselTimeContract{" +
            "id=" + getId() +
            ", holds=" + getHolds() +
            ", holdCapacity=" + getHoldCapacity() +
            ", period=" + getPeriod() +
            ", costPerDay=" + getCostPerDay() +
            "}";
    }
}
