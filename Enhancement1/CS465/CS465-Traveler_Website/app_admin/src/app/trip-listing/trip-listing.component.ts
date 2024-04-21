/* Programmer: Stephen Owusu-Agyekum
* Date: 2024-04-16
* Version: 6.13.4
* Description: This Angular component, TripListingComponent, is responsible for displaying a list of trips.
* Course: CS465-Full Stack Development I
* School name: Southern New Hampshire University
*/

import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { TripDataService } from '../services/trip-data.service';
import { Trip } from '../models/trip';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  // Define the selector, template file, CSS styles for the component
  selector: 'app-trip-listing', 
  templateUrl: './trip-listing.component.html', 
  styleUrls: ['./trip-listing.component.css'],
  // Provide the TripDataService at the component level
  providers: [TripDataService] 
})
// Component to display a list of trips
export class TripListingComponent implements OnInit {
  // Array to store trips
  trips: Trip[]; 
  // Message to display to the user
  message: string; 

  // Dependency injection for TripDataService, AuthenticationService, and router
  constructor(
    // Service to fetch trip data and handle authentication
    private tripDataService: TripDataService, 
    private authService: AuthenticationService,
    // Router for navigation
    private router: Router 
    ) { }

  // Method to navigate to the add trip page
  private addTrip(): void {
    console.log('Inside TripListingComponent#addTrip');
    // Navigate to the add trip page
    this.router.navigate(['add-trip']); 
  }

  // Method to fetch trips from the server
  private getTrips(): void {
    console.log('Inside TripListingComponent#getTrips');
    // Set message to indicate searching
    this.message = 'Searching for trips'; 
    // Call TripDataService's getTrips method to fetch trips
    this.tripDataService
      .getTrips()
      .then(foundTrips => {
        // Update message based on the number of found trips
        this.message = foundTrips.length > 0 ? '' : 'No trips found';
        // Assign fetched trips to the component variable
        this.trips = foundTrips;
      });
  }

  // Method to check if user is logged in
  public isLoggedIn(): boolean {
    // Return authentication status from AuthenticationService
    return this.authService.isLoggedIn(); 
  }

  // Call getTrips method when component initializes
  ngOnInit() {
    // Fetch trips when the component initializes
    this.getTrips(); 
  }
}


