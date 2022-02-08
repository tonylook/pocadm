package ch.orange.service.dto;

import ch.orange.domain.enumeration.Quality;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ch.orange.domain.PurchaseContract} entity.
 */
public class PurchaseContractDTO implements Serializable {

    private Long id;

    private Long purchasingWindow;

    private Quality soymealQuality;

    private BigDecimal price;

    private Float volume;

    private Boolean status;

    private PortDTO port;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurchasingWindow() {
        return purchasingWindow;
    }

    public void setPurchasingWindow(Long purchasingWindow) {
        this.purchasingWindow = purchasingWindow;
    }

    public Quality getSoymealQuality() {
        return soymealQuality;
    }

    public void setSoymealQuality(Quality soymealQuality) {
        this.soymealQuality = soymealQuality;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PortDTO getPort() {
        return port;
    }

    public void setPort(PortDTO port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseContractDTO)) {
            return false;
        }

        PurchaseContractDTO purchaseContractDTO = (PurchaseContractDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, purchaseContractDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseContractDTO{" +
            "id=" + getId() +
            ", purchasingWindow=" + getPurchasingWindow() +
            ", soymealQuality='" + getSoymealQuality() + "'" +
            ", price=" + getPrice() +
            ", volume=" + getVolume() +
            ", status='" + getStatus() + "'" +
            ", port=" + getPort() +
            "}";
    }
}
