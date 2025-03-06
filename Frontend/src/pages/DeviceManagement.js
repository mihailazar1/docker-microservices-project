import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import DeviceForm from '../components/DeviceForm';
import deviceApi from '../api/deviceApi';


function DeviceManagement() {
    const [devices, setDevices] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [currentDevice, setCurrentDevice] = useState(null);

    useEffect(() => {
        fetchDevices();
    }, []);

    const fetchDevices = async () => {
        const token = localStorage.getItem('token');
        const response = await deviceApi.get('', {headers: {
                Authorization: `Bearer ${token}`
            }});
        setDevices(response.data);
    };

    const handleAddDevice = async (device) => {
        try{
            const token = localStorage.getItem('token');
            await deviceApi.post('', device, {headers: {
                    Authorization: `Bearer ${token}`
                }});
            await fetchDevices();
            setShowModal(false);
        } catch (error) {

            console.error('Error adding device:', error);
        alert(`Failed to add device: ${error.response?.data?.message || error.message}`);
        alert(device.description + ", " + device.address + ", " + device.consumption);
    }
    };

    const handleEditDevice = async (device) => {

        try{
            const token = localStorage.getItem('token');
            await deviceApi.post(`/update/${currentDevice.id}`, device, {headers: {
                    Authorization: `Bearer ${token}`
                }});
            await fetchDevices();
            setShowModal(false);
            setCurrentDevice(null);

        } catch (error) {

            console.error('Error editing device:', error);
            alert(`Failed to edit device: ${error.response?.data?.message || error.message}`);
            alert(device.description + ", " + device.address + ", " + device.consumption);
        }

    };

    const handleDeleteDevice = async (deviceId) => {
        const token = localStorage.getItem('token');
        await deviceApi.delete(`/${deviceId}`, {headers: {
                Authorization: `Bearer ${token}`
            }});
        await fetchDevices();
    };

    const openAddModal = () => {
        setCurrentDevice(null);
        setShowModal(true);
    };

    const openEditModal = (device) => {
        setCurrentDevice(device);
        setShowModal(true);
    };

    return (
        <div className="page">
            <Navbar />
            <h2>Device Management</h2>
            <button onClick={openAddModal}>Add Device</button>
            <ul>
                {devices.map((device) => (
                    <li key={device.id}>
                        <div>
                            <strong>{device.description}</strong> - {device.address} - Max Consumption: {device.consumption} kWh
                        </div>
                        <div>
                            <button onClick={() => openEditModal(device)}>Edit</button>
                            <button onClick={() => handleDeleteDevice(device.id)}>Delete</button>
                        </div>
                    </li>
                ))}
            </ul>
            {showModal && (
                <DeviceForm
                    initialData={currentDevice}
                    onSubmit={currentDevice ? handleEditDevice : handleAddDevice}
                    onClose={() => setShowModal(false)}
                />
            )}
        </div>
    );
}

export default DeviceManagement;
