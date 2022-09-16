const mongoose = require("mongoose");

const ApplySchema = new mongoose.Schema({
    placement: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'placement'
    },
    profile: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'profile'
    },
    applied_on:{
        type: String,
        default: new Date().toLocaleString()
    }
});

const User = mongoose.model("Apply", ApplySchema);
module.exports = User;