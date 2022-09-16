const mongoose = require("mongoose");

const UserSchema = new mongoose.Schema({

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

    token: {
        type: String,
    },

    profile: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'profile'
    },
    applications:[{
        type: mongoose.Schema.Types.ObjectId,
        ref:'apply'
    }],
    user_created: {
        type: String,
        default: new Date().toLocaleString()
    }

});

const User = mongoose.model("user", UserSchema);
module.exports = User;