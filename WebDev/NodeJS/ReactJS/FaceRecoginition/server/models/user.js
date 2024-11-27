const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const { Schema } = mongoose;
const faceapi = require('face-api.js');

const userSchema = new Schema({
    username: {
        type: String,
        required: true,
        unique: true,
    },
    name: {
        type: String,
        required: true,
    },
    password: {
        type: String,
        required: true,
    },
    role: {
        type: [String],
        default: ['user'],
        enum: ['user', 'admin', 'superadmin'],
    },
    face: {
        type: Object,
        default: null,
    },
}, { timestamps: true });

userSchema.pre('save', async function (next) {
    if (!this.isModified('password')) return next();
    try {
        const hash = await bcrypt.hash(this.password, 10);
        this.password = hash;
        next();
    } catch (err) {
        next(err);
    }
});

userSchema.methods.comparePassword = async function (password) {
    return await bcrypt.compare(password, this.password);
};

userSchema.methods.generateToken = function () {
    return jwt.sign({ id: this._id }, process.env.JWT_SECRET);
};

userSchema.statics.verifyToken = function (token) {
    return jwt.verify(token, process.env.JWT_SECRET);
};

userSchema.statics.authenticate = async function ({ username, password }) {
    try {
        const user = await this.findOne({ username });
        if (!user) throw new Error('User not found');
        
        const isValid = await user.comparePassword(password);
        if (!isValid) throw new Error('Invalid password');
        
        return user;
    } catch (error) {
        throw new Error('Authentication failed: ' + error.message);
    }
};


userSchema.statics.authorize = async function (token) {
    const { id } = this.verifyToken(token);
    return await this.findById(id);
};

userSchema.statics.register = async function (data) {
    return await this.create(data);
};

userSchema.statics.updateFace = async function (id, face) {
    const faceDescriptor = faceapi.FaceMatcher.fromJSON(face);
    return await this.findByIdAndUpdate(id, { face: faceDescriptor });
}

userSchema.statics.findFace = async function (face) {
    const users = await this.find();
    if (users.length === 0) {
        throw new Error('No users found for face recognition');
    }

    const labeledDescriptors = users.map((user) => {
        if (user.face) {
            try {
                return new faceapi.LabeledFaceDescriptors(
                    user.name,
                    [faceapi.Descriptor.fromJSON(user.face)]
                );
            } catch (err) {
                console.error(`Error deserializing face descriptor for user ${user.name}:`, err);
            }
        }
    }).filter(Boolean);

    if (labeledDescriptors.length === 0) {
        throw new Error('No valid face data available for matching');
    }

    const faceMatcher = new faceapi.FaceMatcher(labeledDescriptors);
    const bestMatch = faceMatcher.findBestMatch(face);

    return {
        label: bestMatch.label,
        distance: bestMatch.distance,
    };
};


exports.User = mongoose.model('User', userSchema);