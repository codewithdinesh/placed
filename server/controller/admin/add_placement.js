const admin = require('../../model/admin');
const placement_scheme = require('../../model/placement');

const add_placement = (req, res) => {

    if (req.admin) {


        var name = req.body.name;

        var desc = req.body.desc;

        var org = req.body.org;

        var org_type = req.body.org_type;

        var org_desc = req.body.org_desc;

        var org_location = req.body.org_location;

        var skills = req.body.skills;

        var roles = req.body.roles;

        var education = req.body.education;

        var experience = req.body.experience;

        var hr = req.body.hr;

        if (!name) {
            return res.status(400).send(JSON.stringify({ "message": "Name is Required", "status": 400 }));
        }

        if (!desc) {
            return res.status(400).send(JSON.stringify({ "message": "Placement desc is Required", "status": 400 }));
        }

        if (!org) {
            return res.status(400).send(JSON.stringify({ "message": "org is Required", "status": 400 }));
        }

        if (!org_desc) {
            return res.status(400).send(JSON.stringify({ "message": "org desc is Required", "status": 400 }));
        }

        if (!org_type) {
            return res.status(400).send(JSON.stringify({ "message": "Org type is Required", "status": 400 }));
        }

        if (!org_location) {
            return res.status(400).send(JSON.stringify({ "message": "org location is Required", "status": 400 })); c
        }

        if (!skills) {

            return res.status(400).send(JSON.stringify({ "message": "skill is Required", "status": 400 }));

        }

        if (!roles) {
            return res.status(400).send(JSON.stringify({ "message": "placement role is Required", "status": 400 }));
        }

        if (!education) {
            return res.status(400).send(JSON.stringify({ "message": "Education is Required", "status": 400 }));
        }


        if (!hr) {
            return res.status(400).send(JSON.stringify({ "message": "HR is Required", "status": 400 }));
        }


        if (!experience) {
            return res.status(400).send(JSON.stringify({ "message": "experience is Required", "status": 400 }));
        }


        const newPlacement = new placement_scheme({
            name: name,
            description: desc,
            organisation: org,
            organisation_type: org_type,
            organisation_desc: org_desc,
            location: org_location,
            skills: skills,
            role: roles,
            education: education,
            experience: experience,
            hr: hr
        });

        newPlacement.save();

        admin.findOneAndUpdate({ _id: req.admin }, {
            $push: { 
                placements: newPlacement._id 
            }
        },
            { new: true }
        ).exec();




        return res.status(200).send({ "message": "successfully placement added", "Placement": newPlacement })

    } else {
        return res.status(404).send({ "message": "Admin not logged" })

    }

}

module.exports = add_placement;