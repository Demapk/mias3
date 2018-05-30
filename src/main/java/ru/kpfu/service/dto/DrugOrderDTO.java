package ru.kpfu.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DrugOrder entity.
 */
public class DrugOrderDTO implements Serializable {

    private Long id;

    private Double dose;

    private Double dailyDose;

    private String units;

    private String frequencey;

    private Integer quantity;

    private Long doctorId;

    private Long drugId;

    private Long prescriptionId;

    private Long patientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDose() {
        return dose;
    }

    public void setDose(Double dose) {
        this.dose = dose;
    }

    public Double getDailyDose() {
        return dailyDose;
    }

    public void setDailyDose(Double dailyDose) {
        this.dailyDose = dailyDose;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getFrequencey() {
        return frequencey;
    }

    public void setFrequencey(String frequencey) {
        this.frequencey = frequencey;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DrugOrderDTO drugOrderDTO = (DrugOrderDTO) o;
        if(drugOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drugOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DrugOrderDTO{" +
            "id=" + getId() +
            ", dose=" + getDose() +
            ", dailyDose=" + getDailyDose() +
            ", units='" + getUnits() + "'" +
            ", frequencey='" + getFrequencey() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
