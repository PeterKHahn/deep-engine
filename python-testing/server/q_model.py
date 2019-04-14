import tensorflow as tf 
import numpy as np
import gym 
import random



class QModel:

    def __init__(self, state_size, num_actions, state_to_vector):
        self.sess = tf.Session()
        
        self.state_size = state_size
        self.num_actions = num_actions

        self.state_to_vector = state_to_vector

        self.max_step = 999
        self.gamma = 0.99

        self.state_vector = tf.placeholder(tf.float32, [None, self.state_size])
        self.state_vector1 = tf.placeholder(tf.float32, [None, self.state_size])

        self.rewards_vector = tf.placeholder(tf.float32, [None]) 
        self.actions_vector = tf.placeholder(tf.int32, shape=[None])

        self.actor_probs = self.actor() 

        self.q_state = self.q_func(self.state_vector)
        self.q_state1 = self.q_func(self.state_vector1, reuse=True)

        self.loss_tensor = self.loss_func()
        self.optimize_tensor = self.optimizer_func()


        self.sess.run(tf.global_variables_initializer())


        self.saver = tf.train.Saver()

        self.history = []


    def q_func(self, state, reuse=False):
        with tf.variable_scope('q', reuse=reuse):
            layer = tf.layers.dense(state, 100, kernel_initializer=tf.random_normal_initializer(stddev=0.01))
            layer = tf.layers.dense(layer, 200, kernel_initializer=tf.random_normal_initializer(stddev=0.01))

            layer = tf.layers.dense(layer, self.num_actions, kernel_initializer=tf.random_normal_initializer(stddev=0.01))
            return layer

    def actor(self):
        return tf.nn.softmax(self.q)

    def loss_func(self):

        reward_from_state1 = self.q_state1 # NEEDS TO BE THE STATE PRIME VECTOR 
        max_reward_of_state1 = tf.reduce_max(reward_from_state1)
        reward_plus_potential = self.rewards_vector + self.gamma * max_reward_of_state1

        loss = tf.reduce_mean(tf.square(reward_plus_potential - self.q_state))

        return loss

         
    

    def optimizer_func(self):
        opt = tf.train.AdamOptimizer(learning_rate=1e-3)
        return opt.minimize(self.loss_tensor)
            


    def log(self, state, action, reward, state1):
        """
        Adds a particular state action reward tuple 
        to the history list 
        """
        self.history.append((state, action, reward, state1))


    def get_action(self, state_dictionary):
        state_vector = self.state_to_vector(state_dictionary)
        fd = {
            self.state_vector: np.matrix(state_vector)
        }

        act_distribution = self.sess.run([self.actor_probs], feed_dict=fd)

        action = np.random.choice(self.num_actions, 1, p=act_distribution[0][0])[0]
        return action 
        

    def train(self):
        pass 


    def train_iteration(self, state_dictionary, p=0.0):
        state_vector = self.state_to_vector(state_dictionary)

        fd = {
            self.state_vector: np.matrix(state_vector) # TODO modify this
        }

        act_dist = self.sess.run([self.actor_probs], feed_dict = fd)
        print(state_vector)
        print(act_dist)
        if random.random() < p: 
            return np.random.choice(self.num_actions, 1)
        

        action = np.random.choice(self.num_actions, 1, p=act_dist[0][0])

        return action
        
  
    
    def save_model(self, file_path):
        save_path = self.saver.save(self.sess, file_path)

    
    def restore_model(self, file_path):
        self.saver.restore(self.sess, file_path)




def state_to_vector(self, state):
    """
    Given a state, returns a vector that 
    can be passed into the model that represents 
    the state
    """
    player1 = state['player1']        
    player2 = state['player2']



    return self.player_to_vector(player1) + self.player_to_vector(player2)


def player_to_vector(self, player):
    px = player['position']['x']
    py = player['position']['y']

    velx = player['velocity']['x']
    vely = player['velocity']['y']

    angle = player['angle'] % np.pi
    angular_velocity = player['angularVelocity']
    

    return [px, py, velx, vely, angle, angular_velocity]

#game = gym.make('CartPole-v1')

#model = BasicModel(game.observation_space.shape[0], game.action_space.n, lambda x: x)


"""
while True:
    state = game.reset() 
    for i in range(999):
        action = model.train_iteration(state)
        st1, reward, done, _ = game.step(action[0])
        model.log(state, action[0], reward)
        # game.render()

        state = st1 
        if done: 
            reward_sum = model.calculate_reward()
            print(reward_sum)
            break

"""
# model.save_model("./hello.ckpt")
