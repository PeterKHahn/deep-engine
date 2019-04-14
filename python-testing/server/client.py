import websocket
import sys
import basic_model
import numpy as np

import json

"""
This file is for training
"""


host = sys.argv[1]
port = sys.argv[2] 
path = sys.argv[3]


server_address = "ws://" + host + ":" + str(port) + "/" + path


def state_to_vector(state):
    """
    Given a state, returns a vector that 
    can be passed into the model that represents 
    the state
    """
    player1 = state['player1']        
    player2 = state['player2']



    return player_to_vector(player1) + player_to_vector(player2)


def player_to_vector(player):
    px = player['position']['x']
    py = player['position']['y']

    velx = player['velocity']['x']
    vely = player['velocity']['y']

    angle = player['angle'] % np.pi
    angular_velocity = player['angularVelocity']
    

    return [px, py, velx, vely, angle, angular_velocity]


model = basic_model.BasicModel(12, 3, state_to_vector)

current_state = None
count = 0

def on_message(ws, message):
    global current_state
    global count

    message = json.loads(message)
    # print(message)

    message_type = message['type']

    if message_type == "INIT":
        current_state = message['state']

    elif message_type == "NORMAL":

        ongoing = message['ongoing']
        rewards = message['rewards']
        state = message['state']

        try:
            count += 1

            action = model.train_iteration(current_state, p=1.0)[0]
            # perform action
            ws.send(str(action))
            model.log(current_state, action, rewards[0])
            current_state = state

        except Exception as error:
            print(error)

        if not ongoing or  count > 999:
            reward_sum = model.calculate_reward()
            print(reward_sum)
            ws.send("RESET")
            count = 0
            pass

            




def on_error(ws, error):
    print(error)

def on_close(ws):
    print("### closed ###")

def on_open(ws):
    print("Opened a connection")



# websocket.enbableTrace(True)
print("Creating server app...")
ws = websocket.WebSocketApp(server_address, on_message=on_message, on_error=on_error, on_close=on_close)
print("Setting on_open...")
ws.on_open = on_open
print("running server..")
# ws.send("RESET")
ws.run_forever()



