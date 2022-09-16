const apply_scheme = require('../../model/apply');

const display_placement_applications = (req, res) => {
    
    if (req.admin) {

        apply_scheme.find({}, (error, response) => {

            if (error) {

                return res.status(400).send({ "message": "Something Error at our end" })
            }
            if (!response) {
                return res.status(400).send({ "message": "No placement applications Found" })

            }

            return res.send(response)
        });
    } else {

        return res.status(400).send({ "message": "Please login as admin" })
    }


}

module.exports = display_placement_applications;