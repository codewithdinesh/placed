
const profileSchema = require('../../model/profile_data');
const User = require('../../model/user');

const profile = async (req, res) => {


    if (req.user) {

        let email = req.body.email;
        let name = req.body.name;
        let dob = req.body.dob;
        let phone_no = req.body.phone_no;
        let address = req.body.address;
        let skills = req.body.skills;
        let experience = req.body.experience;
        let education = req.body.education;
        let interest = req.body.interest;

        let school = req.body.school;
        let school_cgpa = req.body.school_cgpa;
        let college = req.body.college;
        let college_cgpa = req.body.college_cgpa;
        // let placement=req.body.placement;

        if (!name) {
            return res.status(400).send(JSON.stringify({ "message": "Name is Required", "status": 400 }));
        }

        if (!email) {
            return res.status(400).send(JSON.stringify({ "message": "Email is Required", "status": 400 }));
        }

        if (!dob) {
            return res.status(400).send(JSON.stringify({ "message": "Dob is Required", "status": 400 }));
        }

        if (!phone_no) {
            return res.status(400).send(JSON.stringify({ "message": "Phone is Required", "status": 400 }));
        }

        if (!address) {
            return res.status(400).send(JSON.stringify({ "message": "address is Required", "status": 400 }));
        }

        if (!skills) {
            return res.status(400).send(JSON.stringify({ "message": "Skills is Required", "status": 400 }));
        }

        if (!experience) {
            return res.status(400).send(JSON.stringify({ "message": "Experience is Required", "status": 400 }));
        }

        if (!education) {
            return res.status(400).send(JSON.stringify({ "message": "Education is Required", "status": 400 }));
        }

        if (!school) {
            return res.status(400).send(JSON.stringify({ "message": "School Name is Required", "status": 400 }));
        }

        if (!school_cgpa) {
            return res.status(400).send(JSON.stringify({ "message": "School CGPA is Required", "status": 400 }));
        }

        if (!college) {
            return res.status(400).send(JSON.stringify({ "message": "College Name is Required", "status": 400 }));
        }

        if (!college_cgpa) {
            return res.status(400).send(JSON.stringify({ "message": "College CGPA Name is Required", "status": 400 }));
        }

        if (!interest) {
            return res.status(400).send(JSON.stringify({ "message": "interest Name is Required", "status": 400 }));
        }

    

        // if (!placement) {
        //     return res.status(400).send(JSON.stringify({ "message": "interest Name is Required", "status": 400 }));
        // }

        const exists = await profileSchema.exists({ email: email });
        const phone_exists = await profileSchema.exists({ phone_no: phone_no });
        const user_id_exists = await profileSchema.exists({ user_id: req.user });

        if (user_id_exists) {
            return res.status(401).send({ "message": "already exists profile" })
        }

        if (exists) {
            return res.status(401).send({ "message": "email  have already exists profile" })
        }

        if (phone_exists) {
            return res.status(401).send({ "message": "phone have already exists profile" })

        }

        const newProfile = new profileSchema({
            user_id: req.user,
            email: email,
            name: name,
            dob: dob,
            phone_no: phone_no,
            address: address,
            skills: skills,
            experience: experience,
            education:
            {
                education: education,
                school: school,
                school_cgpa: school_cgpa,
                college: college,
                college_cgpa: college_cgpa,
            },
            interest: interest
        });

        newProfile.save();


        User.findOneAndUpdate({ _id: req.user }, {
            profile: newProfile._id,
        },
            { new: true }
        ).exec();


        return res.status(200).send({ "message": "successfully profile added", "profile": newProfile })

    } else {

        return res.status(404).send({ "message": "User not logged" })
        // Only admin can add placements

    }

}

module.exports = profile;