/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This defines a Mongoose schema for users, including fields like email, name, hash, and salt.
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */

// Import Mongoose library for MongoDB, crypto library for cryptographic functions, and jsonwebtoken library for JWT token generation
const mongoose = require("mongoose"); 
const crypto = require("crypto");  
const jwt = require("jsonwebtoken"); 

// Define the user schema
const userSchema = new mongoose.Schema({
  email: {
    // Ensure email is required and should be unique
    type: String,
    unique: true, 
    required: true, 
  },
  name: {
    type: String,
    required: true, 
  },
  // Store password hash using salting
  hash: String, 
  salt: String,
});

// Method to set user's password
userSchema.methods.setPassword = function (password) {
  // Generate a random salt
  this.salt = crypto.randomBytes(16).toString("hex");
  // Hash the password using PBKDF2 with the generated salt
  this.hash = crypto
    .pbkdf2Sync(password, this.salt, 1000, 64, "sha512")
    .toString("hex");
};

// Method to validate user's password
userSchema.methods.validPassword = function (password) {
  // Compute hash of input password with stored salt
  const hash = crypto
    .pbkdf2Sync(password, this.salt, 1000, 64, "sha512")
    .toString("hex");
  // Compare computed hash with stored hash
  return this.hash === hash;
};

// Method to generate JWT token for user
userSchema.methods.generateJwt = function () {
  // Set expiry date for the token (7 days from now)
  const expiry = new Date();
  expiry.setDate(expiry.getDate() + 7);
  // Generate JWT token with user data and expiry
  return jwt.sign(
    {
      _id: this._id, 
      email: this.email, 
      name: this.name,
      exp: parseInt(expiry.getTime() / 1000, 10),
    },

    // Use JWT secret from environment variables
    // "DO NOT KEEP YOUR SECRET IN THE CODE!"
    process.env.JWT_SECRET 
  ); 
};

// Define "users" model using the userSchema
// Export the mongoose model for user
module.exports = mongoose.model("users", userSchema); 
