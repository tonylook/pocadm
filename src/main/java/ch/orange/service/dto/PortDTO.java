package ch.orange.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ch.orange.domain.Port} entity.
 */
public class PortDTO implements Serializable {

    private Long id;

    private Long lodingTime;

    private Long unloadingTime;

    private Float waitingTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLodingTime() {
        return lodingTime;
    }

    public void setLodingTime(Long lodingTime) {
        this.lodingTime = lodingTime;
    }

    public Long getUnloadingTime() {
        return unloadingTime;
    }

    public void setUnloadingTime(Long unloadingTime) {
        this.unloadingTime = unloadingTime;
    }

    public Float getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Float waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortDTO)) {
            return false;
        }

        PortDTO portDTO = (PortDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, portDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PortDTO{" +
            "id=" + getId() +
            ", lodingTime=" + getLodingTime() +
            ", unloadingTime=" + getUnloadingTime() +
            ", waitingTime=" + getWaitingTime() +
            "}";
    }
}
