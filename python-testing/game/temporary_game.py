import dqn
import gym


game = gym.make('Acrobot-v1')

game._max_episode_steps = 200


model = dqn.DQN('Acrobot-v1')

print('Practice')

for i in range(5000):

    st = game.reset()
    model.reset()

    for j in range(300):
        
        action = model.query_action(st, 'PRACTICE')
        st1, r, done, _ = game.step(action)
        model.game_response(st, action, r, st1, done, 'PRACTICE')


        st = st1

        if done: 

            break
    
    
    model.finish_game('PRACTICE')


print('Training')


current_epsilon = 1.0 

for i in range(600):
    print("Starting iteration: ", i)

    st = game.reset()
    model.reset()

    total_reward = 0

    model.set_epsilon(current_epsilon)

    if i % 1 == 0:
        current_epsilon *= 0.99
    if i < 20: 
        model.set_epsilon(1.0)
    else:
        model.set_epsilon(current_epsilon)


    print(model.epsilon)

    for i in range(300):

        action = model.query_action(st, 'TRAIN')
        st1, r, done, _ = game.step(action)
        model.game_response(st, action, r, st1, done, 'TRAIN') 


        st = st1
        total_reward += r

        game.render()

        if done: 
            break

    model.finish_game('TRAIN')


    print(total_reward)

