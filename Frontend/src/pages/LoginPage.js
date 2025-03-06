import React from 'react';
import { useNavigate } from 'react-router-dom';
import LoginForm from '../components/LoginForm';
import userApi from '../api/userApi';
import {jwtDecode} from "jwt-decode";

function LoginPage() {
    const navigate = useNavigate();

    const handleLogin = async (username, password) => {
        try {
            const response = await userApi.post('/login', {
                id: 0,
                name: username,
                password: password,
                role: "user"
            });



            const token = response.data;
            console.log('Received token:', token);
            localStorage.setItem('token', token);


            const decoded = jwtDecode(token);
            console.log('Decoded token:', decoded);
            localStorage.setItem('userId', decoded.userId);
            localStorage.setItem('role', decoded.role);



            navigate('/');
        } catch (error) {
            console.error('Login failed', error);
            alert('Invalid credentials');
        }
    };

    return (
        <div>
            <LoginForm onLogin={handleLogin} />
        </div>
    );
}

export default LoginPage;