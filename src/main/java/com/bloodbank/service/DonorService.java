package com.bloodbank.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bloodbank.model.Donor;
import com.bloodbank.repository.DonorRepository;

@Service
public class DonorService {
    @Autowired
    private DonorRepository donorRepository;

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));
    }

    public List<Donor> getDonorsByBloodGroup(String bloodGroup) {
        return donorRepository.findByBloodGroup(bloodGroup);
    }

    public List<Donor> searchDonorsByName(String name) {
        return donorRepository.findByNameContainingIgnoreCase(name);
    }

    public Donor saveDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    public Donor updateDonor(Long id, Donor donorDetails) {
        Donor donor = getDonorById(id);
        donor.setName(donorDetails.getName());
        donor.setContactNumber(donorDetails.getContactNumber());
        donor.setEmail(donorDetails.getEmail());
        donor.setBloodGroup(donorDetails.getBloodGroup());
        donor.setLastDonationDate(donorDetails.getLastDonationDate());
        return donorRepository.save(donor);
    }

    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }

    public boolean isDonorExistsByContactNumber(String contactNumber) {
        return donorRepository.existsByContactNumber(contactNumber);
    }
}