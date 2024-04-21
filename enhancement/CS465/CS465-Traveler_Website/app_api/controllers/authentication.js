/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This defines methods for user registration and authentication using Passport and Mongoose.
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */

// Import required modules such as Passport for authentication, Mongoose for MongoDB, and the user model
const passport = require("passport");  
const mongoose = require("mongoose");  
const User = mongoose.model("users"); 

// Method to register a new user
const register = (req, res) => {
  // Check if all fields are provided
  if (!req.body.name || !req.body.email || !req.body.password) {
    // If any required field is missing, return 400 error
    return res.status(400).json({ message: "All fields required" });
  }
  // Create a new user instance
  const user = new User();
  user.name = req.body.name;
  user.email = req.body.email;
  // Set password using setPassword method
  user.setPassword(req.body.password); 
  // Save the user to the database
  user.save((err) => {
    if (err) {
      // If error saving user, return 400 error
      res.status(400).json(err);
    } else {
      // If user saved successfully, generate JWT token for the user and return it
      const token = user.generateJwt();
      res.status(200).json({ token });
    }
  });
};

// Method to authenticate a user login
const login = (req, res) => {
  // Check if email and password are provided
  if (!req.body.email || !req.body.password) {
    // If email or password is missing, return 400 error
    return res.status(400).json({ message: "All fields required" });
  }
  // Use Passport to authenticate the user
  passport.authenticate("local", (err, user, info) => {
    if (err) {
      // If error during authentication, return 404 error
      return res.status(404).json(err);
    }
    if (user) {
      // If user authenticated successfully, generate JWT token for the user and return it
      const token = user.generateJwt();
      res.status(200).json({ token });
    } else {
      // If authentication failed, return 401 error
      res.status(401).json(info);
    }
    // Call Passport's authentication middleware with request and response objects
  })(req, res); 
};

// Export the methods
module.exports = {
  register,
  login,
};
