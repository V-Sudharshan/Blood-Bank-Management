package com.bloodbank.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bloodbank.model.BloodDonation;
import com.bloodbank.model.Donor;

public interface BloodDonationRepository extends JpaRepository<BloodDonation, Long> {
    List<BloodDonation> findByDonor(Donor donor);
    List<BloodDonation> findByBloodGroup(String bloodGroup);
    List<BloodDonation> findByStatus(String status);
}