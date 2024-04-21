/*Programmer: Stephen Owusu-Agyekum
* Date: 2024-04-16
* Version: 6.13.4
* Description: This Angular component (HomeComponent) represents the home page of the application
* Course: CS465-Full Stack Development I
* School name: Southern New Hampshire University
*/

import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from "../services/authentication.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
// Component for the home page
export class HomeComponent implements OnInit {
  // Constructor function initializing the HomeComponent with AuthenticationService injected
  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
    // Lifecycle hook called after component initialization
  }

  // Method to check if the user is logged in
  public isLoggedIn(): boolean {
    // Return authentication status from AuthenticationService
    return this.authService.isLoggedIn(); 
  }
}
