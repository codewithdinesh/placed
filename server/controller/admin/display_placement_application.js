
const mongoose = require('mongoose');
const apply_scheme = require('../../model/profile_data');
const display_placement_application = (req, res) => {

    let application_id = req.query.id;

    if (!mongoose.isValidObjectId(application_id)) {

        return res.status(400).send({ "message": "Invalid Application Id" })

    };

    if (req.admin) {

        apply_scheme.findOne({ _id: application_id }, (error, response) => {

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

module.exports = display_placement_application;