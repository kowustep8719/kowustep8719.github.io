/*Programmer: Stephen Owusu-Agyekum
* Date: 2024-04-16
* Version: 6.13.4
* Description: This Angular component (EditTripComponent) allows users to edit trip details.
* Course: CS465-Full Stack Development I
* School name: Southern New Hampshire University
*/

// Import necessary Angular modules and services
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { TripDataService } from "../services/trip-data.service";

// Template file for the component and CSS styles for the component
@Component({
  selector: "app-edit-trip",
  templateUrl: "./edit-trip.component.html", 
  styleUrls: ["./edit-trip.component.css"], 
})
export class EditTripComponent implements OnInit {
  // Define form group for editing trip details
  editForm: FormGroup;
  // Initialize submitted flag as false
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private tripService: TripDataService
  ) {}

  ngOnInit() {
    // Retrieve stashed tripId from local storage
    let tripCode = localStorage.getItem("tripCode");
    // Handle case where tripCode is not found
    if (!tripCode) {
      // Alert the user and redirect to home page
      alert("Something went wrong. Couldn't find the tripCode.");
      this.router.navigate([""]);
      return;
    }
    // Log the found tripCode
    console.log("Found tripCode: " + tripCode);
    // Initialize form with default values and validators
    this.editForm = this.formBuilder.group({
      _id: [],
      code: [tripCode, Validators.required],
      name: ["", Validators.required],
      length: ["", Validators.required],
      start: ["", Validators.required],
      resort: ["", Validators.required],
      perPerson: ["", Validators.required],
      image: ["", Validators.required],
      description: ["", Validators.required],
    });
    // Log the action of fetching trip data
    console.log("Fetching trip data for tripCode: " + tripCode);

    // Fetch trip data from the service and populate the form
    this.tripService.getTrip(tripCode).then((data) => {
      console.log("Received trip data:", data);
      // Use patchValue() to populate form fields with trip data
      this.editForm.patchValue(data[0]);
    });
  }

  onSubmit() {
    // Set submitted flag to true
    this.submitted = true;
    // Check if form is valid
    if (this.editForm.valid) {
      // Call service method to update trip data
      this.tripService.updateTrip(this.editForm.value).then((data) => {
        // Log the updated trip data
        console.log("Updated trip data:", data);
        // Navigate to home page after successful update
        this.router.navigate([""]); 
      });
    }
  }

  // Define a getter to easily access form controls
  get f() {
    return this.editForm.controls;
  }
}
