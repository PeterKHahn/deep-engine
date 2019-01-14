"""
2-input XOR example -- this is most likely the simplest possible example.
"""

from __future__ import print_function
import os
import neat
import visualize

import gym
import numpy as np
import pickle



game = gym.make('MountainCar-v0')
game._max_episode_steps = 200

print("outputs", game.action_space.n)
print("inputs", game.observation_space.shape[0])



def fitness(genome, config, iterations=10, render=False):
    net = neat.nn.FeedForwardNetwork.create(genome, config)

    total_reward = 0

    for i in range(iterations):
        st = game.reset()

        for j in range(999):

            action_probs = net.activate(st)
            action = np.argmax(action_probs)

            st1, reward, done, _ = game.step(action)
            total_reward += reward

            if render:
                game.render()

            st = st1

            if done:

                break

    res =  total_reward / iterations # average reward over the iterations
    return res


def eval_genomes(genomes, config):
    most_fit = None
    winning = None

    for genome_id, genome in genomes:

        fit = fitness(genome, config)
        if most_fit == None or fit > most_fit:
            most_fit = fit
            winning = genome

        genome.fitness = fit


    fitness(winning, config, render=True)


def run(config_file):
    # Load configuration.
    config = neat.Config(neat.DefaultGenome, neat.DefaultReproduction,
                         neat.DefaultSpeciesSet, neat.DefaultStagnation,
                         config_file)

    # Create the population, which is the top-level object for a NEAT run.
    p = neat.Population(config)

    # Add a stdout reporter to show progress in the terminal.
    p.add_reporter(neat.StdOutReporter(True))
    stats = neat.StatisticsReporter()
    p.add_reporter(stats)

    # Run for up to 1000 generations.
    winner = p.run(eval_genomes, 10000)
    pickle.dump(winner, open("winner.p", "wb"))

    #loaded_winner = pickle.load("winner.p", "rb")


    # Display the winning genome.
    print('\nBest genome:\n{!s}'.format(winner))

    # Show output of the most fit genome against training data.
    print('\nOutput:')
    output = fitness(winner, config, render=True)
    print("The winner got a score of ", output)




if __name__ == '__main__':
    # Determine path to configuration file. This path manipulation is
    # here so that the script will run successfully regardless of the
    # current working directory.
    local_dir = os.path.dirname(__file__)
    config_path = os.path.join(local_dir, 'config-feedforward')
    print(config_path)
    run(config_path)
    game.close()
