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

game = gym.make('MountainCar-v0')
game._max_episode_steps = 1000

print("outputs", game.action_space.n)
print("inputs", game.observation_space.shape[0])

def discounted_reward(ls, lam=0.99):
    new_ls = reversed(ls)
    total = 0
    for i in new_ls:
        total = total * lam + i

    return total



def fitness(genome, config, render=False):
    net = neat.nn.FeedForwardNetwork.create(genome, config)

    st = game.reset()
    total_reward = []



    for j in range(999):

        action_probs = net.activate(st)
        action = np.argmax(action_probs)

        st1, reward, done, _ = game.step(action)
        total_reward.append(reward)

        if render:
            game.render()

        st = st1

        if done:

            break

    res =  sum(total_reward) #discounted_reward(total_reward)
    return res


def eval_genomes(genomes, config):
    last = None

    for genome_id, genome in genomes:


        genome.fitness = fitness(genome, config)
        last = genome

    last.fitness = fitness(last, config, render=True)


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

    # Run for up to 1000 generations.
    winner = p.run(eval_genomes, 10000)

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
