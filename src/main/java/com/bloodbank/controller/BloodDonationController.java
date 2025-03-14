package com.bloodbank.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bloodbank.model.BloodDonation;
import com.bloodbank.service.BloodDonationService;

@RestController
@RequestMapping("/api/donations")
public class BloodDonationController {

    @Autowired
    private BloodDonationService bloodDonationService;

    // Get all donations
    @GetMapping
    public ResponseEntity<List<BloodDonation>> getAllDonations() {
        return ResponseEntity.ok(bloodDonationService.getAllDonations());
    }

    // Get a donation by ID
    @GetMapping("/{id}")
    public ResponseEntity<BloodDonation> getDonationById(@PathVariable Long id) {
        return ResponseEntity.ok(bloodDonationService.getDonationById(id));
    }

    // Create a donation
    @PostMapping
    public ResponseEntity<BloodDonation> createDonation(@RequestBody BloodDonation donation) {
        // Ensure the donation has a valid status
        if (donation.getStatus() == null) {
            donation.setStatus("PENDING"); // Default status
        }
        return ResponseEntity.ok(bloodDonationService.saveDonation(donation));
    }

    // Delete a donation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
        bloodDonationService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }

    // Accept a donation
    @PostMapping("/{id}/accept")
    public ResponseEntity<BloodDonation> acceptDonation(@PathVariable Long id) {
        return ResponseEntity.ok(bloodDonationService.acceptDonation(id));
    }
}