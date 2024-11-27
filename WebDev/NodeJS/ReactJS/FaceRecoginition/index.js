require('dotenv').config();
const express = require('express');
const app = express();
const port = process.env.PORT || 3000;
const host = process.env.HOST || 'localhost';

require('./server/configs/db').connect();

const faceapi = require('face-api.js');
const bodyparser = require('body-parser');
const cors = require('cors');
const cookieParser = require('cookie-parser');
const session = require('express-session');

app.use(
    bodyparser.json({
        limit: '50mb',
    })
);
app.use(bodyparser.urlencoded({ extended: true, limit: '50mb' }));
app.use(cookieParser());
app.use(cors());
app.use(
    session({
        secret: 'secret',
        resave: true,
        saveUninitialized: true,
    })
);
app.use(express.static(__dirname + '/public'));

app.use('/api', require('./server'));

(async () => {
    await Promise.all([
        faceapi.nets.ssdMobilenetv1.loadFromDisk('./public/models'),
        faceapi.nets.faceLandmark68Net.loadFromDisk('./public/models'),
        faceapi.nets.faceRecognitionNet.loadFromDisk('./public/models'),
    ]);
})();

app.listen(port, () => {
    console.clear();
    console.log(`Server running at http://${host}:${port}/`);
});
