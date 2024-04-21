/* Programmer: Stephen Owusu-Agyekum
* Date: 2024-04-16
* Version: 6.13.4
* Description: This Angular service, AuthenticationService, provides functionality for user authentication and registration.
* Course: CS465-Full Stack Development I
* School name: Southern New Hampshire University
*/

import { Inject, Injectable } from '@angular/core';
import { BROWSER_STORAGE } from '../storage';
import { User } from '../models/user';
import { AuthResponse } from '../models/authresponse';
import { TripDataService } from '../services/trip-data.service';

// Provides the service at the root level of the application
@Injectable({
    providedIn: 'root' 
})

export class AuthenticationService {

constructor(
    // Injects the browser storage
    @Inject(BROWSER_STORAGE) private storage: Storage,
    // Dependency injection for TripDataService 
    private tripDataService: TripDataService 
) { }

// Retrieves the token from browser storage
public getToken(): string {
    return this.storage.getItem('travlr-token');
}

// Saves the token to browser storage
public saveToken(token: string): void {
    this.storage.setItem('travlr-token', token);
}

// Performs user login
public login(user: User): Promise<any> {
// Calls the login method of TripDataService and saves the token
    return this.tripDataService.login(user)
        .then((authResp: AuthResponse) => this.saveToken(authResp.token));
}

// Performs user registration
public register(user: User): Promise<any> {
    // Calls the register method of TripDataService and saves the token
    return this.tripDataService.register(user)
        .then((authResp: AuthResponse) => this.saveToken(authResp.token));
}

// Logs out the user by removing the token from browser storage
public logout(): void {
    this.storage.removeItem('travlr-token');
}

// Checks if the user is logged in by verifying token expiration
public isLoggedIn(): boolean {
    const token: string = this.getToken();
    if (token) {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.exp > (Date.now() / 1000);
    } else {
        return false;
    }
}

// Retrieves the current user information from the token
public getCurrentUser(): User {
    if (this.isLoggedIn()) {
        const token: string = this.getToken();
        const { email, name } = JSON.parse(atob(token.split('.')[1]));
        return { email, name } as User;
    }
}
}
