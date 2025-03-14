package com.bloodbank.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bloodbank.model.Request;
import com.bloodbank.model.RequestStatus;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByStatus(RequestStatus status);
    List<Request> findByBloodGroup(String bloodGroup);
    List<Request> findByHospitalNameContainingIgnoreCase(String hospitalName);
}