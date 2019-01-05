import tensorflow as tf
import numpy as np
import gym
import os
import time

import random

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'



class DQN:
    def __init__(self):
        self.game = gym.make('MountainCar-v0') # gym.make('CartPole-v1') #
        self.game._max_episode_steps = 1000

        self.num_actions = self.game.action_space.n
        self.state_size = self.game.observation_space.shape[0]
        print('num-actions', self.num_actions)
        print('state-size', self.state_size)

        self.state_input = tf.placeholder(tf.float32, [None, self.state_size], name="state_input")

        # Define any additional placeholders needed for training your agent here:
        self.rewards = tf.placeholder(shape=[None], dtype=tf.float32, name="rewards")
        self.actions = tf.placeholder(shape=[None], dtype=tf.int32, name="actions")

        self.batch_size = 300
        self.D = []

        self.q = self.q()
        self.actor_probs = self.actor()

        self.action_index = self.get_action_index()

        self.loss_val = self.loss()
        self.train_op = self.optimizer()

        self.session = tf.Session()
        self.session.run(tf.global_variables_initializer())

    def optimizer(self):
        """
        :return: Optimizer for your loss function
        """
        opt = tf.train.AdamOptimizer(learning_rate=1e-2)
        return opt.minimize(self.loss_val)

    def q2(self):
        pass


    def q(self):
        """
        Q that takes in a state and outputs the q values for corresponding actions
        """
        W_1 = self.state_size
        W_2 = 500
        W_3 = 200

        output = tf.layers.dense(self.state_input, W_2, activation=tf.nn.relu)
        output = tf.layers.dense(output, W_3, activation=tf.nn.relu)
        output = tf.layers.dense(output, self.num_actions)

        print("output shape", output.shape)


        return output

    def get_action_index(self):
        indices = tf.range(0, tf.shape(self.q)[0]) * self.num_actions + self.actions
        return tf.gather(tf.reshape(self.q, [-1]), indices)


    def actor(self):
        return tf.nn.softmax(self.q)



    def loss(self):
        diff = self.rewards - self.action_index
        loss = tf.reduce_mean(tf.square(diff))
        return loss



    def train_episode(self, epsilon=0.1, render=False):
        """
        train_episode will be called 1000 times by the autograder to train your agent. In this method,
        run your agent for a single episode, then use that data to train your agent. Feel free to
        add any return values to this method.
        """

        self.D = []

        st = self.game.reset()
        for j in range(999):
            action = None
            if random.random() < epsilon:
                action = np.random.choice(self.num_actions, 1)[0]
            else:
                fd = {
                    self.state_input: np.matrix(st)
                }
                act_dist, qx = self.session.run([self.actor_probs, self.q], feed_dict = fd)

                #print(act_dist, qx)
                action = np.random.choice(self.num_actions, 1, p=act_dist[0])[0]
                # action = np.argmax(act_dist[0][0])
            if render:
                self.game.render()
                print(st, act_dist, qx)

            st1, reward, done, _ = self.game.step(action)
            self.D.append((st, action, reward, st1, done))

            st = st1

            if done:
                # st1 is a terminal node

                break

        qs = []

        gamma = 0.99

        idx = np.random.choice(len(self.D),self.batch_size)
        transition = np.array(self.D)[idx]
        for (stx, act, re, st1x, done) in transition:

            if done:
                qs.append(re)
            else:
                fdn = {self.state_input: np.matrix(st1x)}
                next_action = self.session.run([self.q], feed_dict=fdn)
                q_next = np.max(next_action)
                q = re + gamma * q_next
                qs.append(q)


        fd2 = {
            self.state_input: [st[0] for st in transition],
            self.rewards:  qs,
            self.actions: [a[1] for a in transition]}

        l = self.session.run([self.loss_val, self.train_op], feed_dict =fd2)

        return sum([x[2] for x in self.D])




    def discounted_rewards(self, ls):

        gamma = 0.99
        rev = list(reversed(ls))
        for idx in range(1, len(ls)):
            rev[idx] = rev[idx] + gamma * rev[idx - 1]

        return list(reversed(rev))



if __name__ == '__main__':
    # Change __main__ to train your agent for 1000 episodes and print the average reward over the last 100 episodes.
    # The code below is similar to what our autograder will be running.

    tot = 0
    num = 0
    learner = DQN()

    for i in range(10000):
        av = None

        if i % 10 == 0:

            av = learner.train_episode(render=True)
        else:
            if i < 100:
                av = learner.train_episode()
            else:
                av = learner.train_episode(render=False)

        if i > 9000:

            tot += av
            num += 1
        if i % 10 == 0:
            print(i, av)

    print("Total / Num", tot / num)
    # assert(check_actor(learner))
