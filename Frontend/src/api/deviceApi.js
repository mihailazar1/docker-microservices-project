import axios from 'axios';

const deviceApi = axios.create({
    baseURL: '/device',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default deviceApi;