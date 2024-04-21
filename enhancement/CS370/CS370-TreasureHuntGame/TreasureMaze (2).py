"""
TreasureMaze Module
Author: Stephen Owusu-Agyekum
Date: 2024-04-15
Version: V.2.1.0
Description: This module defines the TreasureMaze class representing the game environment for the Treasure Hunter game.
"""

# This class represents the environment for a Treasure Hunter game. It includes a maze object defined as a matrix.
import numpy as np

# Define a constant representing the shade for visited cells.
# Marking visited cells with a distinct shade aids in visualizing the exploration progress of the agent.
visited_mark = 0.8

# Define a constant representing the shade for the current pirate cell.
# Marking the current pirate cell with a distinct shade aids in tracking the pirate's position.
pirate_mark = 0.5

# Define constants representing directions the agent can move.
# These constants are essential for determining the agent's movement options within the maze.
LEFT = 0
UP = 1
RIGHT = 2
DOWN = 3

class TreasureMaze(object):
    """
    Represents the environment of a Treasure Hunter game.

    Parameters:
        maze (2D array): A two-dimensional Numpy array of floats between 0.0 and 1.0, where 1.0 corresponds to a free cell and 0.0 to an occupied cell.
        pirate (tuple): Initial pirate position (defaults to (0,0)).
    """

    def __init__(self, maze, pirate=(0,0)):
        """
        Initializes the TreasureMaze environment.

        Parameters:
            maze (2D array): A two-dimensional Numpy array representing the maze.
            pirate (tuple): Initial pirate position (defaults to (0,0)).
        """
        # Convert the maze into a Numpy array for manipulation.
        self._maze = np.array(maze)
        nrows, ncols = self._maze.shape
        # Define the target cell where the "treasure" is located.
        # Ensures that the target cell is not blocked, which is essential for winning the game.
        self.target = (nrows-1, ncols-1)
        # Generate a list of free cells where the pirate can move.
        self.free_cells = [(r,c) for r in range(nrows) for c in range(ncols) if self._maze[r,c] == 1.0]
        self.free_cells.remove(self.target)
        # Ensure that the target cell is not blocked, preventing an impossible game scenario.
        if self._maze[self.target] == 0.0:
            raise Exception("Invalid maze: target cell cannot be blocked!")
        # Ensure that the pirate starts on a free cell, ensuring a valid starting position.
        if pirate not in self.free_cells:
            raise Exception("Invalid Pirate Location: must sit on a free cell")
        # Reset the game state to initialize the pirate's position and other parameters.
        self.reset(pirate)

    def reset(self, pirate):
        """
        Resets the pirate's position.

        Parameters:
            pirate (tuple): Initial pirate position.
        """
        # Set the initial position of the pirate.
        self.pirate = pirate
        # Create a copy of the maze for manipulation.
        self.maze = np.copy(self._maze)
        nrows, ncols = self.maze.shape
        row, col = pirate
        # Mark the initial pirate position within the maze.
        self.maze[row, col] = pirate_mark
        # Define the initial state of the game, including the pirate's position and game mode.
        self.state = (row, col, 'start')
        # Define a minimum reward to prevent excessively long games.
        self.min_reward = -0.5 * self.maze.size
        # Initialize the total reward accumulated by the pirate.
        self.total_reward = 0
        # Keep track of visited cells to prevent revisiting the same cell.
        self.visited = set()

    def update_state(self, action):
        """
        Updates the state based on agent movement (valid, invalid, or blocked).

        Parameters:
            action (int): Action to take (LEFT, UP, RIGHT, DOWN).
        """
        nrows, ncols = self.maze.shape
        pirate_row, pirate_col, mode = self.state

        # Mark the current cell as visited to keep track of the pirate's exploration.
        if self.maze[pirate_row, pirate_col] > 0.0:
            self.visited.add((pirate_row, pirate_col))

        # Determine the valid actions that the pirate can take based on the current state.
        valid_actions = self.valid_actions()
                
        if not valid_actions:
            # If there are no valid actions, the pirate is blocked and cannot move.
            mode = 'blocked'
        elif action in valid_actions:
            # If the action is valid, update the pirate's position accordingly.
            mode = 'valid'
            if action == LEFT:
                pirate_col -= 1
            elif action == UP:
                pirate_row -= 1
            elif action == RIGHT:
                pirate_col += 1
            elif action == DOWN:
                pirate_row += 1
        else:
            # If the action is invalid, no change occurs in the pirate's position.
            mode = 'invalid'

        # Update the state with the new pirate position and mode.
        self.state = (pirate_row, pirate_col, mode)

    def get_reward(self):
        """
        Returns a reward based on the agent movement.

        Returns:
            float: Reward value.
        """
        pirate_row, pirate_col, mode = self.state
        nrows, ncols = self.maze.shape
        # Provide a high reward if the pirate reaches the target cell (winning condition).
        if pirate_row == nrows-1 and pirate_col == ncols-1:
            return 1.0
        # Apply a penalty if the pirate is blocked and unable to move.
        if mode == 'blocked':
            return self.min_reward - 1
        # Apply a penalty if the pirate revisits a previously visited cell to discourage backtracking.
        if (pirate_row, pirate_col) in self.visited:
            return -0.25
        # Apply a penalty if the pirate's action is invalid (e.g., attempting to move out of bounds).
        if mode == 'invalid':
            return -0.75
        # Provide a small negative reward for all other valid actions to encourage efficiency.
        if mode == 'valid':
            return -0.04

    def act(self, action):
        """
        Updates the state and total reward based on agent action.

        Parameters:
            action (int): Action to take (LEFT, UP, RIGHT, DOWN).

        Returns:
            tuple: Tuple containing environment state, reward, and game status.
        """
        # Update the state based on the action taken by the pirate.
        self.update_state(action)
        # Determine the reward obtained by the pirate for the action.
        reward = self.get_reward()
        # Accumulate the total reward obtained by the pirate over the course of the game.
        self.total_reward += reward
        # Determine the current status of the game based on the pirate's actions and rewards.
        status = self.game_status()
        # Return the current environment state, reward, and game status.
        envstate = self.observe()
        return envstate, reward, status
        
    def observe(self):
        """
        Returns the current environment state.

        Returns:
            array: Current environment state.
        """
        # Draw the environment and represent it as a flattened array for observation.
        canvas = self.draw_env()
        envstate = canvas.reshape((1, -1))
        return envstate

    def draw_env(self):
        """
        Draws the environment for visualization.

        Returns:
            array: Canvas representing the environment.
        """
        canvas = np.copy(self.maze)
        nrows, ncols = self.maze.shape
        # Clear all visual marks to prepare for redrawing.
        for r in range(nrows):
            for c in range(ncols):
                if canvas[r,c] > 0.0:
                    canvas[r,c] = 1.0
        # Draw the pirate at its current position within the environment.
        row, col, valid = self.state
        canvas[row, col] = pirate_mark
        return canvas

    def game_status(self):
        """
        Returns the game status.

        Returns:
            str: Game status ('win', 'lose', 'not_over').
        """
        # Determine if the pirate's total reward has fallen below the minimum threshold, resulting in a loss.
        if self.total_reward < self.min_reward:
            return 'lose'
        pirate_row, pirate_col, mode = self.state
        nrows, ncols = self.maze.shape
        # Determine if the pirate has reached the target cell, resulting in a win.
        if pirate_row == nrows-1 and pirate_col == ncols-1:
            return 'win'
        # If neither win nor loss conditions are met, the game is still ongoing.
        return 'not_over'

    def valid_actions(self, cell=None):
        """
        Returns the set of valid actions starting from the current cell.

        Parameters:
            cell (tuple): Coordinates of the cell. If None, uses the current pirate position.

        Returns:
            list: List of valid actions.
        """
        # Determine the valid actions that the pirate can take from the current cell.
        # Exclude actions that would move the pirate out of bounds or into blocked cells.
        if cell is None:
            row, col, mode = self.state
        else:
            row, col = cell
        actions = [0, 1, 2, 3]
        nrows, ncols = self.maze.shape
        if row == 0:
            actions.remove(1)
        elif row == nrows-1:
            actions.remove(3)
        if col == 0:
            actions.remove(0)
        elif col == ncols-1:
            actions.remove(2)
        if row > 0 and self.maze[row-1,col] == 0.0:
            actions.remove(1)
        if row < nrows-1 and self.maze[row+1,col] == 0.0:
            actions.remove(3)
        if col > 0 and self.maze[row,col-1] == 0.0:
            actions.remove(0)
        if col < ncols-1 and self.maze[row,col+1] == 0.0:
            actions.remove(2)
        return actions
