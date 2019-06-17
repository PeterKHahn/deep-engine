import tensorflow as tf
import numpy as np
import gym
import os
import time

import random

from mpl_toolkits.mplot3d import Axes3D  # noqa: F401 unused import

import matplotlib.pyplot as plt

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'



class DQN:
    def __init__(self, game_name):
        print("Creating DQN")
        self.MAX_MEMORY = 100000
        self.BATCH_SIZE = 200

        self.memory = []
        self.num_episodes = 200

        self.gamma = 0.99

        self.game = gym.make(game_name)
        self.game._max_episode_steps = self.num_episodes

        self.num_actions = self.game.action_space.n
        self.state_size = self.game.observation_space.shape[0]

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


    def add_memory(self, sample):
        self.memory.append(sample)
        if len(self.memory) > self.MAX_MEMORY:
            self.memory.pop(0)

    def sample_memory(self, n):
        n = min(n, len(self.memory))
        return random.sample(self.memory, n)


    def optimizer(self):
        """
        :return: Optimizer for your loss function
        """
        opt = tf.train.RMSPropOptimizer(learning_rate=0.001)
        # opt = tf.train.AdamOptimizer(learning_rate=0.001)
        return opt.minimize(self.loss_val)


    def q_func(self, state, reuse=False):
        """
        Q that takes in a state and outputs the q values for corresponding action
        Q values
        """
        W_1 = self.state_size
        W_2 = 512
        W_3 = 512

        r = 0.5

        bias = True
        activation = tf.nn.leaky_relu

        with tf.variable_scope('q', reuse=reuse):        
            output = tf.layers.dense(state, W_2, activation=activation, use_bias=bias)
            output = tf.layers.dropout(output, rate=r, training=self.initialization)
            output = tf.layers.dense(output, W_3, activation=activation, use_bias=bias)
            output = tf.layers.dropout(output, rate=r, training=self.initialization)

            output = tf.layers.dense(output, W_3, activation=activation, use_bias=bias)
            output = tf.layers.dropout(output, rate=r, training=self.initialization)

            output = tf.layers.dense(output, W_3, activation=activation, use_bias=bias)
            output = tf.layers.dropout(output, rate=r, training=self.initialization)

            output = tf.layers.dense(output, W_3, activation=activation, use_bias=bias)
            output = tf.layers.dropout(output, rate=r, training=self.initialization)  


            output = tf.layers.dense(output, W_3, activation=activation, use_bias=bias)
            output = tf.layers.dropout(output, rate=r, training=self.initialization)

            output = tf.layers.dense(output, W_3, activation=activation, use_bias=bias)
            output = tf.layers.dropout(output, rate=r, training=self.initialization)



            output = tf.layers.dense(output, self.num_actions)

        print("output shape", output.shape)


        return output

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


    def pre_fill(self, iterations):
        i = iterations
        while i > 0:
            st = self.game.reset()
            # st = self.normalize(st)
            states = [] 
            actions = [] 
            rewards = [] 
            state1s = [] 
            for j in range(self.num_episodes - 1):

                action = np.random.choice(self.num_actions, 1)[0]


                st1, reward, done, _ = self.game.step(action)
                # st1 = self.normalize(st1)
                states.append(st)
                actions.append(action)
                rewards.append(reward)
                state1s.append(st1)
                # self.memory.append((st, action, reward, st1))

                st = st1
                i -= 1
                if done:
                    break

            
            qs = self.discounted_rewards(rewards)

            for el in zip(states, actions, qs, state1s):
                self.add_memory(el)




    def pre_train(self, iterations):
        for i in range(iterations):
            sts = np.random.random((self.BATCH_SIZE, self.state_size)) * 2 - 1
            sts1 = np.random.random((self.BATCH_SIZE, self.state_size)) * 2 - 1

            qs = np.full(self.BATCH_SIZE, 30.0)
            #var = np.random.random(self.BATCH_SIZE)
            #qs += var
            acs = np.random.randint(self.num_actions, size=(self.BATCH_SIZE))
            
            if i % 10 == 0:
                print("iteration: ", i)

                self.train_ls(sts, qs, acs, sts1, True)
            else:
                self.train_ls(sts, qs, acs, sts1, True)
 


    


    def train_episode(self, epsilon=0.1, render=False):
        """
        train_episode will be called 1000 times by the autograder to train your agent. In this method,
        run your agent for a single episode, then use that data to train your agent. Feel free to
        add any return values to this method.
        """
        total_reward = 0

       

        current_batch = []

        states = [] 
        actions = [] 
        rewards = [] 
        states1 = []

        st = self.game.reset()
        # st = self.normalize(st)
        for j in range(2999):
            self.retrieve_loss(self.sample_memory(self.BATCH_SIZE))
            action = None
            if random.random() < epsilon:
                action = np.random.choice(self.num_actions, 1)[0]

            else:
                fd = {
                    self.state_input: np.matrix(st), 
                    self.initialization: False
                }
                act_dist, qx = self.session.run([self.actor_probs, self.q], feed_dict = fd)

                action = np.random.choice(self.num_actions, 1, p=act_dist[0])[0]
                # action = np.argmax(act_dist)
            

            if render:

                self.game.render()
                #print(st, act_dist, qx)



            st1, reward, done, _ = self.game.step(action)

            states.append(st)
            actions.append(action) 
            rewards.append(reward)
            states1.append(st1)

            total_reward += reward

            st = st1

            if done:
                break



        qs = self.discounted_rewards(rewards)

        current_batch = []

        for el in zip(states, actions, qs, states1):
            self.add_memory(el)
            current_batch.append(el)
            


        self.retrieve_loss(current_batch)
        self.retrieve_loss(self.sample_memory(self.BATCH_SIZE))


        return total_reward

    def train_ls(self, sts, qs, acs, sts1, init=False, should_print=False):
        
        fd = {
            self.state_input: sts, 
            self.rewards: qs, 
            self.actions: acs, 
            self.state_input1: sts1, 
            self.initialization: init

        }
        _, l = self.session.run([self.train_op, self.loss_val], feed_dict=fd)
        print(l)
        if should_print:

            print('########################################')

            print(acs.count(0) / len(acs), acs.count(1) / len(acs), acs.count(2) / len(acs))
            

            print('########################################')

    def retrieve_loss(self, batch):
        # batch = self.memory # self.sample_memory(self.BATCH_SIZE)


        qs = []
        sts = []
        acs = []
        sts1 = []

        for (stx, act, q, st1x) in batch:

            qs.append(q)

            sts.append(stx)
            sts1.append(st1x)
            acs.append(act)

        self.train_ls(sts, qs, acs, sts1, should_print=False)


    def discounted_rewards(self, ls):

        rev = list(reversed(ls))
        for idx in range(1, len(ls)):
            rev[idx] = rev[idx] + self.gamma * rev[idx - 1]

        return list(reversed(rev))

if __name__ == '__main__':
    # Change __main__ to train your agent for 1000 episodes and print the average reward over the last 100 episodes.
    # The code below is similar to what our autograder will be running.

    tot = 0
    num = 0
    learner = DQN('Acrobot-v1') #gym.make('MountainCar-v0') # gym.make('CartPole-v1') # 'Acrobot-v1'

    print("Prefilling memory...")
    learner.pre_fill(5000)

    print("Pre training model")
    # learner.pre_train(1000)

    print("starting iterations...")


    current_epsilon = 1.0

    for i in range(1000):
        print("Starting iteration: ", i)

        av = None


        if i % 2 == 0:
            current_epsilon *=0.99 #learner.graphs()

        if i < 20:
            av = learner.train_episode(render=True, epsilon=1.0)
        

        else:
            print("epsilon: ", current_epsilon)
            av = learner.train_episode(render=True, epsilon=current_epsilon)



        tot += av
        num += 1

        if i % 1 == 0:
            print('\t', tot / num)
            tot = 0 
            num = 0



