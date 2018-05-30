package ru.kpfu.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Procedure entity.
 */
public class ProcedureDTO implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProcedureDTO procedureDTO = (ProcedureDTO) o;
        if(procedureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), procedureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProcedureDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", parentCode='" + getParentCode() + "'" +
            ", nodeCount=" + getNodeCount() +
            ", additionalInfo='" + getAdditionalInfo() + "'" +
            "}";
    }
}
