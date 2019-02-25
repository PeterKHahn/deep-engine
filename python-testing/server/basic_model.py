import tensorflow as tf 
import numpy as np
import gym 



class BasicModel:

    def __init__(self, state_size, num_actions, state_to_vector):
        self.sess = tf.Session()
        
        self.state_size = state_size
        self.num_actions = num_actions

        self.state_to_vector = state_to_vector

        self.max_step = 999
        self.gamma = 0.99

        self.state_vector = tf.placeholder(tf.float32, [None, self.state_size])
        self.rewards_vector = tf.placeholder(tf.float32, [None]) 
        self.actions_vector = tf.placeholder(tf.int32, shape=[None])

        self.actor_probs = self.actor() 
        self.state_value = self.critic()

        self.loss_tensor = self.loss_func()
        self.optimize_tensor = self.optimizer_func()


        self.sess.run(tf.global_variables_initializer())


        self.saver = tf.train.Saver()

        self.history = []


    def critic(self):
        layer = tf.layers.dense(self.state_vector, 100)
        layer = tf.layers.dense(layer, 1) 
        return layer


    def actor(self):
        layer = tf.layers.dense(self.state_vector, 100)
        layer = tf.layers.dense(layer, self.num_actions, activation=tf.nn.softmax)
        return layer

    def loss_func(self):
        advantage = self.rewards_vector - self.state_value 

        indices = tf.range(0, tf.shape(self.actor_probs)[0]) * self.num_actions + self.actions_vector 
        act_probs = tf.gather(tf.reshape(self.actor_probs   , [-1]), indices) 

        actor_loss = -1 * tf.reduce_mean(tf.log(act_probs) * advantage) 
        critic_loss = tf.reduce_mean(tf.square(advantage)) 

        loss = actor_loss + critic_loss 
        return loss 
         

    def optimizer_func(self):
        opt = tf.train.AdamOptimizer(learning_rate=1e-3)
        return opt.minimize(self.loss_tensor)
            


    def log(self, state, action, reward):
        """
        Adds a particular state action reward tuple 
        to the history list 
        """
        self.history.append((state, action, reward))



    def train_iteration(self, state_dictionary):
        state_vector = self.state_to_vector(state_dictionary)
        fd = {
            self.state_vector: np.matrix(state_vector) # TODO modify this
        }

        act_dist = self.sess.run([self.actor_probs], feed_dict = fd)
        action = np.random.choice(self.num_actions, 1, p=act_dist[0][0])

        return action
        

    def calculate_reward(self):
        discounted_rewards = self.discounted_rewards([x[2] for x in self.history])

        fd = {
            self.state_vector: [st[0] for st in self.history], 
            self.rewards_vector: discounted_rewards, 
            self.actions_vector: [a[1] for a in self.history]
        }

        l = self.sess.run([self.loss_tensor, self.optimize_tensor], feed_dict = fd)

        reward_sum = sum([x[2] for x in self.history]) 

        self.history = []

        return reward_sum




    def discounted_rewards(self, ls):

        rev = list(reversed(ls))
        for idx in range(1, len(ls)):
            rev[idx] = rev[idx] + self.gamma * rev[idx - 1]

        return list(reversed(rev))

    
    def reward(self, state):
        """
        Given a state, returns the reward 
        associated with that state 

        :state a dictionary that has currently 
            undefined parameters 
        """
        pass

  
    
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

game = gym.make('CartPole-v1')

model = BasicModel(game.observation_space.shape[0], game.action_space.n, lambda x: x)



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


# model.save_model("./hello.ckpt")
