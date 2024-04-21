/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This component defines the root component of the Angular application
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */

// Import the Component decorator from Angular core
import { Component } from '@angular/core';

// Define the component metadata, such as the Selector, Template URL, and Style URLs for the component
@Component({
  
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

// Define the component class
export class AppComponent {
  // Define a property for the component title
  title = 'Stephen Traveler Website';
}

