import React, { useState, useEffect } from 'react';

function DeviceForm({ onSubmit, initialData, onClose }) {
    const [description, setDescription] = useState('');
    const [address, setAddress] = useState('');
    const [consumption, setConsumption] = useState('1');

    useEffect(() => {
        if (initialData) {
            setDescription(initialData.description);
            setAddress(initialData.address);
            setConsumption(initialData.consumption);
        }
    }, [initialData]);

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({ description, address, consumption });
    };

    return (
        <div className="modal">
            <form onSubmit={handleSubmit}>
                <h3>{initialData ? 'Edit Device' : 'Add Device'}</h3>
                <input
                    type="text"
                    placeholder="Description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Address"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    required
                />
                <input
                    type="number"
                    placeholder="Max Consumption"
                    value={consumption}
                    onChange={(e) => setConsumption(e.target.value)}
                    required
                />
                <button type="submit">Save</button>
                <button type="button" onClick={onClose}>Cancel</button>
            </form>
        </div>
    );
}

export default DeviceForm;