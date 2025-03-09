This is a proof of concept


To build the frontend Docker image run:

-> docker build -t front_img .


To build the Client microservice Docker image run:

-> docker build -t client_img .


To build the Device microservice Docker image run:

-> docker build -t device_img .


To build the Monitoring microservice Docker image run:

-> docker build -t monitoring_img .


To build the Chat microservice Docker image run:

-> docker build -t chat_img .



To start the simulator run in the terminal:

-> python simulator.py


To start the images run:

-> docker-compose up -d




To access the frontend: localhost/login

To access traefik info: localhost:8080



