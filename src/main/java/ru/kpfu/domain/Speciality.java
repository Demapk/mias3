package ru.kpfu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Speciality.
 */
@Entity
@Table(name = "speciality")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Speciality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "speciality")
    private String speciality;

    @OneToMany(mappedBy = "speciality")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Doctor> doctors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public Speciality speciality(String speciality) {
        this.speciality = speciality;
        return this;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public Speciality doctors(Set<Doctor> doctors) {
        this.doctors = doctors;
        return this;
    }

    public Speciality addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.setSpeciality(this);
        return this;
    }

    public Speciality removeDoctor(Doctor doctor) {
        this.doctors.remove(doctor);
        doctor.setSpeciality(null);
        return this;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
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
        Speciality speciality = (Speciality) o;
        if (speciality.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), speciality.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Speciality{" +
            "id=" + getId() +
            ", speciality='" + getSpeciality() + "'" +
            "}";
    }
}
