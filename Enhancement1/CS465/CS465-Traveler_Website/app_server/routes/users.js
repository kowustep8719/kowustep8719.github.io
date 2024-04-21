/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This sets up a router in an Express application to handle routes related to users
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */

// Import the Express module
var express = require('express');
// Create a router instance
var router = express.Router();

/* GET users listing. */
// Define a route for handling GET requests to the root path '/users'
router.get('/', function(req, res, next) {
  // Send a response with the message 'respond with a resource'
  res.send('respond with a resource');
});

// Export the router
module.exports = router;

