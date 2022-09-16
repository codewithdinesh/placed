
const placement_scheme = require('../../model/placement');
const display_placement = (req, res) => {

    let sort_by_date = req.query.date;
    let placement_id = req.query.id;

    placement_scheme.find({}, (error, response) => {

        if (error) {

            return res.status(400).send({ "message": "Something Error at our end" })
        }
        if (!response) {
            return res.status(400).send({ "message": "No placement Found" })

        }

        return res.send(response)
    });


}

module.exports = display_placement;