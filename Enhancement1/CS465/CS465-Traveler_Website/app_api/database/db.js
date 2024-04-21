/*
 * Programmer: Stephen Owusu-Agyekum
 * Date: 2024-04-16
 * Version: 6.13.4
 * Description: This establishes a connection to a MongoDB database using Mongoose
 * Course: CS465-Full Stack Development I
 * School name: Southern New Hampshire University
 */


// Import the Mongoose library
const mongoose = require('mongoose');
// Retrieve the database host from environment variables or use default
const host = process.env.DB_HOST || '127.0.0.1';
// Construct the MongoDB URI
const dbURI = `mongodb://${host}/travlr`;
// Import the 'readline' module for command-line interface
const readLine = require('readline');

// Avoid the warning: 'current Server Discovery and Monitoring engine is deprecated'
mongoose.set('useUnifiedTopology', true);

// Function to connect to the MongoDB database with a delay
const connect = () => {
    setTimeout(() => mongoose.connect(dbURI, {
        useNewUrlParser: true, 
        useCreateIndex: true, 
        // Wait 1 second before connecting
    }), 1000); 
}

// Event listeners for Mongoose connection events
mongoose.connection.on('connected', () => {
    // When connected to the database
    console.log('Mongoose connected to ' + dbURI);
});

mongoose.connection.on('error', err => {
    // When there is an error connecting to the database
    console.log('Mongoose connection error: ' + err);
});

mongoose.connection.on('disconnected', () => {
    // When disconnected from the database
    console.log('Mongoose disconnected');
});

// If running on Windows platform
if (process.platform === 'win32') {
    // Create an interface to read from the command line
    const rl = readLine.createInterface({
        input: process.stdin,
        output: process.stdout
    });
    // Close the readline interface when the app is terminated
    rl.on('SIGINT', () => {
        process.emit('SIGINT');
    });
}

// Function to perform graceful shutdown
const gracefulShutdown = (msg, callback) => {
    // Close Mongoose connection
    mongoose.connection.close(() => {
        // Log the message when the connection is closed
        console.log('Mongoose disconnected through ' + msg);
        // Call the callback function
        callback();
    });
};

// For nodemon restarts
process.once('SIGUSR2', () => {
    // Perform graceful shutdown when nodemon restarts
    gracefulShutdown('nodemon restart', () => {
        // Exit the process with code 0
        process.kill(process.pid, 'SIGUSR2');
    });
});

// For app termination
process.on('SIGINT', () => {
    // Perform graceful shutdown when the app is terminated
    gracefulShutdown('app termination', () => {
        // Exit the process with code 0
        process.exit(0);
    });
});

// For Heroku app termination
process.on('SIGTERM', () => {
    // Perform graceful shutdown when the app is terminated on Heroku
    gracefulShutdown('Heroku app termination', () => {
        // Exit the process with code 0
        process.exit(0);
    });
});

// Connect to the MongoDB database
connect();

// Bring in the Mongoose schemas
// Import the schema for 'user' and 'travlr' collection
require('./models/travlr'); 
require('./models/user');  