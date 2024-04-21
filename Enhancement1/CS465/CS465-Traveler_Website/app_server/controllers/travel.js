/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This defines a controller that handles requests related to travel data. 
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */


// Import the 'request' library for making HTTP requests
const request = require('request');

// Define options for API requests
const apiOptions = {
    // Assuming API server runs locally on port 3000
    server: 'http://localhost:3000' 
};

// Define a function to render the travel list view
const renderTravelList = (req, res, responseBody) => {
    // Initialize message variable
    let message = null;
    // Set the page title
    let pageTitle = process.env.npm_package_description + " Travel";

    // Check if responseBody is not an array
    if (!(responseBody instanceof Array)) {
        // Set error message
        message = "API lookup error";
        // Initialize responseBody as an empty array
        responseBody = [];
    } else {
        // Check if responseBody is empty
        if (!responseBody.length) {
            // Set message for empty list
            message = "No trips exist in database";
        }
    }

    // Render the travel view with title, trips, and message
    res.render("travel", {
        title: pageTitle,
        trips: responseBody,
        message,
    });
};

// Define a function to handle GET request for traveler view
const travelList = (req, res) => {
    // Define the API endpoint path
    const path = "/api/trips";
    // Define options for the request to the API
    const requestOptions = {
        // Set the request URL
        url: `${apiOptions.server}${path}`,
        // Set the HTTP method to GET
        method: "GET",
        // Set the request body to JSON
        json: {},
    };

    // Log the request URL for debugging
    console.info(" >> travelcontroller.travelList calling" + requestOptions.url);
    // Make a request to the API
    request(requestOptions, (err, { statusCode }, body) => {
        // Check for errors
        if (err) {
            // Log the error
            console.error(err);
        }
        // Call the renderTravelList function to render the travel list view
        renderTravelList(req, res, body);
    });
};

// Export the travelList function
module.exports = {
    travelList,
};
