import random
import time

size = 10

def init_state():
    return (5, 5, 6, 6, 0, True)



def print_board(state):
    player_r, player_c, apple_r, apple_c, score, ongoing = state
    for r in range(size):
        for c in range(size):
            if player_r == r and player_c == c:
                print("o", end="")
            elif apple_r == r and apple_c == c:
                print("x", end="")
            else:
                print(".", end="")
        print("\n", end="")
    print(score, ongoing)


def next_state(action, state):
    player_r, player_c, apple_r, apple_c, score, ongoing = state
    if action == "LEFT":
        return transition(player_r, player_c - 1, state)
    elif action == "RIGHT":
        return transition(player_r, player_c + 1, state)

    elif action == "UP":
        return transition(player_r - 1, player_c, state)
    else:
         # """action == DOWN"""
        return transition(player_r + 1, player_c, state)






def transition(player_r, player_c, state):
    _, _, apple_r, apple_c, score , _ = state
    if player_r < 0 or player_c < 0 or player_r >= size or player_c >= size:
        # catch case where we go out of bounds to end game
        return (-1, -1, -1, -1, score, False)

    if player_r == apple_r and player_c == apple_c:
        new_r, new_c = get_new_apple(player_r, player_c)
        return (player_r, player_c, new_r, new_c, score + 1, True)
    else:
        return (player_r, player_c, apple_r, apple_c, score, True)



def get_new_apple(player_r, player_c):
    new_r, new_c =  (random.randint(0, size - 1), random.randint(0, size))
    if(new_r == player_r and new_c == player_c):
        return get_new_apple(player_r, player_c)
    else:
        return (new_r, new_c)


move_ar = ["LEFT", "RIGHT", "UP", "DOWN"]
state = init_state()

for i in range(1000):
    print_board(state)
    move = random.randint(0, 3)
    state = next_state(move_ar[move], state)
    print(state)
    _, _, _, _, score, ongoing = state
    time.sleep(0.1)
    if not ongoing:
        print("GAME LOST")
        print("SCORE", score)
        break
