import tensorflow as tf 



class PlayerModel:

    """
    This class returns a tensor that effectively encodes a player
    """

    def __init__(self):
        self.num_players = 2

        self.hitbox_vector_size = 10
        self.hitbox_encoding_size = 1024

        self.ecb_vector_size = 10
        self.ecb_encoding_size = 1024

        self.hurtbox_vector_size = 10
        self.hurtbox_encoding_size = 1024


        # A tensor of [batch_size, num_hitboxes, hitbox_vector_size]
        self.hitbox_input = tf.placeholder(tf.float32, [None, None, self.hitbox_vector_size])

        self.hurtbox_input = tf.placeholder(tf.float32, [None, None, self.hitbox_vector_size])

        self.ecb_input = tf.placeholder(tf.float32, [None, self.ecb_vector_size])
        

        self.grounded_input = tf.placeholder(tf.bool, [None, 1])
        self.damage_input = tf.placeholder(tf.float32, [None, 1])
        self.hitbox_active_input = tf.placeholder(tf.bool, [None, 1])



        self.hitbox_tensor = self.hitbox_tensor_func()
        self.hugrtbox_tensor = self.hurtbox_tensor_func()
        self.ecb_tensor = self.ecb_tensor_func
    
    def hitbox_tensor_func(self):
        layer1 = tf.layers.dense(self.hitbox_input, self.hitbox_encoding_size)
        layer2 = tf.math.reduce_mean(layer1, axis=1)
        return layer2

    def hurtbox_tensor_func(self):
        layer1 = tf.layers.dense(self.hurtbox_input, self.hurtbox_encoding_size)
        layer2 = tf.math.reduce_mean(layer1, axis=1)
        return layer2

    def ecb_tensor_func(self):
        return tf.layers.dense(self.ecb_input, self.ecb_encoding_size)
        

    def process_state(self, state):
        """
        State is a dictionary
        """
        hitbox = state['hitbox']
        hurtbox = state['hurtbox']
        hitbox_active = state['hitboxActive']

        damage = state['damage']
        grounded = state['grounded']
        ecb = state['ecb']
        
        



    def read_players(self, lines):

        for i in range(self.num_players):

            pass

print('done')