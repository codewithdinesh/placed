const mongoose = require("mongoose");

const AdminSchema = new mongoose.Schema({

    email: {
        type: String,
        unique: true,
        index: true,
        required: [true, "Email required"],
    },

    password: {
        type: String,
        required: [true, "password required"],
    },

    name: {
        type: String,
        required: [true, "name required"],
    },

    token: {
        type: String,
    },

    placements: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'placement'
    }],
    admin_created:{
        type: String,
        default: new Date().toLocaleString()
    }
});

const User = mongoose.model("Admin", AdminSchema);
module.exports = User;