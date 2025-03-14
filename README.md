<h1>Energy Management System</h1>

A proof of concept energy management system that consists of a frontend and 4 other microservices designed to manage users and their associated energy metering devices. The system also has a chat interface that provides a communication means between a user and an administrator. 

The system is deployed using Docker and Traefik is used as a reverse proxy that receives requests on behalf of the system, identifies which components are responsible for
handling them, and routes them securely.

The administrator can perform CRUD operations on user accounts, smart energy metering devices and on the mapping of users to devices.

<h2>Deployment diagram</h2>

<img src="https://i.imgur.com/j9X38zq.jpeg" height="80%" width="80%"/>
<br/>
<br/>



