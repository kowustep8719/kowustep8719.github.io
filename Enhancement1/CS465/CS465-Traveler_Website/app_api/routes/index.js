/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This defines routes for user authentication and managing trips in an Express.js application
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */

// Import the Express module
const express = require("express");
// Create a router instance
const router = express.Router();

// Import the JSON Web Token (JWT) module for authentication
const jwt = require("express-jwt");
// Configure JWT authentication middleware
const auth = jwt({
  // Secret key for JWT encryption
  secret: process.env.JWT_SECRET,
  // Property name to attach decoded JWT payload to the request object 
  userProperty: "payload",
  // Specify the encryption algorithm for JWT 
  algorithms: ["HS256"], 
});

// Import authentication controller
const authController = require("../controllers/authentication");
// Import trips controller
const tripsController = require("../controllers/trips");

// Define routes for user authentication
// Route for user login and registration
router.route("/login").post(authController.login); 
router.route("/register").post(authController.register);

// Define routes for managing trips
router
  .route("/trips")
  // Route to get list of trips
  .get(tripsController.tripsList)
  // Route to add a new trip, requires authentication
  .post(auth, tripsController.tripsAddTrip); 

// Route to details and updates of a specific trip, requires authentication
router
  .route("/trips/:tripCode")
  .get(tripsController.tripsList)
  .put(auth, tripsController.tripsUpdateTrip);

// Route to find a trip by trip code
router.route("/trips/:tripCode").get(tripsController.tripsFindCode);

// Export the router
module.exports = router;
