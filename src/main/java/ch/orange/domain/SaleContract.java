package ch.orange.domain;

import ch.orange.domain.enumeration.Quality;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A SaleContract.
 */
@Entity
@Table(name = "sale_contract")
public class SaleContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "delivery_window")
    private Long deliveryWindow;

    @Enumerated(EnumType.STRING)
    @Column(name = "soymeal_quality")
    private Quality soymealQuality;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "volume")
    private Float volume;

    @Column(name = "allowances")
    private Float allowances;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(optional = false)
    @NotNull
    private Port port;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SaleContract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeliveryWindow() {
        return this.deliveryWindow;
    }

    public SaleContract deliveryWindow(Long deliveryWindow) {
        this.setDeliveryWindow(deliveryWindow);
        return this;
    }

    public void setDeliveryWindow(Long deliveryWindow) {
        this.deliveryWindow = deliveryWindow;
    }

    public Quality getSoymealQuality() {
        return this.soymealQuality;
    }

    public SaleContract soymealQuality(Quality soymealQuality) {
        this.setSoymealQuality(soymealQuality);
        return this;
    }

    public void setSoymealQuality(Quality soymealQuality) {
        this.soymealQuality = soymealQuality;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public SaleContract price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getVolume() {
        return this.volume;
    }

    public SaleContract volume(Float volume) {
        this.setVolume(volume);
        return this;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Float getAllowances() {
        return this.allowances;
    }

    public SaleContract allowances(Float allowances) {
        this.setAllowances(allowances);
        return this;
    }

    public void setAllowances(Float allowances) {
        this.allowances = allowances;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public SaleContract status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Port getPort() {
        return this.port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public SaleContract port(Port port) {
        this.setPort(port);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaleContract)) {
            return false;
        }
        return id != null && id.equals(((SaleContract) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleContract{" +
            "id=" + getId() +
            ", deliveryWindow=" + getDeliveryWindow() +
            ", soymealQuality='" + getSoymealQuality() + "'" +
            ", price=" + getPrice() +
            ", volume=" + getVolume() +
            ", allowances=" + getAllowances() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
