import time
import json
import random

from flask import Flask, jsonify
from flask_socketio import SocketIO, emit, join_room, leave_room
import AnomalyDetector

app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret!'
socketio = SocketIO(app, cors_allowed_origins='*')
detector = AnomalyDetector.AnomalyDetector()

@socketio.on('echo')
def echo(data):
    print(data)
    emit('echo', data)

@socketio.on('subscribe')
def client_connected(data):
    '''
    {
      "id": "gwijoaas09-wg5sdfs4rege-w4h54h-w4hw5wegeg",
      "name":"bob",
      "group": "police_officer",
      "manager": false,
      "timestamp": "UNIX",
      "room": "w7yh9gg-h4-ge4gaw4aw-wafwfawe",
      "event_type": "subscribe"
    }
    '''
    notify_room(data)

@socketio.on('socketboi')
def sensor_update(data):
    '''
    {
        "id":"gwijoaas09-wg5sdfs4rege-w4h54h-w4hw5wegeg",
        "accelerometer": [0.0,0.0,0.0]
    }

    '''
    print("\n\n\n\n\n\n\n\n\n\n\n-------------------------------")
    print(data)
    print("-------------------------------")
    anomaly = detector.analyze(json.loads(data))
    notify_room(data)
    if anomaly:
        emit('anomaly', json.dumps(anomaly), broadcast=True)


@socketio.on('unsubscribe')
def on_leave(data):
    '''
    {
      "id": "gwijoaas09-wg5sdfs4rege-w4h54h-w4hw5wegeg",
      "name":"bob",
      "group": "police_officer",
      "manager": false,
      "timestamp": "UNIX",
      "room": "w7yh9gg-h4-ge4gaw4aw-wafwfawe",
      "event_type": "subscribe"
    }
    '''
    notify_room(point)

def notify_room(event_json):
    emit('update', event_json, broadcast=True)



if __name__ == '__main__':
    import os
    port = int(os.environ.get('PORT', 5000))
    socketio.run(app, host="0.0.0.0", port=port, debug=True)
