package com.bloodbank.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bloodbank.model.BloodInventory;
import com.bloodbank.service.BloodInventoryService;

@RestController
@RequestMapping("/api/inventory")
public class BloodInventoryController {
    @Autowired
    private BloodInventoryService bloodInventoryService;

    @GetMapping
    public ResponseEntity<List<BloodInventory>> getAllInventory() {
        return ResponseEntity.ok(bloodInventoryService.getAllBloodInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodInventory> getInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(bloodInventoryService.getBloodInventoryById(id));
    }

    @PostMapping
    public ResponseEntity<BloodInventory> createInventory(@RequestBody BloodInventory bloodInventory) {
        return ResponseEntity.ok(bloodInventoryService.saveBloodInventory(bloodInventory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodInventory> updateInventory(@PathVariable Long id, @RequestBody BloodInventory bloodInventoryDetails) {
        return ResponseEntity.ok(bloodInventoryService.updateBloodInventory(id, bloodInventoryDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        bloodInventoryService.deleteBloodInventory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available/{bloodGroup}/{units}")
    public ResponseEntity<Boolean> isBloodAvailable(@PathVariable String bloodGroup, @PathVariable int units) {
        return ResponseEntity.ok(bloodInventoryService.isBloodAvailable(bloodGroup, units));
    }

    @PostMapping("/update/{bloodGroup}/{units}")
    public ResponseEntity<Void> updateInventory(@PathVariable String bloodGroup, @PathVariable int units) {
        bloodInventoryService.updateInventory(bloodGroup, units);
        return ResponseEntity.noContent().build();
    }
}