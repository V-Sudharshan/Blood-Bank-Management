package com.bloodbank.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "donors")
public class Donor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column(name = "contact_number", nullable = false)
    private String contactNumber;
    
    @Column(name = "blood_group", nullable = false)
    private String bloodGroup;
    
    @Column(name = "last_donation_date")
    private LocalDate lastDonationDate;
    
    // Default constructor
    public Donor() {
    }
    
    // Parameterized constructor
    public Donor(String name, String email, String contactNumber, String bloodGroup, LocalDate lastDonationDate) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.bloodGroup = bloodGroup;
        this.lastDonationDate = lastDonationDate;
    }
    
    // Getters and Setters
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public String getBloodGroup() {
        return bloodGroup;
    }
    
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    
    public LocalDate getLastDonationDate() {
        return lastDonationDate;
    }
    
    public void setLastDonationDate(LocalDate lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }
    
    @Override
    public String toString() {
        return "Donor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", lastDonationDate=" + lastDonationDate +
                '}';
    }
}