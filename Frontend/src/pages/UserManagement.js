import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import UserForm from '../components/UserForm';
import userApi from '../api/userApi';



function UserManagement() {
    const [users, setUsers] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [currentUser, setCurrentUser] = useState(null);

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {
        const token = localStorage.getItem('token');
        const response = await userApi.get('', {headers: {
                Authorization: `Bearer ${token}`
            }});
        setUsers(response.data);
    };

    const handleAddUser = async (user) => {
        const token = localStorage.getItem('token');
        await userApi.post('', user, {headers: {
                Authorization: `Bearer ${token}`
            }});
        await fetchUsers();
        setShowModal(false);
    };

    const handleEditUser = async (user) => {
        const token = localStorage.getItem('token');
        try{
            await userApi.post(`/update/${currentUser.id}`, user, {
                headers: {
                    Authorization: `Bearer ${token}`
                }});
            await fetchUsers();
            setShowModal(false);
            setCurrentUser(null);
            alert(user.name + ", " + user.role + ", " + user.password);

        } catch (error) {
            console.error('Error editing user:', error);
            alert(`Failed to edit user: ${error.response?.data?.message || error.message}`);
            alert(user.name + ", " + user.role + ", " + user.password);
        }

    };

    const handleDeleteUser = async (userId) => {
        const token = localStorage.getItem('token');
        try {
            await userApi.delete(`/${userId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            await fetchUsers();
        } catch (error) {
            console.error('Error deleting user:', error);
            alert('Failed to delete user. Ensure you have the required permissions.');
        }
    };

    const openAddModal = () => {
        setCurrentUser(null);
        setShowModal(true);
    };

    const openEditModal = (user) => {
        setCurrentUser(user);
        setShowModal(true);
    };

    return (
        <div className="page">
            <Navbar />
            <h2>User Management</h2>
            <button onClick={openAddModal}>Add User</button>
            <ul>
                {users.map((user) => (
                    <li key={user.id}>
                        {user.name} - {user.role}
                        <button onClick={() => openEditModal(user)}>Edit</button>
                        <button onClick={() => handleDeleteUser(user.id)}>Delete</button>
                    </li>
                ))}
            </ul>
            {showModal && (
                <UserForm
                    initialData={currentUser}
                    onSubmit={currentUser ? handleEditUser : handleAddUser}
                    onClose={() => setShowModal(false)}
                />
            )}
        </div>
    );
}

export default UserManagement;