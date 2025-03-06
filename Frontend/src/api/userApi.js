import axios from 'axios';

const userApi = axios.create({
    baseURL: '/client',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default userApi;