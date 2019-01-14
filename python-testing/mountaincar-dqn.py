import tensorflow as tf
import numpy as np
import gym
import os
import time

import random

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'



class DQN:
    def __init__(self):
        self.MAX_MEMORY = 100000
        self.BATCH_SIZE = 200

        self.memory = []
        self.num_episodes = 200

        self.game = gym.make('MountainCar-v0') # gym.make('CartPole-v1') #
        self.game._max_episode_steps = self.num_episodes

        self.num_actions = 2#self.game.action_space.n
        self.state_size = self.game.observation_space.shape[0]

        self.high = self.game.observation_space.high
        self.low = self.game.observation_space.low
        self.mean = (self.high + self.low) / 2
        self.spread = abs(self.high - self.low) / 2

        print('num-actions', self.num_actions)
        print('state-size', self.state_size)

        self.state_input = tf.placeholder(tf.float32, [None, self.state_size], name="state_input")

        # Define any additional placeholders needed for training your agent here:
        self.rewards = tf.placeholder(shape=[None], dtype=tf.float32, name="rewards")
        self.actions = tf.placeholder(shape=[None], dtype=tf.int32, name="actions")

        self.batch_size = 200

        self.q = self.q()
        self.actor_probs = self.actor()

        self.values = self.values_tensor()
        self.diff = self.diff_tensor()

        self.action_index = self.get_action_index()

        self.loss_val = self.loss()
        self.train_op = self.optimizer()

        self.session = tf.Session()
        self.session.run(tf.global_variables_initializer())


    def add_memory(self, sample):
        self.memory.append(sample)
        if len(self.memory) > self.MAX_MEMORY:
            self.memory.pop()

    def sample_memory(self, n):
        n = min(n, len(self.memory))
        return random.sample(self.memory, n)


    def optimizer(self):
        """
        :return: Optimizer for your loss function
        """
        opt = tf.train.RMSPropOptimizer(learning_rate=0.001)
        return opt.minimize(self.loss_val)


    def q(self):
        """
        Q that takes in a state and outputs the q values for corresponding action
        Q values
        """
        W_1 = self.state_size
        W_2 = 256
        W_3 = 256

        output = tf.layers.dense(self.state_input, W_2, activation=tf.nn.leaky_relu)
        output = tf.layers.dense(output, W_3, activation=tf.nn.leaky_relu)
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
        actions_one_hot = tf.one_hot(self.actions, self.num_actions)
        return tf.reduce_sum(tf.multiply(self.q, actions_one_hot), 1)

    def diff_tensor(self):
        return self.rewards - self.values

    def loss(self):

        values = self.values

        diff = self.diff
        loss = tf.reduce_mean(tf.square(diff))
        return loss


    def pre_fill(self, iterations):
        i = iterations
        while i > 0:
            st = self.game.reset()
            st = self.normalize(st)
            for j in range(self.num_episodes - 1):

                action = np.random.choice(self.num_actions, 1)[0]
                if action == 1:
                    action = 2
                st1, reward, done, _ = self.game.step(action)
                st1 = self.normalize(st1)
                self.memory.append((st, action, reward, st1, done))

                st = st1
                i -= 1
                if done:
                    break

    def normalize(self, st):
        return (st - self.mean) / self.spread

    def train_episode(self, epsilon=0.1, render=False):
        """
        train_episode will be called 1000 times by the autograder to train your agent. In this method,
        run your agent for a single episode, then use that data to train your agent. Feel free to
        add any return values to this method.
        """
        total_reward = 0
        flag = False


        current_batch = []

        st = self.game.reset()
        st = self.normalize(st)
        for j in range(2999):
            action = None
            if random.random() < epsilon:
                action = np.random.choice(self.num_actions, 1)[0]
                if action == 1:
                    action = 2
            else:
                fd = {
                    self.state_input: np.matrix(st)
                }
                act_dist, qx = self.session.run([self.actor_probs, self.q], feed_dict = fd)

                #print(act_dist, qx)
                action = np.random.choice(self.num_actions, 1, p=act_dist[0])[0]
                #action = np.argmax(act_dist[0][0])

                if action == 1:
                    action = 2


            if render:

                self.game.render()
                #print(st, act_dist, qx)

            st1, reward, done, _ = self.game.step(action)
            st1 = self.normalize(st1)
            self.memory.append((st, action, reward, st1, done))
            current_batch.append((st, action, reward, st1, done))
            #self.retrieve_loss()

            total_reward += reward
            if reward > -1:
                print("AYOOO")

            st = st1

            if done:
                # st1 is a terminal node
                flag=True
                break



        l = self.retrieve_loss()

        return total_reward

    def retrieve_loss(self):
        batch = self.sample_memory(self.BATCH_SIZE)
        gamma = 0.9


        qs = []
        sts = []
        acs = []

        for (stx, act, re, st1x, done) in batch:


            if done:
                qs.append(re)
            else:
                fdn = {self.state_input: np.matrix(st1x)}
                next_action = self.session.run([self.q], feed_dict=fdn)
                q_next = np.max(next_action)
                # print(re, st1x, q_next)
                q = re + gamma * q_next
                qs.append(q)

            acs.append(act)
            sts.append(stx)

        fd2 = {
            self.state_input: sts,
            self.rewards:  qs,
            self.actions: acs}

        l = self.session.run([self.values, self.train_op], feed_dict =fd2)
        return l







if __name__ == '__main__':
    # Change __main__ to train your agent for 1000 episodes and print the average reward over the last 100 episodes.
    # The code below is similar to what our autograder will be running.

    tot = 0
    num = 0
    learner = DQN()

    print("Prefilling memory...")
    learner.pre_fill(100000)

    print("starting iterations...")

    for i in range(100000):

        epsilon = None
        av = None
        if i % 10 == 0:
            print('iteration: ', i)

        if i < 500:
            av = learner.train_episode(render=False, epsilon=1)
            epsilon = 1
        else:

            if i % 100 == 0:
                av = learner.train_episode(render=True)
                epsilon = 0.1
            else:
                if i % 2 == 0:

                    av = learner.train_episode(render=False)
                    epsilon = .1
                else:
                    av = learner.train_episode(render=False, epsilon=0.5)
                    epsilon = .5
        if av > -200:
            print("made it to the goal!", epsilon)

        if i > 9000:

            tot += av
            num += 1
        if i % 10 == 0:
            print(i, av)

    print("Total / Num", tot / num)
    # assert(check_actor(learner))
