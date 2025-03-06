import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import Dashboard from './pages/Dashboard';
import UserManagement from './pages/UserManagement';
import DeviceManagement from './pages/DeviceManagement';
import MyDevices from './pages/MyDevices';
import PrivateRoute from './components/PrivateRoute';
import AuthWrapper from './components/AuthWrapper';
import Layout from './components/Layout';

function App() {
    return (
        <Router>
            <AuthWrapper>
                <Routes>
                    {/* Login Page route without Navbar */}
                    <Route path="/login" element={<LoginPage />} />

                    {/* Layout for all authenticated pages */}
                    <Route element={<Layout />}>
                        <Route
                            path="/"
                            element={<PrivateRoute allowedRoles={['admin', 'client']}><Dashboard /></PrivateRoute>}
                        />
                        <Route
                            path="/users"
                            element={<PrivateRoute allowedRoles={['admin']}><UserManagement /></PrivateRoute>}
                        />
                        <Route
                            path="/devices"
                            element={<PrivateRoute allowedRoles={['admin']}><DeviceManagement /></PrivateRoute>}
                        />
                        <Route
                            path="/my-devices"
                            element={<PrivateRoute allowedRoles={['client']}><MyDevices /></PrivateRoute>}
                        />
                    </Route>
                </Routes>
            </AuthWrapper>
        </Router>
    );
}

export default App;
