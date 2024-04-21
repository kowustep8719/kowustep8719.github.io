/* Programmer: Stephen Owusu-Agyekum
* Date: 2024-04-16
* Version: 6.13.4
* Description: This Angular component, TripCardComponent, is responsible for displaying individual trip cards
* Course: CS465-Full Stack Development I
* School name: Southern New Hampshire University
*/

import { Component, OnInit, Input } from '@angular/core';
import { Router } from "@angular/router";
import { Trip } from '../models/trip';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  // Define the selector, template file, CSS styles for the component
  selector: 'app-trip-card', 
  templateUrl: './trip-card.component.html', 
  styleUrls: ['./trip-card.component.css'] 
})
// Component to display individual trip cards
export class TripCardComponent implements OnInit {
  // Input property to receive trip data from parent component
  @Input('trip') trip: Trip; 

  constructor(
    // Dependency injection for Router
    private router: Router,
    // Dependency injection for AuthenticationService
    private authService: AuthenticationService 
  ) { }
  // Lifecycle hook called after component initialization
  ngOnInit() {
    
  }

  // Method to navigate to the edit trip page and store trip code in local storage
  private editTrip(trip: Trip): void {
    // Remove any existing trip code from local storage
    localStorage.removeItem("tripCode"); 
    // Store the trip code in local storage
    localStorage.setItem("tripCode", trip.code); 
    // Navigate to the edit trip page
    this.router.navigate(['edit-trip']); 
  }

  // Method to check if user is logged in
  public isLoggedIn(): boolean {
    // Return authentication status from AuthenticationService
    return this.authService.isLoggedIn(); 
  }
}

