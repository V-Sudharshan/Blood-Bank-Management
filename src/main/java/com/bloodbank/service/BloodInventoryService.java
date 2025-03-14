package com.bloodbank.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bloodbank.model.BloodInventory;
import com.bloodbank.repository.BloodInventoryRepository;

@Service
public class BloodInventoryService {

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    // Get all inventory entries
    public List<BloodInventory> getAllBloodInventory() {
        return bloodInventoryRepository.findAll();
    }

    // Get inventory by ID
    public BloodInventory getBloodInventoryById(Long id) {
        return bloodInventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blood inventory not found with id: " + id));
    }

    // Get inventory by blood group
    public Optional<BloodInventory> getBloodInventoryByBloodGroup(String bloodGroup) {
        return bloodInventoryRepository.findByBloodGroup(bloodGroup);
    }

    // Get expired inventory
    public List<BloodInventory> getExpiredBloodInventory() {
        return bloodInventoryRepository.findByExpiryDateBefore(LocalDate.now());
    }

    // Save or update inventory
    public BloodInventory saveBloodInventory(BloodInventory bloodInventory) {
        return bloodInventoryRepository.save(bloodInventory);
    }

    // Update inventory by ID
    public BloodInventory updateBloodInventory(Long id, BloodInventory bloodInventoryDetails) {
        BloodInventory bloodInventory = getBloodInventoryById(id);
        bloodInventory.setBloodGroup(bloodInventoryDetails.getBloodGroup());
        bloodInventory.setQuantity(bloodInventoryDetails.getQuantity());
        bloodInventory.setCollectionDate(bloodInventoryDetails.getCollectionDate());
        bloodInventory.setExpiryDate(bloodInventoryDetails.getExpiryDate());
        return bloodInventoryRepository.save(bloodInventory);
    }

    // Delete inventory by ID
    public void deleteBloodInventory(Long id) {
        bloodInventoryRepository.deleteById(id);
    }

    // Check if blood is available
    public boolean isBloodAvailable(String bloodGroup, int units) {
        Optional<BloodInventory> inventoryOpt = bloodInventoryRepository.findByBloodGroup(bloodGroup);
        if (inventoryOpt.isPresent()) {
            BloodInventory inventory = inventoryOpt.get();
            return inventory.getQuantity() >= units && inventory.getExpiryDate().isAfter(LocalDate.now());
        }
        return false;
    }

    // Update inventory for a specific blood group
    public void updateInventory(String bloodGroup, Integer units) {
        Optional<BloodInventory> inventoryOpt = bloodInventoryRepository.findByBloodGroup(bloodGroup);
        if (inventoryOpt.isPresent()) {
            // Update existing inventory
            BloodInventory inventory = inventoryOpt.get();
            inventory.setQuantity(inventory.getQuantity() + units);
            bloodInventoryRepository.save(inventory);
        } else {
            // Create new inventory entry if it doesn't exist
            BloodInventory newInventory = new BloodInventory();
            newInventory.setBloodGroup(bloodGroup);
            newInventory.setQuantity(units);
            newInventory.setCollectionDate(LocalDate.now());
            newInventory.setExpiryDate(LocalDate.now().plusDays(42)); // 42 days expiry
            bloodInventoryRepository.save(newInventory);
        }
    }
}