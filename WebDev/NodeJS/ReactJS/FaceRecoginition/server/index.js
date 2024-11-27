const express = require('express');
const {User} = require('./models/user');
const router = express.Router();

router.get('/', (req, res) => {
    res.send('Hello World!');
});

router.post('/contact', (req, res) => {
    console.log(req.body);
    res.send('Message received!');
});

router.post('/login', (req, res) => {
    if (req.body.username === 'admin' && req.body.password === 'admin') {
        res.send({ token: '123456' });
    } else {
        res.status(401).send('Invalid credentials');
    }
});

router.post('/detect-face', async (req, res) => {
    try {
        const detections = req.body.faces;

        if (detections?.length === 0) {
            return res.status(404).send('No faces detected');
        }
        await User.register({ username: 'ranveerwalai76@gmail.com', password: '95RSW@76', name: 'Ranveer Singh Walia', face: detections[0].descriptor });
        res.send(detections);
    } catch (err) {
        console.error('Error detecting faces:', err);
        res.status(500).send('Internal server error');
    }
});

module.exports = router;
