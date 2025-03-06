// src/components/UserForm.js
import React, { useState, useEffect } from 'react';

function UserForm({ onSubmit, initialData, onClose }) {
    const [name, setName] = useState('');
    const [role, setRole] = useState('client');
    const [password, setPassword] = useState('');

    useEffect(() => {
        if (initialData) {
            setName(initialData.name);
            setRole(initialData.role || 'client');
            setPassword('');
        }
    }, [initialData]);

    const handleSubmit = (e) => {
        e.preventDefault();

        const userData = {
            name,
            role,
            ...(password ? { password } : {}), // Include password only if it's not empty
        };
        onSubmit(userData);
    };

    return (
        <div className="modal">
            <form onSubmit={handleSubmit}>
                <h3>{initialData ? 'Edit User' : 'Add User'}</h3>
                <input
                    type="text"
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                <select value={role} onChange={(e) => setRole(e.target.value)} required>
                    <option value="admin">Admin</option>
                    <option value="client">Client</option>
                </select>
                <input
                    type="password"
                    placeholder={initialData ? "New Password (optional)" : "Password"}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required = {!initialData} // password required if adding a new user
                />
                <button type="submit">Save</button>
                <button type="button" onClick={onClose}>Cancel</button>
            </form>
        </div>
    );
}

export default UserForm;
