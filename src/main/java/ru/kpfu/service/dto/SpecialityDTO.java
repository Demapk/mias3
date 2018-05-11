package ru.kpfu.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Speciality entity.
 */
public class SpecialityDTO implements Serializable {

    private Long id;

    private String speciality;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpecialityDTO specialityDTO = (SpecialityDTO) o;
        if(specialityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpecialityDTO{" +
            "id=" + getId() +
            ", speciality='" + getSpeciality() + "'" +
            "}";
    }
}
