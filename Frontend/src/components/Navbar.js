import React from 'react';
import '../Navbar.css';

import { Link, useNavigate } from 'react-router-dom';

function Navbar() {
    const navigate = useNavigate();
    const role = localStorage.getItem('role'); // Retrieve role from localStorage

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        navigate('/login');
    };

    return (
        <nav>
            <Link to="/">Dashboard</Link>
            {role === 'admin' && (
                <>
                    <Link to="/users">Manage Users</Link>
                    <Link to="/devices">Manage Devices</Link>
                </>
            )}
            {role === 'client' && (
                <Link to="/my-devices">Your Devices</Link>
            )}

        </nav>
    );
}

export default Navbar;
