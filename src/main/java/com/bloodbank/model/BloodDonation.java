package com.bloodbank.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "blood_donations")
public class BloodDonation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    @Column(name = "donation_date", nullable = false)
    private LocalDate donationDate;

    @Column(name = "blood_group", nullable = false)
    private String bloodGroup;

    @Column(nullable = false)
    private Integer units;

    @Column(name = "hemoglobin_level")
    private Double hemoglobinLevel;

    @Column(nullable = false)
    private String status; // ACCEPTED, REJECTED

    // Default constructor
    public BloodDonation() {
    }

    // Parameterized constructor
    public BloodDonation(Donor donor, LocalDate donationDate, String bloodGroup, Integer units, 
                         Double hemoglobinLevel, String status) {
        this.donor = donor;
        this.donationDate = donationDate;
        this.bloodGroup = bloodGroup;
        this.units = units;
        this.hemoglobinLevel = hemoglobinLevel;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Double getHemoglobinLevel() {
        return hemoglobinLevel;
    }

    public void setHemoglobinLevel(Double hemoglobinLevel) {
        this.hemoglobinLevel = hemoglobinLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BloodDonation{" +
                "id=" + id +
                ", donor=" + donor.getName() +
                ", donationDate=" + donationDate +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", units=" + units +
                ", hemoglobinLevel=" + hemoglobinLevel +
                ", status='" + status + '\'' +
                '}';
    }
}