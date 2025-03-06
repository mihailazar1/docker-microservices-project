import React from 'react';
import Navbar from './Navbar';
import { Outlet } from 'react-router-dom';

function Layout() {
    return (
        <div>
            <Navbar />
            <Outlet /> {/* Renders child components based on the route */}
        </div>
    );
}

export default Layout;
