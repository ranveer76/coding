const mongoose = require('mongoose');

const connect = async () => {
    mongoose
        .connect('mongodb://127.0.0.1:27017', {
            useNewUrlParser: true,
            useUnifiedTopology: true,
            connectTimeoutMS: 30000,
            serverSelectionTimeoutMS: 30000,
        })
        .then(() => console.log('MongoDB connected'))
        .catch((err) => console.error('MongoDB connection error:', err));
};

module.exports = { connect };
