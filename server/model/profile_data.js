const mongoose = require("mongoose");

const userProfileSchema = new mongoose.Schema({

    user_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'user'
    },

    placement: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'placement'
    },

    email: {
        type: String,
        required: [true, "email required"]
    },

    name: {
        type: String,
        required: [true, "name required"]
    },

    dob: {
        type: String,
        required: [true, "dob required"],

    },

    phone_no: {
        type: String,
        required: [true, "phone no required"],

    },

    address: {
        type: String,
        required: [true, "address required"],

    },

    skills: {
        type: String
    },

    experience: {
        type: String,

    },
    education: {
        education: {
            type: String,
            required: [true, "Education required"]
        },

        school: {
            type: String,
            required: [true, "School name required"],
        },

        school_cgpa: {
            type: String,
            required: [true, "School CGPA required"],
        },

        college: {
            type: String,
            required: [true, "School name required"],
        },


        college_cgpa: {
            type: String,
            required: [true, "School CGPA required"],
        }

    },

    interest: { type: String },

    profile_created: {
        type: String,
        default: new Date().toLocaleString()
    }

});

const User = mongoose.model("profile", userProfileSchema);
module.exports = User;