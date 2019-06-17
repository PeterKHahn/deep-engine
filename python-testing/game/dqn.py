import tensorflow as tf
import numpy as np
import gym
import os
import time

import random

from mpl_toolkits.mplot3d import Axes3D  # noqa: F401 unused import

import matplotlib.pyplot as plt

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'



class Memory:

    def __init__(self):

        self.memory = []
        self.intense_plus = [] 
        self.intense_minus = []
        self.positive = [] 
        self.MAX_MEMORY = 100000
        self.gamma = 0.99


        self.current_batch = []

        self.states = [] 
        self.actions = [] 
        self.state1s = [] 
        self.rewards = []

        self.reward = 0
        

    def sample_memory(self, n):
        n = min(n, len(self.memory))
        return random.sample(self.memory, n)

    def sample_intense(self, n):
        n1 = min(n, len(self.intense_plus))
        n2 = min(n, len(self.intense_minus))
        return random.sample(self.intense_plus, n1) + random.sample(self.intense_minus, n2)


    def sample_positive(self, n):
        n = min(n, len(self.positive)) 
        return random.sample(self.positive, n)

    def reset(self):
        
        self.states = [] 
        self.actions = [] 
        self.state1s = [] 
        self.rewards = [] 

        self.current_batch = []

        self.reward = 0


    def add_current(self, st, a, r, st1):
        self.states.append(st)
        self.actions.append(a)
        self.rewards.append(r)
        self.state1s.append(st1)

        self.reward += r


    def discounted_rewards(self):
        ls = self.rewards
        rev = list(reversed(ls))

        for idx in range(1, len(ls)):
            rev[idx] = rev[idx] + self.gamma * rev[idx - 1]

        return list(reversed(rev))
        

    def create_memories(self):
        qs = self.discounted_rewards()
        
        for el in zip(self.states, self.actions, qs, self.state1s):
            self.add_memory(el)

        return qs



    def add_memory(self, sample):
        self.memory.append(sample)
        self.current_batch.append(sample)
        if sample[2] > 97:
            self.intense_plus.append(sample)
            if len(self.intense_plus) > self.MAX_MEMORY:
                self.intense_plus.pop(0)
        elif sample[2] < 97:
            self.intense_minus.append(sample)
            if len(self.intense_minus) > self.MAX_MEMORY:
                self.intense_minus.pop(0)

        if sample[2] > 0:
            self.positive.append(sample)
            if len(self.positive) > self.MAX_MEMORY:
                self.positive.pop(0)
            
        if len(self.memory) > self.MAX_MEMORY:
            self.memory.pop(0)


class DQN:
    def __init__(self, parameters):
        print("Creating DQN")
        self.memory = Memory()

        self.BATCH_SIZE = 250

        self.gamma = self.memory.gamma
        self.epsilon = 1.0 # 1.0




        self.num_actions = parameters.action_space
        self.state_size = parameters.state_space

        print('num-actions', self.num_actions)
        print('state-size', self.state_size)

        self.state_input = tf.placeholder(tf.float32, [None, self.state_size], name="state_input")
        self.state_input1 = tf.placeholder(tf.float32, [None, self.state_size], name="state_input1")

        self.initialization = tf.placeholder(tf.bool)

        # Define any additional placeholders needed for training your agent here:
        self.rewards = tf.placeholder(shape=[None], dtype=tf.float32, name="rewards")
        self.actions = tf.placeholder(shape=[None], dtype=tf.int32, name="actions")


        self.q = self.q_func(self.state_input)
        self.q1 = self.q_func(self.state_input1, reuse=True)

        self.actor_probs = self.actor()
        self.action_index = self.get_action_index()

        self.values = self.values_tensor()
        self.diff = self.diff_tensor()


        self.loss_val = self.loss()
        self.train_op = self.optimizer()


        self.session = tf.Session()
        self.session.run(tf.global_variables_initializer())
        self.saver = tf.train.Saver()





    def optimizer(self):
        """
        :return: Optimizer for your loss function
        """
        #opt = tf.train.RMSPropOptimizer(learning_rate=0.001)
        opt = tf.train.AdamOptimizer(learning_rate=0.001)
        return opt.minimize(self.loss_val)


    def q_func(self, state, reuse=False):
        """
        Q that takes in a state and outputs the q values for corresponding action
        Q values
        """
        W_1 = self.state_size
        W_2 = 300
        W_3 = 500

        r = 0.5

        bias = True
        activation = tf.nn.leaky_relu

        with tf.variable_scope('q', reuse=reuse):       
            W = tf.Variable(initial_value=tf.random_normal(shape=[self.state_size, W_2], stddev=0.1))
            b = tf.Variable(initial_value=tf.random_normal(shape=[W_2], stddev=0.1))

            output = activation(tf.matmul(state, W) + b)


            for i in range(1):

                W = tf.Variable(initial_value=tf.random_normal(shape=[W_2, W_2], stddev=0.1))
                b = tf.Variable(initial_value=tf.random_normal(shape=[W_2], stddev=0.1))

                output = activation(tf.matmul(output, W) + b)

            W = tf.Variable(initial_value=tf.random_normal(shape=[W_2, self.num_actions], stddev=0.1))
            b = tf.Variable(initial_value=tf.random_normal(shape=[self.num_actions], stddev=0.1))

            output = activation(tf.matmul(output, W) + b)
        return output


    def set_epsilon(self, epsilon):
        self.epsilon = epsilon
    def get_action_index(self):
        """
        a tensor that represents the q values of particular actions
        """
        indices = tf.range(0, tf.shape(self.q)[0]) * self.num_actions + self.actions
        return tf.gather(tf.reshape(self.q, [-1]), indices)


    def actor(self):
        return tf.nn.softmax(self.q)

    def values_tensor(self):
        reward_from_state1 = self.q1 # NEEDS TO BE THE STATE PRIME VECTOR 
        max_reward_of_state1 = tf.reduce_max(reward_from_state1, axis=1)
        reward_plus_potential = self.rewards + (self.gamma * max_reward_of_state1)

        return reward_plus_potential

        #actions_one_hot = tf.one_hot(self.actions, self.num_actions)
        #return tf.reduce_sum(tf.multiply(self.q, actions_one_hot), 1)

    def diff_tensor(self):
        return self.values - self.action_index # self.rewards

    def loss(self):

        diff = self.diff
        loss = tf.reduce_mean(tf.square(diff))
        return loss
    def reset(self):
        self.memory.reset()
        
    def query_action(self, st, mode):

        if mode == 'PRACTICE':
            action = np.random.choice(self.num_actions, 1)[0]
            return action 
             
        elif mode == 'TRAIN' or mode == 'TEST':

            action = None

            if random.random() < self.epsilon:
                action = np.random.choice(self.num_actions, 1)[0]
                

            else:

                fd = {
                    self.state_input: np.matrix(st), 
                    self.initialization: False
                }
                act_dist = self.session.run(self.actor_probs, feed_dict = fd)
                action = np.random.choice(self.num_actions, 1, p=act_dist[0])[0]
            return action
        else:
            print("FUCK")

        
    def game_response(self, state, action, reward, state1, train=False):

        if train:

            self.retrieve_loss(self.memory.sample_memory(self.BATCH_SIZE))
            self.retrieve_loss(self.memory.sample_intense(self.BATCH_SIZE))
            self.retrieve_loss(self.memory.sample_positive(self.BATCH_SIZE))

        self.memory.add_current(state, action, reward, state1)



    def finish_game(self, train=False):
        reward = self.memory.create_memories()

        if train:
            self.retrieve_loss(self.memory.current_batch)
            self.retrieve_loss(self.memory.sample_memory(self.BATCH_SIZE))
            self.retrieve_loss(self.memory.sample_intense(self.BATCH_SIZE))
            self.retrieve_loss(self.memory.sample_positive(self.BATCH_SIZE))


            if self.epsilon > 0.1 and random.random() < 0.05:
                self.epsilon *= 0.995



    def train_ls(self, sts, qs, acs, sts1, init=False, should_print=False):
        
        fd = {
            self.state_input: sts, 
            self.rewards: qs, 
            self.actions: acs, 
            self.state_input1: sts1, 
            self.initialization: init

        }
        _, l = self.session.run([self.train_op, self.loss_val], feed_dict=fd)


        if should_print:

            print('########################################')

            print(acs.count(0) / len(acs), acs.count(1) / len(acs), acs.count(2) / len(acs))
            

            print('########################################')

        return l

    def retrieve_loss(self, batch):

        if not batch:
            print('batch is empty')
            return 



        qs = []
        sts = []
        acs = []
        sts1 = []

        for (stx, act, q, st1x) in batch:

            qs.append(q)

            sts.append(stx)
            sts1.append(st1x)
            acs.append(act)

        

        return self.train_ls(sts, qs, acs, sts1, should_print=False)

    def save_model(self, iteration):
        self.saver.save(self.session, './pokemodel' + str(iteration))
        