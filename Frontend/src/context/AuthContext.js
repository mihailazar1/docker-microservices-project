// src/context/AuthContext.js
import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [userRole, setUserRole] = useState(null);  // 'admin' or 'user'
    const [token, setToken] = useState(null);
    const [userId, setUserId] = useState(null);  // New state for user ID

    useEffect(() => {
        // Load token, role, and userId from localStorage if available
        const storedToken = localStorage.getItem('token');
        const storedRole = localStorage.getItem('role');
        const storedUserId = localStorage.getItem('userId'); // Load userId from localStorage

        if (storedToken && storedRole && storedUserId) {
            setToken(storedToken);
            setUserRole(storedRole);
            setUserId(storedUserId);
            setIsAuthenticated(true);
        }
    }, []);

    const login = (token, role, id) => {
        setToken(token);
        setUserRole(role);
        setUserId(id); // Set userId
        setIsAuthenticated(true);

        // Save token, role, and userId to localStorage
        localStorage.setItem('token', token);
        localStorage.setItem('role', role);
        localStorage.setItem('userId', id); // Save userId
    };

    const logout = () => {
        setToken(null);
        setUserRole(null);
        setUserId(null); // Clear userId
        setIsAuthenticated(false);

        // Clear token, role, and userId from localStorage
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('userId'); // Remove userId
    };

    return (
        <AuthContext.Provider value={{ isAuthenticated, userRole, token, userId, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};
