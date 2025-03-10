import csv
import json
import pika
import time
from datetime import datetime


with open('config.json', 'r') as config_file:
    config = json.load(config_file)

device_id = config['device_id']
rabbitmq_url = config['rabbitmq_url']
queue_name = config['queue_name']

# Connect to RabbitMQ
connection_params = pika.URLParameters(rabbitmq_url)
connection = pika.BlockingConnection(connection_params)
channel = connection.channel()

channel.queue_declare(queue=queue_name, durable=True)


with open('sensor.csv', 'r') as csv_file:
    csv_reader = csv.reader(csv_file)
    sensor_values = list(csv_reader)
    
print(len(sensor_values))

# Iterate through sensor values and send them to RabbitMQ
for value_row in sensor_values:
    measurement_value = float(value_row[0])  
    timestamp = int(datetime.utcnow().timestamp())  

  
    message = {
        "timestamp": timestamp,
        "device_id": device_id,
        "measurement_value": measurement_value
    }

   
    message_json = json.dumps(message)

   
    channel.basic_publish(exchange='',
                          routing_key=queue_name,
                          body=message_json)

    print(f"Sent: {message_json}")

 
    time.sleep(1)


connection.close()
