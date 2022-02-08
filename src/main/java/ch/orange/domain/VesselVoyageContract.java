package ch.orange.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * A VesselVoyageContract.
 */
@Entity
@Table(name = "vessel_voyage_contract")
public class VesselVoyageContract implements Serializable {

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

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    @Column(name = "period")
    private Long period;

    @Column(name = "cost", precision = 21, scale = 2)
    private BigDecimal cost;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VesselVoyageContract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHolds() {
        return this.holds;
    }

    public VesselVoyageContract holds(Integer holds) {
        this.setHolds(holds);
        return this;
    }

    public void setHolds(Integer holds) {
        this.holds = holds;
    }

    public Float getHoldCapacity() {
        return this.holdCapacity;
    }

    public VesselVoyageContract holdCapacity(Float holdCapacity) {
        this.setHoldCapacity(holdCapacity);
        return this;
    }

    public void setHoldCapacity(Float holdCapacity) {
        this.holdCapacity = holdCapacity;
    }

    public String getSource() {
        return this.source;
    }

    public VesselVoyageContract source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return this.destination;
    }

    public VesselVoyageContract destination(String destination) {
        this.setDestination(destination);
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getPeriod() {
        return this.period;
    }

    public VesselVoyageContract period(Long period) {
        this.setPeriod(period);
        return this;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    public VesselVoyageContract cost(BigDecimal cost) {
        this.setCost(cost);
        return this;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VesselVoyageContract)) {
            return false;
        }
        return id != null && id.equals(((VesselVoyageContract) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VesselVoyageContract{" +
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
