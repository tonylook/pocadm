package ch.orange.service.dto;

import ch.orange.domain.enumeration.Quality;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ch.orange.domain.SaleContract} entity.
 */
public class SaleContractDTO implements Serializable {

    private Long id;

    private Long deliveryWindow;

    private Quality soymealQuality;

    private BigDecimal price;

    private Float volume;

    private Float allowances;

    private Boolean status;

    private PortDTO port;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeliveryWindow() {
        return deliveryWindow;
    }

    public void setDeliveryWindow(Long deliveryWindow) {
        this.deliveryWindow = deliveryWindow;
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

    public Float getAllowances() {
        return allowances;
    }

    public void setAllowances(Float allowances) {
        this.allowances = allowances;
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
        if (!(o instanceof SaleContractDTO)) {
            return false;
        }

        SaleContractDTO saleContractDTO = (SaleContractDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, saleContractDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleContractDTO{" +
            "id=" + getId() +
            ", deliveryWindow=" + getDeliveryWindow() +
            ", soymealQuality='" + getSoymealQuality() + "'" +
            ", price=" + getPrice() +
            ", volume=" + getVolume() +
            ", allowances=" + getAllowances() +
            ", status='" + getStatus() + "'" +
            ", port=" + getPort() +
            "}";
    }
}
