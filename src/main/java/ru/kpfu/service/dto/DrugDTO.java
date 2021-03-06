package ru.kpfu.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Drug entity.
 */
public class DrugDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DrugDTO drugDTO = (DrugDTO) o;
        if(drugDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drugDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DrugDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
