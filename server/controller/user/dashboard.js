const User = require('../../model/user');
const profile = require('../../model/profile_data');
const application = require('../../model/apply');
const placement = require("../../model/placement");

const dashboard = (req, res) => {


    if (req.user) {

        // user
        User.findOne({ _id: req.user }, (error, response) => {

            if (error) {

                return res.status(500).send({ "message": "Something Error at our end" })
            }
            if (!response) {
                return res.status(404).send({ "message": "user not found" })
            }



            // profile
            if (response.profile) {

                profile.findOne({ user_id: req.user }, (error, response_profile) => {

                    if (error) {

                        return res.status(500).send({ "message": "Something Error at our end" })
                    }

                    if (!response_profile) {

                        return res.status(200).send(response);

                    }



                    // applications submited
                    if (response.applications) {

                        application.find({ profile: req.user }, async (err, application_response) => {

                            if (err) {

                                return res.status(500).send({ "message": "Something Error at our end" });

                            }
                            if (!application_response) {

                                return res.status(200).send({ "user": response, "profile": response_profile });

                            }



                            return res.status(200).send({ "user": response, "profile": response_profile, "applications": application_response });



                        })

                    } else {

                        return res.status(200).send({ "user": response, "profile": response_profile, "err": "err" });

                    }

                });

            } else {
                return res.status(200).send({"user": response});
            }

        });

    } else {

        return res.status(401).send({ "message": "Please login" })
    }

}

module.exports = dashboard;

