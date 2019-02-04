import websocket


def on_message(ws, message):
    print(message)

def on_error(ws, error):
    print(error)

def on_close(ws):
    print("### closed ###")

def on_open(ws):
    print("Opened a connection")
    for i in range(3):

        ws.send("Hello %d" % i)


# websocket.enbableTrace(True)
print("Creating server app...")
ws = websocket.WebSocketApp("ws://localhost:4567/chat", on_message=on_message, on_error=on_error, on_close=on_close)
print("Setting on_open...")
ws.on_open = on_open
print("running server..")
ws.run_forever()

