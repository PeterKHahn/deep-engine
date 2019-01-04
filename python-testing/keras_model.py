from keras.models import Sequential
from keras.layers import Dense
import numpy as np

import snake
import time

import gym
import random

game =  gym.make('CartPole-v1') # snake.Snake()
num_actions = game.action_space.n
state_size = game.observation_space.shape[0]


def random_model():
    model = Sequential()
    model.add(Dense(units=50, activation='relu', input_dim=state_size))
    model.add(Dense(units=num_actions, activation='softmax', input_dim=50))

    return model



def init_models(population_size):
    return [random_model() for i in range(population_size)]




population_size = 10

def play_game(actor, render=False):
    st = game.reset()
    total_reward = 0


    for j in range(999):

        action_probs = actor.predict(np.array([st]), 1)
        action = np.random.choice(num_actions, 1, p=action_probs[0])[0]

        st1, reward, ongoing, _ = game.step(action)
        total_reward += reward
        if reward > 0:
            print("AU")


        if render:
            game.render()
            time.sleep(0.1)
        st = st1
        if not ongoing:

            break

    # print(total_reward)
    return total_reward


def fittest(population):

    scores_list = []

    for actor in population:

        total_reward = play_game(actor)

        # print(total_reward)
        scores_list.append((actor, total_reward))

    max = sorted(scores_list, key=lambda x: x[1])[-population_size // 10: ]
    return max


def breed(actor1, actor2, mutation_rate=0.8):
    new_actor = random_model()
    new_actor_weights = new_actor.get_weights()

    weights = new_actor_weights[0]
    bias = new_actor_weights[1]

    weights1 = actor1.get_weights()


    weights2 = actor2.get_weights()

    for r in range(len(weights)):
        for c in range(len(weights[r])):
            #print(weights1[r][c])

            if random.random() < 0.5:
                weights[r][c] = weights1[0][r][c]
            else:
                weights[r][c] = weights2[0][r][c]

            if random.random() < mutation_rate:
                change = random.uniform(-0.1, 0.1)
                weights[r][c] += change

    return [weights, bias]



    # return np.

    #for r in range(num_actions):
    #    for c in range(state_size):
    #        if random.random() < 0.2:
    #            continue
    #        elif random.random() < 0.5:
    #            new_actor[r][c] = actor1[r][c]
    #        else:
    #            new_actor[r][c] = actor2[r][c]
    #return new_actor

def random_mutate(weights, mutation_rate=0.8):
    for r in range(len(weights)):
        for c in range(len(weights[r])):
            if random.random() < mutation_rate:
                change = random.uniform(-0.1, 0.1)
                weights[r][c] += change
    return weights

num_generations = 1000
def experiment():
    population = init_models(population_size)

    for i in range(num_generations):
        print("Generation: ", i)
        fittest_population = fittest(population)
        new_pop_size = len(fittest_population)

        new_population_weights = []

        for j in range(population_size):
            rand1 = random.randint(0, new_pop_size - 1)
            rand2 = random.randint(0, new_pop_size - 1)
            baby = breed(fittest_population[rand1][0], fittest_population[rand2][0])
            new_population_weights.append(baby)


        for k in range(population_size):
            net = population[k]

            net.set_weights(new_population_weights[k])



        # Test loop
        if i % 5 == 0:
            rand1 = random.randint(0, new_pop_size - 1)

            reward = play_game(population[rand1], render=True)
            print("Reward: ", reward)





experiment()

game.close()
