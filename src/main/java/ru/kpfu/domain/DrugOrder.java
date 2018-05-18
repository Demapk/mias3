package ru.kpfu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DrugOrder.
 */
@Entity
@Table(name = "drug_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DrugOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dose")
    private Double dose;

    @Column(name = "daily_dose")
    private Double dailyDose;

    @Column(name = "units")
    private String units;

    @Column(name = "frequencey")
    private String frequencey;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Drug drug;

    @ManyToOne
    private Prescription prescription;

    @ManyToOne
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDose() {
        return dose;
    }

    public DrugOrder dose(Double dose) {
        this.dose = dose;
        return this;
    }

    public void setDose(Double dose) {
        this.dose = dose;
    }

    public Double getDailyDose() {
        return dailyDose;
    }

    public DrugOrder dailyDose(Double dailyDose) {
        this.dailyDose = dailyDose;
        return this;
    }

    public void setDailyDose(Double dailyDose) {
        this.dailyDose = dailyDose;
    }

    public String getUnits() {
        return units;
    }

    public DrugOrder units(String units) {
        this.units = units;
        return this;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getFrequencey() {
        return frequencey;
    }

    public DrugOrder frequencey(String frequencey) {
        this.frequencey = frequencey;
        return this;
    }

    public void setFrequencey(String frequencey) {
        this.frequencey = frequencey;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public DrugOrder quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public DrugOrder doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Drug getDrug() {
        return drug;
    }

    public DrugOrder drug(Drug drug) {
        this.drug = drug;
        return this;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public DrugOrder prescription(Prescription prescription) {
        this.prescription = prescription;
        return this;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Patient getPatient() {
        return patient;
    }

    public DrugOrder patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
        DrugOrder drugOrder = (DrugOrder) o;
        if (drugOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drugOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DrugOrder{" +
            "id=" + getId() +
            ", dose=" + getDose() +
            ", dailyDose=" + getDailyDose() +
            ", units='" + getUnits() + "'" +
            ", frequencey='" + getFrequencey() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
