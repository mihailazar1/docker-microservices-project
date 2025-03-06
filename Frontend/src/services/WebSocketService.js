import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

class WebSocketService {
    constructor() {
        this.client = null;
    }

    connect(onMessageReceived) {
        this.client = new Client({
            webSocketFactory: () => new SockJS('/chat'), // Connect to WebSocket endpoint
            debug: console.log,
            onConnect: () => {
                console.log('Connected to WebSocket');
                this.client.subscribe('/topic/messages', (message) => {
                    onMessageReceived(JSON.parse(message.body));
                });
            },
            onDisconnect: () => console.log('Disconnected from WebSocket'),
        });
        this.client.activate();
    }

    sendMessage(message) {
        if (this.client && this.client.connected) {
            this.client.publish({
                destination: '/app/send',
                body: JSON.stringify(message),
            });
        }
    }
}

export default new WebSocketService();