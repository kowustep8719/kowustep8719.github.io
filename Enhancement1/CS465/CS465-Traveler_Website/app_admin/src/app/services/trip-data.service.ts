/* Programmer: Stephen Owusu-Agyekum
* Date: 2024-04-16
* Version: 6.13.4
* Description: This Angular service, TripDataService, facilitates communication with the backend API for managing trip data
* Course: CS465-Full Stack Development I
* School name: Southern New Hampshire University
*/

import { Injectable, Inject } from "@angular/core";
import { Http, Headers } from "@angular/http";

import { Trip } from "../models/trip";
import { BROWSER_STORAGE } from "../storage";
import { User } from "../models/user";
import { AuthResponse } from "../models/authresponse";

@Injectable()
export class TripDataService {

  constructor(
    // HTTP client for making requests
    private http: Http, 
    // Inject browser storage for storing authentication token
    @Inject(BROWSER_STORAGE) private storage: Storage 
  ) {}

  private apiBaseUrl = "http://localhost:3000/api/";
  private tripUrl = `${this.apiBaseUrl}trips/`;

  // Method to fetch all trips from the backend API
  public getTrips(): Promise<Trip[]> {
    console.log("Inside TripDataService#getTrips");
    return this.http
      // Catch and handle any errors
      .get(`${this.apiBaseUrl}trips`)
      .toPromise()
      .then((response) => response.json() as Trip[])
      .catch(this.handleError); 
  }

  // Method to fetch a single trip by its code from the backend API
  public getTrip(tripCode: string): Promise<Trip> {
    console.log("Inside TripDataService#getdTrip");
    return this.http
      // Catch and handle any errors
      .get(this.tripUrl + tripCode)
      .toPromise()
      .then((response) => response.json() as Trip)
      .catch(this.handleError); 
  }

  // Method to add a new trip via the backend API
  public addTrip(formData: Trip): Promise<Trip> {
    console.log("Inside TripDataService#addTrip");
    const headers = new Headers({
      "Content-Type": "application/json",
      // Attach authorization token from local storage
      Authorization: `Bearer ${localStorage.getItem("travlr-token")}`, 
    });
    return this.http
      // Catch and handle any errors
      .post(this.tripUrl, formData, { headers: headers })
      .toPromise()
      .then((response) => response.json() as Trip[])
      .catch(this.handleError); 
  }

  // Method to update an existing trip via the backend API
  public updateTrip(formData: Trip): Promise<Trip> {
    console.log("Inside TripDataService#updateTrip");
    const headers = new Headers({
      "Content-Type": "application/json",
      // Attach authorization token from local storage
      Authorization: `Bearer ${localStorage.getItem("travlr-token")}`, 
    });
    return this.http
      // Catch and handle any errors
      .put(this.tripUrl + formData.code, formData, { headers: headers })
      .toPromise()
      .then((response) => response.json() as Trip[])
      .catch(this.handleError); 
  }

  // Method to handle error responses from HTTP requests
  private handleError(error: any): Promise<any> {
    console.error("Something has gone wrong", error); 
    // Return the error message
    return Promise.reject(error.message || error); 
  }

  // Method to handle user login via the backend API
  public login(user: User): Promise<AuthResponse> {
    // Call makeAuthApiCall method for authentication
    return this.makeAuthApiCall("login", user); 
  }

  // Method to handle user registration via the backend API
  public register(user: User): Promise<AuthResponse> {
    // Call makeAuthApiCall method for registration
    return this.makeAuthApiCall("register", user); 
  }

  // Method to make authenticated API calls for user authentication
  // Catch and handle any errors
  private makeAuthApiCall(urlPath: string, user: User): Promise<AuthResponse> {
    const url: string = `${this.apiBaseUrl}/${urlPath}`;
    return this.http
      .post(url, user)
      .toPromise()
      .then((response) => response.json() as AuthResponse)
      .catch(this.handleError); 
  }
}
