import pymunk
import pymunk.pygame_util

import pyglet
import pygame

import time

from pymunk.pyglet_util import DrawOptions

import numpy as np

from default_bot import DefaultBot

from dqn import DQN



class Parameters:

    def __init__(self):
        self.action_space = 5
        self.state_space = 8
        


class Game:

    
    
    def __init__(self, p1_bot, p2_bot):

        self.width = 300
        self.height = 200




        self.space = self.set_space()

        self.p1_bot = p1_bot # P1 bot is the default bot
        self.p2_bot = p2_bot # P2 bot is the training bot

        self.player1 = self.create_player()
        self.player2 = self.create_player()

        self.p1_action_lambdas = self.create_lambdas(self.player1)
        self.p2_action_lambdas = self.create_lambdas(self.player2)

        self.max_vel = 0



        pygame.init() 
        self.screen = pygame.display.set_mode((1200, 768))


    def reset(self):
        # pygame.quit()
        self.space = self.set_space() 
        self.player1 = self.create_player()
        self.player2 = self.create_player() 

        self.p1_action_lambdas = self.create_lambdas(self.player1)
        self.p2_action_lambdas = self.create_lambdas(self.player2)

        pygame.init()
        # self.screen = pygame.display.set_mode((1200, 768))
 



    def create_lambdas(self, player):
        """
        Warning, directions don't matter but may not be accurate
        """

        force_mag = 5

        f_left = lambda: player.apply_impulse_at_local_point((force_mag, 0), (0,0))
        f_right = lambda: player.apply_impulse_at_local_point((0, force_mag), (0,0))
        f_up = lambda: player.apply_impulse_at_local_point((-force_mag, 0), (0,0))
        f_down = lambda: player.apply_impulse_at_local_point((0, -force_mag), (0,0))
        f_stay = lambda: None

        return [f_left, f_right, f_up, f_down, f_stay]


    def set_space(self):
        space = pymunk.Space() 
        space.gravity = (0,0)
        return space

    def create_player(self):
        player1 = pymunk.Body(1, 1666)
        player1.position = (self.width / 2, self.height / 2)

        poly = pymunk.Circle(player1, radius=50)
        self.space.add(player1, poly)

        # player1.apply_force_at_local_point((100,0), (0,0))


        return player1
    
    def update(self, dt):
        self.space.step(dt)

        




    def player_state(self, player):
        pos = player.position 
        vel = player.velocity 

        self.max_vel = max(vel.x, self.max_vel)

        return [(pos.x / (self.width / 2) - 1) * 10, (pos.y / (self.height / 2) - 1) * 10, vel.x / 10, vel.y / 10]
            

    def player_states(self, render=False):
        p1_temp = self.player_state(self.player1)
        p2_temp = self.player_state(self.player2)

        p1_state = p1_temp + p2_temp 
        p2_state = p2_temp + p1_temp
        return p1_state, p2_state

    def iter(self, mode, render=False, train=False):



        p1_state, p2_state = self.player_states()



        p1_action = self.p1_bot.query_action(p1_state, mode)
        p2_action = self.p2_bot.query_action(p2_state, mode)

        
        if render:

            game.render(p1_action, p2_action)
            time.sleep(0.002)



        self.p1_action_lambdas[p1_action]() 
        self.p2_action_lambdas[p2_action]()


        self.step()

        p1_state1, p2_state1 = self.player_states()
        


        p1_reward, p2_reward, done = self.calculate_reward()

        # train only the p2 bot
        self.p1_bot.game_response(p1_state, p1_action, p1_reward, p1_state1, False)
        self.p2_bot.game_response(p2_state, p2_action, p2_reward, p2_state1, train) 



        return p1_reward, p2_reward, done 
    

    def episode(self, mode, render=False, train=False):
        self.reset()

        self.p1_bot.reset() 
        self.p2_bot.reset()

        p1_total = 0 
        p2_total = 0
        
        for i in range(1000):
            p1_reward, p2_reward, done = self.iter(mode, render, train=train)
            p1_total += p1_reward
            p2_total += p2_reward
            if done:
                break

        # print(p1_total, p2_total)


        self.p1_bot.finish_game(False)
        self.p2_bot.finish_game(train)


        return p1_total, p2_total


    def calculate_reward(self):

        def player_oob(player):
            p1_pos = player.position

            return p1_pos.x < 0 or p1_pos.x > self.width or p1_pos.y < 0 or p1_pos.y > self.height


        p1_oob = player_oob(self.player1)
        p2_oob = player_oob(self.player2)


        p1_score = 0
        p2_score = 0

        if p1_oob:
            p1_score -= 100
            p2_score += 100

        if p2_oob:
            p2_score -= 100
            p1_score += 100

        return p1_score, p2_score, p1_oob or p2_oob



    def render(self, p1_action, p2_action):
        self.screen.fill((255, 0, 0))

        surface = pygame.Surface((self.width,self.height))
        options = pymunk.pygame_util.DrawOptions(surface)
        # options = pymunk.SpaceDebugDrawOptions()
        options.flags = pymunk.SpaceDebugDrawOptions.DRAW_SHAPES

        self.space.debug_draw(options)

        self.screen.blit(surface, (150, 15))


        
        if p2_action == 4:
            pygame.draw.circle(self.screen, (255, 0, 255), (100, 100), 20)
        else:
            pygame.draw.circle(self.screen, (0, 0, 255), (100, 100), 20)


        if p2_action == 0:
            pygame.draw.circle(self.screen, (255, 0, 255), (150, 100), 20)
        else:
            pygame.draw.circle(self.screen, (0, 0, 255), (150, 100), 20)



        if p2_action == 3:
            pygame.draw.circle(self.screen, (255, 0, 255), (100, 150), 20)
        else:
            pygame.draw.circle(self.screen, (0, 0, 255), (100, 150), 20)


        if p2_action == 1:
            pygame.draw.circle(self.screen, (255, 0, 255), (100, 50), 20)
        else:
            pygame.draw.circle(self.screen, (0, 0, 255), (100, 50), 20)


        if p2_action == 2:
            pygame.draw.circle(self.screen, (255, 0, 255), (50, 100), 20)
        else:
            pygame.draw.circle(self.screen, (0, 0, 255), (50, 100), 20)



        pygame.display.flip()


        pass




    def step(self):
        
        self.space.step(0.1)


if __name__ == '__main__':
    parameters = Parameters()

    dummy_bot = DefaultBot(parameters)
    cool_bot = DQN(parameters)


    for game_run in range(2):
        game = Game(dummy_bot, cool_bot)

        with open('scores.txt', 'a+') as f:
            f.write("new game" + '\n')

        for i in range(1000):
            if i % 50 == 0:
                print("Starting iteration: ", i)
                
            game.episode("PRACTICE")


        scores = []

        for i in range(20000):


            if i % 100 == 0:
                print("Starting iteration: ", i)

                epsilon = game.p2_bot.epsilon
                print('epsilon', epsilon)
                game.p2_bot.epsilon = 0

                for i in range(25):
                    p1_reward, p2_reward = game.episode("TRAIN", render=True)
                    scores.append(p1_reward > p2_reward)



                wins = scores.count(False) / len(scores)
                print("player 2 wins", wins) 

                with open('scores.txt', 'a+') as f:
                    f.write(str(wins) + '\n')
                
                scores = []

                game.p2_bot.epsilon = epsilon
            else:
                p1_reward, p2_reward = game.episode("TRAIN", train=True)


        print('done with game')
        cool_bot.save_model(game_run)

        dummy_bot = cool_bot 
        cool_bot = DQN(parameters)

                

            






