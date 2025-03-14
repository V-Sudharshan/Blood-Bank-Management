package com.bloodbank.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bloodbank.model.BloodInventory;

public interface BloodInventoryRepository extends JpaRepository<BloodInventory, Long> {
    Optional<BloodInventory> findByBloodGroup(String bloodGroup); // Returns Optional<BloodInventory>
    List<BloodInventory> findByExpiryDateBefore(LocalDate expiryDate);
}