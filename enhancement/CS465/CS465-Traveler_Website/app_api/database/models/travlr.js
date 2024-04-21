/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This defines a Mongoose schema for trips, 
 * including fields like code, name, length, start date, resort, 
 * perPerson, image, and description.
 * 
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */

// Import Mongoose library for MongoDB
const mongoose = require('mongoose'); 

// Define the trip schema such as trip code, name, length, date, cost, and trip description
const tripSchema = new mongoose.Schema({
    code: { type: String, required: true, index: true }, 
    name: { type: String, required: true, index: true }, 
    length: { type: String, required: true }, 
    start: { type: Date, required: true }, 
    resort: { type: String, required: true }, 
    perPerson: { type: String, required: true }, 
    image: { type: String, required: true }, 
    description: { type: String, required: true } 
});

// Export the mongoose model for trips
// Define "trips" model using the tripSchema
module.exports = mongoose.model("trips", tripSchema); 