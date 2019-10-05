import time
import json
import random

from flask import Flask, jsonify
from flask_socketio import SocketIO, emit, join_room, leave_room

app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret!'
socketio = SocketIO(app, cors_allowed_origins='*')

# @app.route('/')
# def index():
#     return jsonify("200 or something"), 101



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

    '''
    print("\n\n\n\n\n\n\n\n\n\n\n-------------------------------")
    print(data)
    print("-------------------------------")
    notify_room(data)

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
    emit('update', event_json)


# @app.route('/test_update', methods=['GET'])
# def test_update():
#     print("TESTING")
#     ranNum = random.randint(1,101)
#     for i in range(ranNum):
#         point = {"price":i}
#         notify_room(point, "default")
#         return "done"

if __name__ == '__main__':
    import os
    port = int(os.environ.get('PORT', 5000))
    socketio.run(app, host="0.0.0.0", port=port, debug=True)
