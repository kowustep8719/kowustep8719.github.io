/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This code configures Passport for authentication using a local strategy and Mongoose for MongoDB.
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */

// Import required modules such as Passport for authentication and Mongoose for MongoDB
// Import LocalStrategy for local authentication
const passport = require("passport"); 
const LocalStrategy = require("passport-local").Strategy; 
const mongoose = require("mongoose");  
const User = mongoose.model("users"); 

// Configure Passport to use local strategy
passport.use(
  new LocalStrategy(
    {
      // Set the field name for username as email
      usernameField: "email", 
    },
    // Local strategy callback function
    (username, password, done) => {
      // Find user by email
      User.findOne({ email: username }, (err, user) => {
        if (err) {
          // If error occurs during database query, return the error
          return done(err);
        }
        if (!user) {
          // If user not found, return error message
          return done(null, false, {
            message: "Incorrect username.",
          });
        }
        if (!user.validPassword(password)) {
          // If password is incorrect, return error message
          return done(null, false, {
            message: "Incorrect password.",
          });
        }
        // If user and password are correct, return authenticated user
        return done(null, user);
      });
    }
  )
);
