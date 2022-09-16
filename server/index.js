require("dotenv").config();

const express=require('express')
const app = express();
const router = require('./routes/routes')
var cookieParser = require('cookie-parser');


// Middlewares
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser())

app.use('/',router);

app.listen(8001,(req,res)=>{
    console.log("Server Started..")
})