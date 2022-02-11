package ch.orange.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Port.
 */
@Entity
@Table(name = "port")
public class Port implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "loading_time")
    private Long loadingTime;

    @Column(name = "unloading_time")
    private Long unloadingTime;

    @Column(name = "waiting_time")
    private Float waitingTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Port id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoadingTime() {
        return this.loadingTime;
    }

    public Port loadingTime(Long loadingTime) {
        this.setLoadingTime(loadingTime);
        return this;
    }

    public void setLoadingTime(Long loadingTime) {
        this.loadingTime = loadingTime;
    }

    public Long getUnloadingTime() {
        return this.unloadingTime;
    }

    public Port unloadingTime(Long unloadingTime) {
        this.setUnloadingTime(unloadingTime);
        return this;
    }

    public void setUnloadingTime(Long unloadingTime) {
        this.unloadingTime = unloadingTime;
    }

    public Float getWaitingTime() {
        return this.waitingTime;
    }

    public Port waitingTime(Float waitingTime) {
        this.setWaitingTime(waitingTime);
        return this;
    }

    public void setWaitingTime(Float waitingTime) {
        this.waitingTime = waitingTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Port)) {
            return false;
        }
        return id != null && id.equals(((Port) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Port{" +
            "id=" + getId() +
            ", loadingTime=" + getLoadingTime() +
            ", unloadingTime=" + getUnloadingTime() +
            ", waitingTime=" + getWaitingTime() +
            "}";
    }
}
