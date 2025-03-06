import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ChatBox from '../components/ChatBox';

function Dashboard() {
    const navigate = useNavigate();
    const userId = localStorage.getItem('userId');
    const role = localStorage.getItem('role');

    const handleLogout = () => {
        localStorage.clear();
        navigate('/login');
    };

    return (
        <div>
            <h1>Dashboard</h1>
            <button onClick={handleLogout}>Logout</button>
            <ChatBox userId={userId} role={role} /> {/* Add ChatBox */}
        </div>
    );
}

export default Dashboard;
