"""
2-input XOR example -- this is most likely the simplest possible example.
"""

from __future__ import print_function
import os
import neat
import visualize

import gym
import numpy as np

# 2-input XOR inputs and expected outputs.
xor_inputs = [(0.0, 0.0), (0.0, 1.0), (1.0, 0.0), (1.0, 1.0)]
xor_outputs = [   (0.0,),     (1.0,),     (1.0,),     (0.0,)]

game = gym.make('CartPole-v1')

def fitness(genome, config):
    net = neat.nn.FeedForwardNetwork.create(genome, config)

    st = game.reset()
    total_reward = 0



    for j in range(999):

        action_probs = net.activate(st)
        action = np.argmax(action_probs)

        st1, reward, done, _ = game.step(action)
        total_reward += reward

        if True:
            game.render()

        st = st1

        if done:

            break

    return total_reward


def eval_genomes(genomes, config):
    for genome_id, genome in genomes:


        genome.fitness = fitness(genome, config)


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
    p.add_reporter(neat.Checkpointer(5))

    # Run for up to 300 generations.
    winner = p.run(eval_genomes, 300)

    # Display the winning genome.
    print('\nBest genome:\n{!s}'.format(winner))

    # Show output of the most fit genome against training data.
    print('\nOutput:')
    output = fitness(winner, config)
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
