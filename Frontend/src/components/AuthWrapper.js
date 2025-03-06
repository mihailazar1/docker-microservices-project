import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function AuthWrapper({ children }) {
    const navigate = useNavigate();
    const token = localStorage.getItem('token');

    useEffect(() => {
        if (!token) {
            navigate('/login'); // Redirect to login if token is missing
        }
    }, [navigate, token]);

    return <>{children}</>;
}

export default AuthWrapper;
