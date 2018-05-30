package ru.kpfu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Diseas.
 */
@Entity
@Table(name = "diseas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Diseas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 512)
    @Column(name = "name", length = 512, nullable = false)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Size(max = 20)
    @Column(name = "parent_code", length = 20)
    private String parentCode;

    @NotNull
    @Column(name = "node_count", nullable = false)
    private Integer nodeCount;

    @Column(name = "additional_info")
    private String additionalInfo;

    @ManyToOne
    private Diseas parentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Diseas name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Diseas code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public Diseas parentCode(String parentCode) {
        this.parentCode = parentCode;
        return this;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getNodeCount() {
        return nodeCount;
    }

    public Diseas nodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
        return this;
    }

    public void setNodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public Diseas additionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Diseas getParentId() {
        return parentId;
    }

    public Diseas parentId(Diseas diseas) {
        this.parentId = diseas;
        return this;
    }

    public void setParentId(Diseas diseas) {
        this.parentId = diseas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Diseas diseas = (Diseas) o;
        if (diseas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diseas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Diseas{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", parentCode='" + getParentCode() + "'" +
            ", nodeCount=" + getNodeCount() +
            ", additionalInfo='" + getAdditionalInfo() + "'" +
            "}";
    }
}
