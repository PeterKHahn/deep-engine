import random
from tkinter import *
import time


master = Tk()

size = 200


canvas_width = 600
canvas_height = 480

center_x = int(canvas_width/2)
center_y = int(canvas_height/2)

w = Canvas(master, width=canvas_width, height=canvas_height)

w.pack()
w.create_line(center_x - size, center_y + size, center_x + size, center_y + size, fill="#476042")
w.create_line(center_x - size, center_y - size, center_x - size, center_y + size, fill="#476042")
w.create_line(center_x - size, center_y - size, center_x + size, center_y - size, fill="#476042")
w.create_line(center_x + size, center_y - size, center_x + size, center_y + size, fill="#476042")

snek = w.create_oval(center_x - 10, center_y - 10, center_x + 10, center_y + 10, fill="#000000")


def init_state():
    return ((5,5,6,6), 0, True)


def draw_canvas(state):

    x_vel,y_vel = state

    w.move(snek, x_vel, y_vel)

    master.update()
    time.sleep(0.01)



#draw_canvas((center_x,center_y))
for i in range(900):
    draw_canvas((5 * (random.random() - 0.5), 5 * (random.random() - 0.5)))
