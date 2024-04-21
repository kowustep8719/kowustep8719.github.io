/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This code defines a router in an Express application. 
 * It imports the travelList function from the travel controller and sets up a route for the home page (/).
 * 
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */

// Import the Express module
var express = require('express');
// Create a router instance
var router = express.Router();
// Import the travelList function from the travel controller
const controller = require('../controllers/travel');

/* GET home page. */
// Define a route for the home page
// When a GET request is made to the root URL ('/'), invoke the travelList function from the travel controller
router.get('/', controller.travelList); 

// Export the router
module.exports = router;
