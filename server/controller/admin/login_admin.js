
const mongoose = require('mongoose');
const connConnect = require("../../config/db").conConnect;
const userModel = require('../../model/admin');
const bcrypt = require("bcrypt");
const jwt = require('jsonwebtoken');

const login_admin = (req, result) => {

    let email = req.body.email;
    let password = req.body.password;

    /* Check email enterred or not */
    if (!email) {
        return res.status(400).send(JSON.stringify({ "message": "Email is Required", "status": 400 }));

    }

    /* Check password is enterred or not */
    if (!password) {
        return res.status(400).send(JSON.stringify({ "message": "Password is Required", "status": 400 }));


    } else {

        userModel.findOne({ email: email }, function (err, user) {
            if (err) return err;
            if (!user) return result.status(401).send({ "status": "user not found", "email": email });

            bcrypt.compare(password, user.password, function (err, res) {
                if (err) return err;
                if (res === false) {
                    return result.status(401).send({ "status": "password not match", "email": email })
                }

                // Create token
                const token = jwt.sign(
                    {
                        admin_id: user._id
                    },
                    process.env.SECRET_KEY,
                    {
                        expiresIn: Math.floor(Date.now() / 1000) + (60 * 60)
                    }
                );

                // save user token
                userModel.findOneAndUpdate({ _id: user._id }, {
                    token: token
                },
                    { new: true }
                ).exec();
                let options = {
                    path: "/",
                    sameSite: true,
                    maxAge: 1000 * 60 * 60 * 24, // would expire after 24 hours
                    httpOnly: true,
                }

                result.cookie('token_id', token, options);
                result.status(200).send({ "status": "login success", "login_token": token, "email": email, "userId": user._id });
            });

        });


    }

}

module.exports = login_admin;