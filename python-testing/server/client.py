import websocket
import sys

import json

"""
This file is for training
"""


host = sys.argv[1]
port = sys.argv[2] 
path = sys.argv[3]


server_address = "ws://" + host + ":" + str(port) + "/" + path


# model = BasicModel()

def on_message(ws, message):
    message = json.loads(message)
    # print(message)

    ongoing = message['ongoing']
    rewards = message['rewards']
    state = message['state']

    print(ongoing, rewards, state)


    if ongoing: 
         
        # model.train_iteration(state)
        pass
    else:
        pass

    #   print(message)
    x = {"held": ["A"]}
    # ws.send(str({held: ["A"]}))
    ws.send(str(x))

    if True: 
        pass 
    else:
        ws.send("RESET")

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



