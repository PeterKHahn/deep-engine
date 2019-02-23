import websocket
import sys

"""
This file is for training
"""


host = sys.argv[1]
port = sys.argv[2] 
path = sys.argv[3]


server_address = "ws://" + host + ":" + str(port) + "/" + path


def on_message(ws, message):
    print(message)
    player_id = message 
    x = {"held": ["A"]}
    # ws.send(str({held: ["A"]}))
    ws.send(str(x))

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
ws.run_forever()

