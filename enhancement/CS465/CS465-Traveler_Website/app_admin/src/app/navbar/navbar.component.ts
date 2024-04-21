/* Programmer: Stephen Owusu-Agyekum
* Date: 2024-04-16
* Version: 6.13.4
* Description: This Angular component, NavbarComponent, is responsible for displaying the navigation bar in the application
* Course: CS465-Full Stack Development I
* School name: Southern New Hampshire University
*/

import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  // Define the selector, template file, and CSS styles for the component
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
// Component for displaying the navigation bar
export class NavbarComponent implements OnInit {

  constructor(
    // Dependency injection for AuthenticationService
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit() {
    // Lifecycle hook called after component initialization
  }

  // Method to check if the user is logged in
  public isLoggedIn(): boolean {
    // Return authentication status from AuthenticationService
    return this.authenticationService.isLoggedIn(); 
  }

  // Method to handle user logout
  private onLogout(): void {
    // Call logout method from AuthenticationService
    return this.authenticationService.logout(); 
  }
}
