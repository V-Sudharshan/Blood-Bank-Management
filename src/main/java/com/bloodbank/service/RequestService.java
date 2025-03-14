package com.bloodbank.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bloodbank.model.Request;
import com.bloodbank.model.RequestStatus;
import com.bloodbank.repository.RequestRepository;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private BloodInventoryService bloodInventoryService;

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Request getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id));
    }

    public List<Request> getRequestsByStatus(RequestStatus status) {
        return requestRepository.findByStatus(status);
    }

    public List<Request> getRequestsByBloodGroup(String bloodGroup) {
        return requestRepository.findByBloodGroup(bloodGroup);
    }

    public List<Request> getRequestsByHospital(String hospitalName) {
        return requestRepository.findByHospitalNameContainingIgnoreCase(hospitalName);
    }

    public Request saveRequest(Request request) {
        if (request.getStatus() == null) {
            request.setStatus(RequestStatus.PENDING);
        }
        return requestRepository.save(request);
    }

    public Request updateRequest(Long id, Request requestDetails) {
        Request request = getRequestById(id);
        request.setPatientName(requestDetails.getPatientName());
        request.setHospitalName(requestDetails.getHospitalName());
        request.setContactNumber(requestDetails.getContactNumber());
        request.setBloodGroup(requestDetails.getBloodGroup());
        request.setUnitsRequired(requestDetails.getUnitsRequired());
        request.setRequestDate(requestDetails.getRequestDate());
        request.setStatus(requestDetails.getStatus());
        return requestRepository.save(request);
    }

    public Request updateRequestStatus(Long id, RequestStatus status) {
        Request request = getRequestById(id);
        request.setStatus(status);
        return requestRepository.save(request);
    }

    public Request approveRequest(Long id) {
        Request request = getRequestById(id);
        if (request.getStatus() == RequestStatus.PENDING) {
            if (bloodInventoryService.isBloodAvailable(request.getBloodGroup(), request.getUnitsRequired())) {
                request.setStatus(RequestStatus.APPROVED);
                return requestRepository.save(request);
            } else {
                throw new RuntimeException("Not enough blood units available");
            }
        } else {
            throw new RuntimeException("Request must be in PENDING status to be approved");
        }
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }
}