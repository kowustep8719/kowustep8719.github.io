/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This module defines the main module of the Angular application, 
 * including the root components, routing configuration, and dependencies
 * 
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */

// Import BrowserModule for running the application in a browser
import { BrowserModule } from "@angular/platform-browser"; 
// Import NgModule for defining Angular modules
import { NgModule } from "@angular/core"; 
// Import HttpModule for making HTTP requests
import { HttpModule } from "@angular/http"; 
// Import FormsModule and ReactiveFormsModule for handling forms
import { FormsModule, ReactiveFormsModule } from "@angular/forms"; 

// Import the root components
import { AppComponent } from "./app.component"; 
import { AppRoutingModule } from "./app-router.module";
import { TripListingComponent } from "./trip-listing/trip-listing.component"; 
import { TripCardComponent } from "./trip-card/trip-card.component"; 
import { TripDataService } from "./services/trip-data.service"; 
import { AddTripComponent } from "./add-trip/add-trip.component"; 
import { EditTripComponent } from './edit-trip/edit-trip.component'; 
import { NavbarComponent } from './navbar/navbar.component'; 
import { LoginComponent } from './login/login.component'; 
import { HomeComponent } from './home/home.component'; 

// Declaration of the various Components and configuration of Angular module
@NgModule({
  declarations: [
    AppComponent, 
    TripListingComponent, 
    TripCardComponent, 
    AddTripComponent, 
    EditTripComponent, 
    NavbarComponent, 
    LoginComponent, 
    HomeComponent, 
  ],
  imports: [
    // BrowserModule for running the application in a browser
    BrowserModule,
    // HttpModule for making HTTP requests 
    HttpModule, 
    // ReactiveFormsModule for handling reactive forms
    ReactiveFormsModule, 
    // AppRoutingModule for routing configuration
    AppRoutingModule, 
    // FormsModule for handling template-driven forms
    FormsModule 
  ],
  // Provide TripDataService for dependency injection
  providers: [TripDataService],
  // Bootstrap AppComponent as the root component of the application 
  bootstrap: [AppComponent], 
})
// AppModule class for defining the main module of the application
export class AppModule {}

