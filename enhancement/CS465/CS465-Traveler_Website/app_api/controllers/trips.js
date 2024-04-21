/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This defines methods for CRUD operations on trips, 
 * including listing trips, finding a trip by code, adding a new trip, and updating a trip.
 * 
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */

// Destructure 'model' from mongoose for convenience
// Import Mongoose library for MongoDB
const { model } = require("mongoose"); 
const mongoose = require("mongoose"); 
const Model = mongoose.model("trips"); 
const user = mongoose.model("users"); 

// Async method to list trips
const tripsList = async (req, res) => {
  // Find all trips
  Model.find({}).exec((err, trips) => {
    if (!trips) {
      // If no trips found, return 404 error
      return res.status(404).json({ message: "Trips not found" });
    } else if (err) {
      // If error, return the error
      return res.status(404).json(err);
    } else {
      // If trips found, return the trips
      return res.status(200).json(trips);
    }
  });
};

// Async method to find a trip by its code
const tripsFindCode = async (req, res) => {
  // Find a trip by its code
  Model.find({ code: req.params.tripCode }).exec((err, trip) => {
    if (!trip) {
      // If trip not found, return 404 error
      return res.status(404).json({ message: "Trip not found" });
    } else if (err) {
      // If error, return the error
      return res.status(404).json(err);
    } else {
      // If trip found, return the trip
      return res.status(200).json(trip);
    }
  });
};

// Async method to update a trip
const tripsUpdateTrip = async (req, res) => {
  // Update a trip
  getUser(req, res, (req, res) => {
    Model.findOneAndUpdate(
       // Find the trip by its code
      { code: req.params.tripCode },
      {
        // Update trip details
        code: req.body.code,
        name: req.body.name,
        length: req.body.length,
        start: req.body.start,
        resort: req.body.resort,
        perPerson: req.body.perPerson,
        image: req.body.image,
        description: req.body.description,
      },
      // Return the updated trip
      { new: true } 
    )
      .then((trip) => {
        if (!trip) {
          // If trip not found, return 404 error
          return res.status(404).send({
            message: "Trip not found with code " + req.params.tripCode,
          });
        }
        // If trip updated successfully, return the updated trip
        res.send(trip);
      })
      .catch((err) => {
        if (err.kind === "ObjectId") {
          // If invalid trip code, return 404 error
          return res.status(404).send({
            message: "Trip not found with code " + req.params.tripCode,
          });
        }
        // If other error, return 500 error
        return res.status(500).json(err);
      });
  });
};

// Async method to add a new trip
const tripsAddTrip = async (req, res) => {
  // Add a new trip
  getUser(req, res, (req, res) => {
    Model.create(
      {
        // Create a new trip
        code: req.body.code,
        name: req.body.name,
        length: req.body.length,
        start: req.body.start,
        resort: req.body.resort,
        perPerson: req.body.perPerson,
        image: req.body.image,
        description: req.body.description,
      },
      (err, trip) => {
        if (err) {
          // If error, return 400 error
          return res.status(400).json(err);
        } else {
          // If trip added successfully, return 201 status and the new trip
          return res.status(201).json(trip);
        }
      }
    );
  });
};

// Method to get user details
const getUser = (req, res, callback) => {
  if (req.payload && req.payload.email) {
    // If user email exists in payload
    user.findOne({ email: req.payload.email }).exec((err, user) => {
      if (!user) {
        // If user not found, return 404 error
        return res.status(404).json({ message: "User not found" });
      } else if (err) {
        // If error, return the error
        console.log(err);
        return res.status(404).json(err);
      }
      // If user found, execute the callback function with user details
      callback(req, res, user.name);
    });
  } else {
    // If user email not found in payload, return 404 error
    return res.status(404).json({ message: "User not found" });
  }
};

// Export all methods
module.exports = {
  tripsList,
  tripsFindCode,
  tripsAddTrip,
  tripsUpdateTrip,
};
