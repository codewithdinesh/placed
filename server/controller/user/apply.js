const profile = require('../../model/profile_data');
const ApplySchema = require("../../model/apply");
const User = require("../../model/user");
const mongoose=require("mongoose")
const apply = (req, res) => {

    if (req.user) {

        let placement_id = req.body.placement_id;


        if (!placement_id) {
            return res.status(400).send({ "message": "Please mention Placement ID" })
        }

        if (!mongoose.isValidObjectId(placement_id)) {

            return res.status(400).send({ "message": "Invalid placement id" })

        };


        profile.findOne({ user_id: req.user }, (error, response) => {

            if (error) {
                return res.status(401).send({ "message": "error while fecthing the profile" });
            }

            if (!response) {

                return res.status(403).send({ "message": "could not found user profile data , please create profile and then apply" });

            }

            req.profile_id = response._id;

            if (req.profile_id) {

                ApplySchema.findOne({ placement: placement_id, profile: req.user }, (error, response) => {
                    if (error) {
                        res.status(400).send({ "message": "Error while fetching the applications" })
                    }

                    if (response) {
                        res.status(402).send({ "message": "your application is already applied" })
                    }

                    if (!response) {

                        const newApplication = new ApplySchema({
                            placement: placement_id,
                            profile: req.user
                        });

                        newApplication.save();

                        User.findOneAndUpdate({ _id: req.user }, {
                            $push: {
                                applications: newApplication._id
                            }
                        }, { new: true }).exec();


                        return res.status(200).send({ "message": "Your application submitted successfully" });

                    }
                })

            }

        });


    } else {
        return res.status(404).send({ "message": "Please Login to Apply" })
    }



}

module.exports = apply;