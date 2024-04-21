/*Programmer: Stephen Owusu-Agyekum
* Date: 2024-04-16
* Version: 6.13.4
* Description: Component for adding a new trip
* Course: CS465-Full Stack Development I
* School name: Southern New Hampshire University
*/

// Import necessary Angular modules and services
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { TripDataService } from "../services/trip-data.service";

@Component({
  selector: "app-add-trip",
  templateUrl: "./add-trip.component.html",
  styleUrls: ["./add-trip.component.css"],
})
export class AddTripComponent implements OnInit {
  // Define form group for adding trip details
  addForm: FormGroup;
  // Initialize submitted flag as false
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private tripService: TripDataService
  ) {}

  ngOnInit() {
    // Initialize the form with validators
    this.addForm = this.formBuilder.group({
      _id: [],
      code: ["", Validators.required],
      name: ["", Validators.required],
      length: ["", Validators.required],
      start: ["", Validators.required],
      resort: ["", Validators.required],
      perPerson: ["", Validators.required],
      image: ["", Validators.required],
      description: ["", Validators.required],
    });
  }

  // Function triggered on form submission
  onSubmit() {
    // Set submitted flag to true
    this.submitted = true;
    // Check if form is valid
    if (this.addForm.valid) {
      // Call addTrip function from tripService
      this.tripService.addTrip(this.addForm.value).then((data) => {
        console.log("Added trip data:", data);
        // Navigate to home page after successful addition
        this.router.navigate([""]);
      });
    }
  }

  // Getter function to access form controls easily
  get f() {
    return this.addForm.controls;
  }
}
