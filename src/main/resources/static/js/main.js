$(document).ready(function() {
    console.log("jQuery is loaded. Main.js is working.");

    // Hide all sections initially except home
    $("#donors-section, #inventory-section, #requests-section, #donations-section").hide();

    // Navigation Click Handlers
    $("#homeLink").click(function() {
        showSection("contentArea");
    });

    $("#donorsLink").click(function() {
        showSection("donors-section");
        loadDonors();
    });

    $("#inventoryLink").click(function() {
        showSection("inventory-section");
        loadInventory();
    });

    $("#requestsLink").click(function() {
        showSection("requests-section");
        loadRequests();
    });

    $("#donationsLink").click(function() {
        showSection("donations-section");
        loadDonations();
    });

    // Function to show a specific section
    function showSection(sectionId) {
        $("div[id$='-section']").hide(); // Hide all sections
        $("#" + sectionId).fadeIn(500).addClass("fade-in"); // Show the selected section with animation
    }

    // Load Donors
    function loadDonors() {
        $("#loadingSpinner").show();
        $.ajax({
            url: "/api/donors",
            type: "GET",
            headers: {
                "Authorization": "Bearer <your-jwt-token>" // Replace with your token
            },
            success: function(data) {
                let donorList = "";
                data.forEach(donor => {
                    donorList += `
                        <div class="col-md-4 mb-4">
                            <div class="card h-100 shadow">
                                <div class="card-body">
                                    <h5 class="card-title">${donor.name}</h5>
                                    <p class="card-text">Blood Group: ${donor.bloodGroup}</p>
                                    <p class="card-text">Contact: ${donor.contactNumber}</p>
                                </div>
                            </div>
                        </div>`;
                });
                $("#donor-list").html(donorList);
            },
            error: function(xhr) {
                $("#donor-list").html("<p>Error loading donors.</p>");
            },
            complete: function() {
                $("#loadingSpinner").hide();
            }
        });
    }

    // Load Inventory
    function loadInventory() {
        $("#loadingSpinner").show();
        $.ajax({
            url: "/api/inventory",
            type: "GET",
            headers: {
                "Authorization": "Bearer <your-jwt-token>" // Replace with your token
            },
            success: function(data) {
                let inventoryList = "";
                data.forEach(item => {
                    inventoryList += `
                        <div class="col-md-4 mb-4">
                            <div class="card h-100 shadow">
                                <div class="card-body">
                                    <h5 class="card-title">${item.bloodGroup}</h5>
                                    <p class="card-text">Quantity: ${item.quantity} units</p>
                                </div>
                            </div>
                        </div>`;
                });
                $("#inventory-list").html(inventoryList);
            },
            error: function(xhr) {
                $("#inventory-list").html("<p>Error loading inventory.</p>");
            },
            complete: function() {
                $("#loadingSpinner").hide();
            }
        });
    }

    // Load Requests
    function loadRequests() {
        $("#loadingSpinner").show();
        $.ajax({
            url: "/api/requests",
            type: "GET",
            headers: {
                "Authorization": "Bearer <your-jwt-token>" // Replace with your token
            },
            success: function(data) {
                let requestList = "";
                data.forEach(request => {
                    requestList += `
                        <div class="col-md-4 mb-4">
                            <div class="card h-100 shadow">
                                <div class="card-body">
                                    <h5 class="card-title">${request.patientName}</h5>
                                    <p class="card-text">Blood Group: ${request.bloodGroup}</p>
                                    <p class="card-text">Units Required: ${request.unitsRequired}</p>
                                </div>
                            </div>
                        </div>`;
                });
                $("#request-list").html(requestList);
            },
            error: function(xhr) {
                $("#request-list").html("<p>Error loading requests.</p>");
            },
            complete: function() {
                $("#loadingSpinner").hide();
            }
        });
    }

    // Load Donations
    function loadDonations() {
        $("#loadingSpinner").show();
        $.ajax({
            url: "/api/donations",
            type: "GET",
            headers: {
                "Authorization": "Bearer <your-jwt-token>" // Replace with your token
            },
            success: function(data) {
                let donationList = "";
                data.forEach(donation => {
                    donationList += `
                        <div class="col-md-4 mb-4">
                            <div class="card h-100 shadow">
                                <div class="card-body">
                                    <h5 class="card-title">${donation.donor.name}</h5>
                                    <p class="card-text">Blood Group: ${donation.bloodGroup}</p>
                                    <p class="card-text">Units: ${donation.units}</p>
                                    <p class="card-text">Status: ${donation.status}</p>
                                    ${donation.status === "PENDING" ? 
                                        `<button class="btn btn-success btn-sm" onclick="acceptDonation(${donation.id})">Accept</button>` : 
                                        ''
                                    }
                                </div>
                            </div>
                        </div>`;
                });
                $("#donation-list").html(donationList);
            },
            error: function(xhr) {
                $("#donation-list").html("<p>Error loading donations.</p>");
            },
            complete: function() {
                $("#loadingSpinner").hide();
            }
        });
    }

    // Accept Donation
    function acceptDonation(donationId) {
        $.ajax({
            url: `/api/donations/${donationId}/accept`,
            type: "POST",
            headers: {
                "Authorization": "Bearer <your-jwt-token>" // Replace with your token
            },
            success: function(response) {
                alert("Donation accepted successfully!");
                loadDonations(); // Refresh the list
            },
            error: function(xhr) {
                alert("Failed to accept donation.");
            }
        });
    }

    // Handle Donate Form Submission
    $("#donateForm").submit(function(e) {
        e.preventDefault();

        let donorData = {
            name: $("#donorName").val(),
            email: $("#donorEmail").val(),
            contactNumber: $("#donorContact").val(),
            bloodGroup: $("#donorBloodGroup").val(),
            lastDonationDate: $("#donorLastDonation").val() || null
        };

        console.log("Sending data:", donorData); // Debugging log

        $.ajax({
            url: "/api/donors",
            type: "POST",
            contentType: "application/json",
            headers: {
                "Authorization": "Bearer <your-jwt-token>" // Replace with your token
            },
            data: JSON.stringify(donorData),
            success: function(response) {
                alert("Donation Recorded Successfully!");
                $("#donateModal").modal("hide");
                $("#donateForm")[0].reset();
            },
            error: function(xhr) {
                alert("Failed to save donation. Check console for errors.");
                console.log(xhr.responseText);
            }
        });
    });

    // Handle Request Blood Form Submission
    $("#requestForm").submit(function(e) {
        e.preventDefault();

        let requestData = {
            patientName: $("#patientName").val(),
            hospitalName: $("#hospitalName").val(),
            contactNumber: $("#contactNumber").val(),
            bloodGroup: $("#bloodGroup").val(),
            unitsRequired: $("#unitsRequired").val(),
            requestDate: new Date().toISOString().split('T')[0], // Current date
            status: "PENDING" // Default status
        };

        console.log("Sending data:", requestData); // Debugging log

        $.ajax({
            url: "/api/requests",
            type: "POST",
            contentType: "application/json",
            headers: {
                "Authorization": "Bearer <your-jwt-token>" // Replace with your token
            },
            data: JSON.stringify(requestData),
            success: function(response) {
                alert("Blood Request Submitted Successfully!");
                $("#requestModal").modal("hide");
                $("#requestForm")[0].reset();
            },
            error: function(xhr) {
                alert("Failed to submit request. Check console for errors.");
                console.log(xhr.responseText);
            }
        });
    });

    // Handle Donation Form Submission
    $("#donationForm").submit(function(e) {
        e.preventDefault();

        let donationData = {
            donor: { id: $("#donorId").val() }, // Donor ID
            donationDate: $("#donationDate").val(),
            bloodGroup: $("#bloodGroup").val(),
            units: $("#units").val(),
            hemoglobinLevel: $("#hemoglobinLevel").val(),
            status: $("#status").val()
        };

        $.ajax({
            url: "/api/donations",
            type: "POST",
            contentType: "application/json",
            headers: {
                "Authorization": "Bearer <your-jwt-token>" // Replace with your token
            },
            data: JSON.stringify(donationData),
            success: function(response) {
                alert("Donation Recorded Successfully!");

                // Delete the donor after successful donation
                deleteDonor(donationData.donor.id);

                $("#donationModal").modal("hide");
                $("#donationForm")[0].reset();
                loadDonations(); // Refresh the donations list
            },
            error: function(xhr) {
                alert("Failed to save donation. Check console for errors.");
                console.log(xhr.responseText);
            }
        });
    });

    // Function to delete a donor
    function deleteDonor(donorId) {
        console.log("Deleting donor with ID:", donorId); // Debugging log
        $.ajax({
            url: `/api/donors/${donorId}`,
            type: "DELETE",
            headers: {
                "Authorization": "Bearer <your-jwt-token>" // Replace with your token
            },
            success: function(response) {
                alert("Donor deleted successfully!");
                loadDonors(); // Refresh the donors list
            },
            error: function(xhr) {
                alert("Failed to delete donor. Check console for errors.");
                console.log(xhr.responseText);
            }
        });
    }
});