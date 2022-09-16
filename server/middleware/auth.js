const admin = require("../model/admin")
const User = require("../model/user")
const jwt = require("jsonwebtoken");
const decode = require("jsonwebtoken/decode");

const auth = (req, res, next) => {

    const token = req.headers.login_token || req.body.token || req.cookies.token_id;

    if (!token) {
        return res.status(403).send({
            status: "Authentication is required",
            code: "403"
        });
    }

    try {
        const decoded = jwt.verify(token, process.env.SECRET_KEY);

        if (decoded.user_id) {
            //req.user = decoded
            User.findOne({ token: token }, (err, result) => {

                if (err)
                    return res.status(403).send({
                        status: "Authentication Error",
                        code: "403"
                    });


                if (!result) {
                    return res.status(403).send({
                        status: "Invalid Authentication",
                        code: "403"

                    });

                }
                req.user = decoded.user_id;

                return next()

            });
        }

        if (decoded.admin_id) {
            admin.findOne({ token: token }, (err, result) => {
                if (err)
                    return res.status(403).send({
                        status: "Authentication Error",
                        code: "403"
                    });


                if (!result) {
                    return res.status(403).send({
                        status: "Invalid Authentication",
                        code: "403"

                    });

                }

                req.admin = decoded.admin_id;
                return next();
            })
        }

    } catch (err) {
        return res.status(401).send({ message: "Invalid Token" });
    }


}

module.exports = auth;