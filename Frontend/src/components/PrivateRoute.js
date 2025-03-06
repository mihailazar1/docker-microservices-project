import React from 'react';
import { Navigate } from 'react-router-dom';

function PrivateRoute({ children, allowedRoles }) {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');

    if (!token) {
        return <Navigate to="/login" replace />; // Redirect if no token
    }

    if (allowedRoles && !allowedRoles.includes(role)) {
        return <Navigate to="/" replace />; // Redirect if role is unauthorized
    }

    return children;
}

export default PrivateRoute;
