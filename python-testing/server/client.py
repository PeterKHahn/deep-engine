import websocket


def on_message(ws, message):
    print(message)

def on_error(ws, error):
    print(error)

def on_close(ws):
    print("### closed ###")

def on_open(ws):
    for i in range(3):
        time.sleep(1)
        ws.send("Hello %d" % i)
    time.sleep(1)
    ws.close()


websocket.enbableTrace(True)
ws = websocket.WebSocketApp("ws://localhost:4567/chat", on_message=on_message, on_error=on_error, on_close=on_close)
ws.on_open = on_open
ws.run_forever()
