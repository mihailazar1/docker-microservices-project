// src/pages/MyDevices.js
import React, { useEffect, useState } from 'react';
import deviceApi from '../api/deviceApi';

const MyDevices = () => {
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDevices = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await deviceApi.get('', {
                    headers: { Authorization: `Bearer ${token}` },
                });
                setDevices(response.data);
            } catch (error) {
                console.error('Failed to fetch devices:', error);
                setError('Failed to load devices. Please try again later.');
            } finally {
                setLoading(false);
            }
        };
        fetchDevices();
    }, []);

    if (loading) return <div>Loading your devices...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div>
            <h2>My Devices</h2>
            <ul>
                {devices.map((device) => (
                    <li key={device.id}>
                        {device.description} - Max Consumption: {device.consumption} kWh
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default MyDevices;
