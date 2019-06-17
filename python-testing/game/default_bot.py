import numpy as np 

class DefaultBot:
    

    def __init__(self, parameters):
        self.action_space = parameters.action_space
        self.state_space = parameters.state_space
        pass

    def query_action(self, st, mode):
        return np.random.randint(self.action_space)


    def game_response(self, state, action, reward, state1, train=False):
        pass 

        
    def finish_game(self, mode):
        pass


    def reset(self):
        pass