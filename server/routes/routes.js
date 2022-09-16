const express = require("express");
const app = express.Router();

// user
const user_login = require('../controller/user/login_user');
const user_signup = require('../controller/user/signup_user');
const Addprofile = require("../controller/user/profile");
const display_placements = require("../controller/user/display_placements");
const display_placement = require("../controller/user/display_placement");
const apply = require('../controller/user/apply');



// admin 
const admin_signup = require('../controller/admin/signup_admin');
const admin_login = require('../controller/admin/login_admin')
const add_placement = require('../controller/admin/add_placement');
const display_placement_applications = require("../controller/admin/display_placement_applications");
const display_placement_application = require("../controller/admin/display_placement_application");
const display_profile = require("../controller/admin/diplay_profile");

// middleware
const auth = require('../middleware/auth');
const dashboard = require("../controller/user/dashboard");
const profile_dashboard = require("../controller/user/profile_dashboard");
const admin_Details = require("../controller/admin/admin_details");


app.get('/welcome', (req, res) => {


    return res.send({ "message": "welcome" });


})

// user
app.post('/user/signup', user_signup);

app.post('/user/login', user_login);

app.post("/profile", auth, Addprofile);

app.get('/placements', display_placements);

app.get('/placement?:id', display_placement);

app.post('/apply', auth, apply);

app.get('/user', auth, dashboard);

app.get('/user/profile', auth, profile_dashboard);


// admin
app.post('/admin/signup', admin_signup);

app.post('/admin/login', admin_login);

app.post('/add_placement', auth, add_placement);

app.get('/placement/applications', auth, display_placement_applications);

app.get('/placement/application?:id', auth, display_placement_application);
app.get("/admin", auth, admin_Details);
app.get("/profile?:id", auth, display_profile);





module.exports = app;