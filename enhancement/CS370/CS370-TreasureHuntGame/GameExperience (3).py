"""
GameExperience Module
Author: Stephen Owusu-Agyekum
Date: 2024-04-15
Version: V.2.1.0
Description: This module contains the GameExperience class for managing agent experiences and learning in the Treasure Hunter game.
"""

# This class stores the episodes, all the states that come in between the initial state and the terminal state. 
# This is later used by the agent for learning by experience, called "exploration". 
# It also provides an overview of the class purpose, which is to store episodes for reinforcement learning.
# Clearly state the purpose of the class to ensure understanding and clarity for developers.

import numpy as np

class GameExperience(object):
    
    # model = neural network model
    # max_memory = number of episodes to keep in memory. The oldest episode is deleted to make room for a new episode.
    # discount = discount factor; determines the importance of future rewards vs. immediate rewards
    # To document the parameters of the class constructor for clarity and usage guidance.
    # To specify the purpose and default values of parameters to guide users during instantiation.

    def __init__(self, model, max_memory=100, discount=0.95):
        self.model = model
        self.max_memory = max_memory
        self.discount = discount
        self.memory = list()
        self.num_actions = model.output_shape[-1]
    
    # Stores episodes in memory
    # Describe the function responsible for storing episodes in memory.
    # Provide a clear explanation of the purpose and operation of the 'remember' method.

    def remember(self, episode):
        # episode = [envstate, action, reward, envstate_next, game_over]
        # memory[i] = episode
        # envstate == flattened 1d maze cells info, including pirate cell (see method: observe)
        self.memory.append(episode)
        if len(self.memory) > self.max_memory:
            del self.memory[0]

    # Predicts the next action based on the current environment state        
    # This explain the purpose of the method for predicting the next action.
    # This is to clarify the role of the 'predict' method in determining the next action based on the current state.

    def predict(self, envstate):
        # Predicts the Q-values for each action given the current environment state.
        return self.model.predict(envstate)[0]

    # Returns input and targets from memory, defaults to data size of 10
    # Describe the function for retrieving input and target data from memory.
    # Specify the behavior of the 'get_data' method and its default parameters for data retrieval.

    def get_data(self, data_size=10):
        # Determines the size of the environment state (1st element of episode).
        # envstate 1d size (1st element of episode)
        env_size = self.memory[0][0].shape[1]   
        mem_size = len(self.memory)
        data_size = min(mem_size, data_size)
        inputs = np.zeros((data_size, env_size))
        targets = np.zeros((data_size, self.num_actions))
        for i, j in enumerate(np.random.choice(range(mem_size), data_size, replace=False)):
            envstate, action, reward, envstate_next, game_over = self.memory[j]
            inputs[i] = envstate
            # There should be no target values for actions not taken.
            targets[i] = self.predict(envstate)
            # Q_sa is the maximum Q-value for the next state.
            # Q_sa = derived policy = max quality env/action = max_a' Q(s', a')
            Q_sa = np.max(self.predict(envstate_next))
            if game_over:
                targets[i, action] = reward
            else:
                # Updates the target value based on the Bellman equation.
                # reward + gamma * max_a' Q(s', a')
                targets[i, action] = reward + self.discount * Q_sa
        return inputs, targets
