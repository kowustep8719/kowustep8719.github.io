
// Load environment variables from a .env file
require("dotenv").config();


// Import the required modules for setting up the Express.js application
// This section imports necessary modules to configure an Express.js application.
//  We're using Express.js as the framework for our backend due to its simplicity and robust features.
var createError = require("http-errors");
var express = require("express"); 
var path = require("path"); 
var cookieParser = require("cookie-parser"); 
var logger = require("morgan"); 
const hbs = require("hbs"); 
const passport = require("passport"); 

// Connect to the MongoDB database
require("./app_api/database/db");

// Configure Passport for authentication
require("./app_api/config/passport");

// Import routes for the server-side rendering
var indexRouter = require("./app_server/routes/index"); 
var usersRouter = require("./app_server/routes/users"); 
var travelRouter = require("./app_server/routes/travel"); 

// Import API routes for the RESTful API
const apiRouter = require("./app_api/routes/index"); // Main API router

// Create the Express application
var app = express();

// Set up the view engine and views directory
app.set("views", path.join(__dirname, "app_server", "views")); 
// Register partials directory for reusable templates
hbs.registerPartials(path.join(__dirname, "app_server", "views/partials")); 
app.set("view engine", "hbs"); 

// Set up middleware for request logging, parsing JSON, parsing URL-encoded bodies, cookie parsing,
// serving static files, and initializing Passport
app.use(logger("dev")); 
app.use(express.json()); 
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser()); 
app.use(express.static(path.join(__dirname, "public"))); 
app.use(passport.initialize()); 

// Enable CORS for API routes
app.use("/api", (req, res, next) => {
  // Allow requests from this origin
  res.header("Access-Control-Allow-Origin", "http://localhost:4200"); 
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept, Authorization"
  ); // Allow specific headers
  // Allow specific HTTP methods
  res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE"); 
  next();
});

// Mount routes for server-side rendering like index, user and travel route
app.use("/", indexRouter); 
app.use("/users", usersRouter); 
app.use("/travel", travelRouter); 

// Mount API routes
app.use("/api", apiRouter); 

// Error handling middleware for handling UnauthorizedError
app.use((err, req, res, next) => {
  if (err.name === "UnauthorizedError") {
    res.status(401).json({ message: err.name + ": " + err.message });
  }
});

// Error handling middleware for handling 404 errors
// Forward to the next error-handling middleware with a 404 error
app.use(function (req, res, next) {
  next(createError(404)); 
});

// Error handling middleware for rendering error pages
app.use(function (err, req, res, next) {
  // Set the error message in local variables
  res.locals.message = err.message; 
  // Set the error object in local variables based on the environment
  res.locals.error = req.app.get("env") === "development" ? err : {}; 
  // Set the HTTP status code to the error status or 500 (Internal Server Error)
  res.status(err.status || 500); 
  // Render the error page
  res.render("error"); 
});

// Export the Express application
module.exports = app;
