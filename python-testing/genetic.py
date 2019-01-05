import numpy as np
import gym
import random

import snake

game = gym.make('CartPole-v1')
num_actions = game.action_space.n
state_size = game.observation_space.shape[0]

def random_matrix():
    return np.random.rand(num_actions, state_size)

def init(population_size):
    return [random_matrix() for i in range(population_size)]


population_size = 100000

def softmax(x):
    e_x = np.exp(x - np.max(x))
    return e_x / e_x.sum()


def play_game(actor, render=False):
    st = game.reset()
    total_reward = 0

    action_probs = softmax(np.dot(actor,  st))
    action = np.argmax(action_probs)

    for j in range(999):
        st1, reward, done, _ = game.step(action)
        total_reward += reward

        if render:
            game.render()

        st = st1

        if done:

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


def breed(actor1, actor2, mutation_rate=0.1):
    new_actor = np.random.rand(num_actions, state_size)
    for r in range(num_actions):
        for c in range(state_size):
            if random.random() < 0.2:
                continue
            elif random.random() < 0.5:
                new_actor[r][c] = actor1[r][c]
            else:
                new_actor[r][c] = actor2[r][c]
    return new_actor



num_generations = 1000
def experiment():
    population = init(population_size)

    for i in range(num_generations):
        print("Generation: ", i)
        fittest_population = fittest(population)
        new_pop_size = len(fittest_population)

        new_population = []

        for j in range(population_size):
            rand1 = random.randint(0, new_pop_size - 1)
            rand2 = random.randint(0, new_pop_size - 1)
            baby = breed(fittest_population[rand1][0], fittest_population[rand2][0])
            new_population.append(baby)


        population = new_population

        # Test loop
        if i % 10 == 0:
            rand1 = random.randint(0, new_pop_size - 1)

            reward = play_game(fittest_population[rand1][0], render=False)
            print("Reward: ", reward)





experiment()

game.close()
