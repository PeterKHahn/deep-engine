import tensorflow as tf
import numpy as np
import gym
import os

import random

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'



class A2C:
    def __init__(self):
        self.game = gym.make('MountainCar-v0') # gym.make('CartPole-v1') # 
        self.num_actions = self.game.action_space.n
        self.state_size = self.game.observation_space.shape[0]
        print('num-actions', self.num_actions)
        print('state-size', self.state_size)

        self.state_input = tf.placeholder(tf.float32, [None, self.state_size])
        self.initialization = tf.placeholder(tf.bool)

        # Define any additional placeholders needed for training your agent here:
        self.rewards = tf.placeholder(shape=[None], dtype=tf.float32)
        self.actions = tf.placeholder(shape=[None], dtype=tf.int32)


        self.state_value = self.critic()
        self.actor_probs = self.actor()
        self.loss_val = self.loss()
        self.train_op = self.optimizer()
        self.session = tf.Session()
        self.session.run(tf.global_variables_initializer())

    def optimizer(self):
        """
        :return: Optimizer for your loss function
        """
        opt = tf.train.AdamOptimizer(learning_rate=1e-3)
        return opt.minimize(self.loss_val)

    def critic(self):
        """
        Calculates the estimated value for every state in self.state_input. The critic should not depend on
        any other tensors besides self.state_input.
        :return: A tensor of shape [num_states] representing the estimated value of each state in the trajectory.
        """
        V_1 = self.state_size
        V_2 = 500
        V_3 = 200
        dropout = 0.9


        output = tf.layers.dense(self.state_input, V_2)
        output = tf.layers.dropout(output, rate=dropout, training=self.initialization)

        output = tf.layers.dense(output, V_3)
        output = tf.layers.dropout(output, rate=dropout, training=self.initialization)

        output = tf.layers.dense(output, 1)# , activation=tf.nn.relu)

        print("Should be [num_states]", output.shape)
        return output


    def actor(self):
        """
        Calculates the action probabilities for every state in self.state_input. The actor should not depend on
        any other tensors besides self.state_input.
        :return: A tensor of shape [num_states, num_actions] representing the probability distribution
        """
        W_1 = self.state_size
        W_2 = 500
        W_3 = 200

        dropout = 0.9

        output = tf.layers.dense(self.state_input, W_2, activation=tf.nn.relu)
        output = tf.layers.dropout(output, rate=dropout, training=self.initialization)

        output = tf.layers.dense(output, W_3, activation=tf.nn.relu)
        output = tf.layers.dropout(output, rate=dropout, training=self.initialization)

        output = tf.layers.dense(output, self.num_actions, activation=tf.nn.softmax)

        print("output shape", output.shape)


        return output



    def loss(self):
        """
        :return: A scalar tensor representing the combined actor and critic loss.
        """
        advantage = self.rewards - self.state_value

        indices = tf.range(0, tf.shape(self.actor_probs)[0]) * self.num_actions + self.actions
        act_probs = tf.gather(tf.reshape(self.actor_probs, [-1]), indices)

        actor_loss = -1 *  tf.reduce_mean(tf.log(act_probs) * advantage)
        critic_loss = tf.reduce_mean(tf.square(advantage))

        loss = actor_loss + critic_loss
        return loss

    def initialize(self):
        for i in range(10000):
            random_states = np.random.rand(100, self.state_size)
            rewards = np.full(100, 10000)
            random_actions = np.random.randint(self.num_actions, size=100)
            fd2 = {
                    self.state_input: random_states, 
                    self.rewards:  rewards,
                    self.actions: random_actions, 
                    self.initialization: True
            }
            self.session.run([self.train_op], feed_dict =fd2)


    def normalize(self, state):
        norm = np.linalg.norm(state)
        if norm == 0: 
            return state
        return state / norm

    def train_episode(self, p=0.0, render=False):
        """
        train_episode will be called 1000 times by the autograder to train your agent. In this method,
        run your agent for a single episode, then use that data to train your agent. Feel free to
        add any return values to this method.
        """
        history = []
        st = self.game.reset()
        st = self.normalize(st)
        for j in range(999):
            fd = {
                self.state_input: np.matrix(st), 
                self.initialization: False
            }
            act_dist = self.session.run([self.actor_probs], feed_dict = fd)
            action = np.random.choice(self.num_actions, 1, p=act_dist[0][0])

            if random.random() < p:
                action = np.random.choice(self.num_actions, 1)

            st1, reward, done, _ = self.game.step(action[0])
            st1 = self.normalize(st1)
            if render:
                self.game.render()


            history.append((st, action[0], reward))

            st = st1

            if done:
                discounted_rewards = self.discounted_rewards([x[2] for x in history])
                fd2 = {
                    self.state_input: [st[0] for st in history],
                    self.rewards:  discounted_rewards,
                    self.actions: [a[1] for a in history], 
                    self.initialization: False
                }


                l = self.session.run([self.loss_val, self.state_value, self.train_op], feed_dict =fd2)
                print(l[1])

                break



        return sum([x[2] for x in history])


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
    learner = A2C()

    learner.initialize()

    for i in range(10000):

        

        if i > 9000:

            tot += av
            num += 1
        if i % 100 == 0:
            av = learner.train_episode(render=True)

            print(i, av)
        else:
            learner.train_episode()


    print("Total / Num", tot / num)
    # assert(check_actor(learner))
