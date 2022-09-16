const mongoose = require('mongoose');


const conCreate = require("../../config/db").conCreate;

const connConnect = require("../../config/db").conConnect;

const adminModel = require('../../model/admin');

const bcrypt = require("bcrypt");

const jwt = require('jsonwebtoken');


const signup_admin = async (req, res) => {
    var email = req.body.email;
    var pass = req.body.pass;
    var retype_pass = req.body.retype_pass;
    var name = req.body.name;


    if (!name) {

        return res.status(401).send({ "message": "Name Required", "status": 401, "email": email })
    }


    /* Check email enterred or not */
    if (!email) {
        return res.status(400).send(JSON.stringify({ "message": "Email is Required", "status": 400 }));

    }

    /* Check password is enterred or not */
    if (!pass) {
        return res.status(400).send(JSON.stringify({ "message": "Password is Required", "status": 400 }));

    }

    /* Check retype_pass */
    if (!retype_pass) {
        return res.status(400).send(JSON.stringify({ "message": "Retype Password  Required", "status": 400 }));

    }

    if (pass != retype_pass) {

        return res.status(401).send({ "message": "password does not match", "status": 401, "email": email })
    }



    const exists = await adminModel.exists({ email: email });

    if (exists) {

        return res.status(409).send({ "email": email, "status": 409, "message": "already having account" });

    } else {

        /* Encrypting password and storing user data to database*/

        bcrypt.genSalt(10, function (err, salt) {

            if (err) return next(err);
            bcrypt.hash(pass, salt, function (err, hash) {

                if (err) return next(err);

                const newUser = new adminModel({
                    email: email,
                    password: hash,
                    name: name
                });


                /* Create token */
                const token = jwt.sign(
                    {
                       admin_id: newUser._id
                    },
                    process.env.SECRET_KEY,
                    {
                        expiresIn: Math.floor(Date.now() / 1000) + (60 * 60)

                    }
                );

                /* save user token */
                newUser.token = token;
                newUser.save();

                let options = {
                    path: "/",
                    sameSite: true,
                    maxAge: 1000 * 60 * 60 * 24, // would expire after 24 hours
                    httpOnly: true,
                }

                res.cookie('token_id', token, options);
                return res.status(200).send({ "message": "account created successfully", "status": 200, "login_token": token, "email": email });

                // return res.redirect('/login');
                /*  next(); */
            });
        });


    }
}

module.exports = signup_admin;