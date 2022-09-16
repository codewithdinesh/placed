const profile = require('../../model/profile_data');

const dashboard = (req, res) => {

    if (req.user) {

        profile.findOne({ user_id: req.user }, (error, response) => {

            if (error) {

                return res.status(400).send({ "message": "Something Error at our end" })
            }
            if (!response) {

                return res.status(400).send({ "message": "Profile not created , please create profile" })

            }

            return res.send(response);

        });

    } else {

        return res.status(400).send({ "message": "Please login" })
    }

}

module.exports = dashboard;

