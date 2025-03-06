import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import { Box, Typography, TextField, Button } from '@mui/material';

const ChatBox = ({ userId, role }) => {
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState('');
    const [client, setClient] = useState(null);

    useEffect(() => {


        const stompClient = new Client({
            brokerURL:`${window.location.protocol === 'https:' ? 'wss' : 'ws'}://${window.location.host}/chat`,
            connectHeaders: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            onConnect: () => {
                console.log('Connected to WebSocket');
                stompClient.subscribe('/topic/messages', (message) => {
                    const receivedMessage = JSON.parse(message.body);
                    setMessages((prev) => [...prev, receivedMessage]);
                });
            },
            onStompError: (frame) => {
                console.error('STOMP error', frame);
            },
        });

        stompClient.activate();
        setClient(stompClient);

        return () => {
            stompClient.deactivate();
        };
    }, []);

    const sendMessage = () => {
        if (newMessage.trim() && client) {
            const message = {
                senderId: role + ", id " + userId,
                content: newMessage,
                recipientId: role === 'admin' ? 'user' : 'admin',
            };
            client.publish({ destination: '/app/send', body: JSON.stringify(message) });
            setMessages((prev) => [...prev, message]);
            setNewMessage('');
        }
    };

    return (
        <Box
            sx={{
                position: 'fixed',
                bottom: 20,
                right: 20,
                width: 300,
                boxShadow: 3,
                borderRadius: 2,
                p: 2,
                bgcolor: '#fff',
                zIndex: 1000,
            }}
        >
            <Typography variant="h6" gutterBottom>
                Chat
            </Typography>
            <Box
                sx={{
                    maxHeight: 200,
                    overflowY: 'auto',
                    bgcolor: '#f9f9f9',
                    p: 1,
                    mb: 1,
                    borderRadius: 1,
                }}
            >
                {messages.map((msg, index) => (
                    <Typography
                        key={index}
                        variant="body2"
                        sx={{
                            mb: 1,
                            color: msg.recipientId === 'admin' ? 'primary.main' : 'text.secondary',
                            textAlign: msg.recipientId === 'admin' ? 'right' : 'left',
                        }}
                    >
                        <b>{msg.senderId}:</b> {msg.content}
                    </Typography>
                ))}
            </Box>
            <TextField
                fullWidth
                variant="outlined"
                size="small"
                placeholder="Type a message..."
                value={newMessage}
                onChange={(e) => setNewMessage(e.target.value)}
                sx={{ mb: 1 }}
            />
            <Button variant="contained" fullWidth onClick={sendMessage}>
                Send
            </Button>
        </Box>
    );
};

export default ChatBox;
