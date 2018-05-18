package ru.kpfu.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Treatment entity.
 */
public class TreatmentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 512)
    private String name;

    @NotNull
    @Size(max = 20)
    private String code;

    @Size(max = 20)
    private String parentCode;

    @NotNull
    private Integer nodeCount;

    private String additionalInfo;

    private Long parentIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Long getParentIdId() {
        return parentIdId;
    }

    public void setParentIdId(Long treatmentId) {
        this.parentIdId = treatmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TreatmentDTO treatmentDTO = (TreatmentDTO) o;
        if(treatmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), treatmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TreatmentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", parentCode='" + getParentCode() + "'" +
            ", nodeCount=" + getNodeCount() +
            ", additionalInfo='" + getAdditionalInfo() + "'" +
            "}";
    }
}
