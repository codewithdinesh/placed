const mongoose = require("mongoose");

const PlacementSchema = new mongoose.Schema({

    description: {
        type: String,
        required: [true, "password required"],
    },
    name: {
        type: String,
        required: [true, "name required"],
    },
    organisation: {
        type: String
    },
    organisation_type: {
        type: String
    },

    organisation_desc: {
        type: String
    },

    location: {
        type: String
    },

    skills:
    {
        type: String
    },

    education: {
        type: String,
    },

    role: {
        type: String,
    },

    experience: {
        type: String
    },

    hr: {
        type: String
    },
    placement_added_date: {
        type: String,
        default: new Date().toLocaleString()
    }
});

const User = mongoose.model("Placement", PlacementSchema);
module.exports = User;