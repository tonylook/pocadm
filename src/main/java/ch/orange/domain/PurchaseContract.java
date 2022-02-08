package ch.orange.domain;

import ch.orange.domain.enumeration.Quality;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PurchaseContract.
 */
@Entity
@Table(name = "purchase_contract")
public class PurchaseContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "purchasing_window")
    private Long purchasingWindow;

    @Enumerated(EnumType.STRING)
    @Column(name = "soymeal_quality")
    private Quality soymealQuality;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "volume")
    private Float volume;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(optional = false)
    @NotNull
    private Port port;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PurchaseContract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurchasingWindow() {
        return this.purchasingWindow;
    }

    public PurchaseContract purchasingWindow(Long purchasingWindow) {
        this.setPurchasingWindow(purchasingWindow);
        return this;
    }

    public void setPurchasingWindow(Long purchasingWindow) {
        this.purchasingWindow = purchasingWindow;
    }

    public Quality getSoymealQuality() {
        return this.soymealQuality;
    }

    public PurchaseContract soymealQuality(Quality soymealQuality) {
        this.setSoymealQuality(soymealQuality);
        return this;
    }

    public void setSoymealQuality(Quality soymealQuality) {
        this.soymealQuality = soymealQuality;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public PurchaseContract price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getVolume() {
        return this.volume;
    }

    public PurchaseContract volume(Float volume) {
        this.setVolume(volume);
        return this;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public PurchaseContract status(Boolean status) {
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

    public PurchaseContract port(Port port) {
        this.setPort(port);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseContract)) {
            return false;
        }
        return id != null && id.equals(((PurchaseContract) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseContract{" +
            "id=" + getId() +
            ", purchasingWindow=" + getPurchasingWindow() +
            ", soymealQuality='" + getSoymealQuality() + "'" +
            ", price=" + getPrice() +
            ", volume=" + getVolume() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
