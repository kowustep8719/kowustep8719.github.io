/*Programmer: Stephen Owusu-Agyekum
* Date: 2024-04-16
* Version: 6.13.4
* Description: This Angular component (LoginComponent) is responsible for handling user login functionality
* Course: CS465-Full Stack Development I
* School name: Southern New Hampshire University
*/

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { User } from '../models/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
// Component for user login functionality
export class LoginComponent implements OnInit {
  // Variable to store form error message
  public formError: string = '';
  // Object to store user credentials
  public credentials = {
    name: '',
    email: '',
    password: ''
  };

  constructor(
    // Dependency injection for Router
    private router: Router,
    // Dependency injection for AuthenticationService
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit() {
    // Lifecycle hook called after component initialization
  }

  // Method to clear previous form error message
  public onLoginSubmit(): void {
    // Clear previous form error message
    this.formError = ''; 
    // Check if email and password are provided
    if (!this.credentials.email || !this.credentials.password) {
      // Set form error message
      this.formError = 'All fields are required, please try again'; 
    } else {
      // Call method to initiate login process
      this.doLogin(); 
    }
  }

  // Method to initiate the login process
  private doLogin(): void {
    // Call AuthenticationService's login method with provided credentials
    this.authenticationService.login(this.credentials)
    // Redirect to home page on successful login and set form error message on login failure
      .then(() => this.router.navigateByUrl('#')) 
      .catch((message) => this.formError = message); 
  }
}
