package com.bloodbank.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bloodbank.model.BloodDonation;
import com.bloodbank.repository.BloodDonationRepository;

@Service
public class BloodDonationService {

    @Autowired
    private BloodDonationRepository bloodDonationRepository;

    @Autowired
    private BloodInventoryService bloodInventoryService;

    // Get all donations
    public List<BloodDonation> getAllDonations() {
        return bloodDonationRepository.findAll();
    }

    // Save a donation
    public BloodDonation saveDonation(BloodDonation donation) {
        // Ensure the donation has a valid status
        if (donation.getStatus() == null) {
            donation.setStatus("PENDING"); // Default status
        }
        return bloodDonationRepository.save(donation);
    }

    // Get a donation by ID
    public BloodDonation getDonationById(Long id) {
        return bloodDonationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
    }

    // Delete a donation
    public void deleteDonation(Long id) {
        bloodDonationRepository.deleteById(id);
    }

    // Accept a donation
    public BloodDonation acceptDonation(Long id) {
        BloodDonation donation = getDonationById(id);
        donation.setStatus("ACCEPTED");
        bloodDonationRepository.save(donation);

        // Update the inventory
        bloodInventoryService.updateInventory(donation.getBloodGroup(), donation.getUnits());

        return donation;
    }
}