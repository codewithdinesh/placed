
const mongoose = require('mongoose');
const profile_scheme = require('../../model/profile_data');
const display_profile = (req, res) => {

    let application_id = req.query.id;

    if (!mongoose.isValidObjectId(application_id)) {

        return res.status(400).send({ "message": "Invalid profile Id" })

    };

    if (req.admin) {

        profile_scheme.findOne({ user_id: application_id }, (error, response) => {

            if (error) {

                return res.status(400).send({ "message": "Something Error at our end" })
            }
            if (!response) {
                return res.status(400).send({ "message": "Application not found" })

            }

            return res.send(response)
        });
    } else {

        return res.status(400).send({ "message": "Please login as admin" })
    }


}

module.exports = display_profile;