const admin = require("../../model/admin");
const placements=require("../../model/placement");



const admin_Details = (req, res) => {

    if (req.admin) {


        admin.findOne({ _id: req.admin }, (err, respone) => {
            if (err) {
                res.status(404).send({ "message": "Error While Fetching Account" })
            }
            if (!respone) {
                res.status(401).send({ "message": "Admin Not Found" })
            }

            res.status(200).send(respone);
        })

    } else {
        res.send({ "message": "Please Login as admin" });
    }
}

module.exports = admin_Details;