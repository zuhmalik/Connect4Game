# Connect4Game

# Overview
This project is an implementation of the classic Connect4 game in Java. Connect4, also known as Four in a Row, is a two-player board game where players take turns dropping colored discs into a grid, aiming to form a line of four discs of their color either vertically, horizontally, or diagonally. This implementation provides an interactive text-based experience of the game, offering users a chance to enjoy the strategic gameplay in a command-line environment.

# Features
Interactive Gameplay: Players can take turns dropping their colored discs into the game grid by specifying the column number.
Win Detection: The program detects when a player has formed a line of four discs of their color, signaling the end of the game.
Grid Visualization: The current state of the game board is displayed after each move, helping players visualize the ongoing gameplay.
Input Validation: User inputs are validated to ensure the game runs smoothly and to prevent illegal moves or crashes.
Column Full Detection: The game alerts players when a column is already full and prevents them from making invalid moves.
Restart Option: After the game ends, players can choose to restart and play again.

# Technical Details

Language: Java
MVC Architecture: The project follows the Model-View-Controller (MVC) design pattern, enhancing code modularity and maintainability.
Board Representation: The game board is represented as a 2D array, where each cell can hold a disc of the specified color.
User Interaction: Input from players is obtained using the Scanner class, allowing for interaction through the command line.
Winning Condition Check: The game checks for winning conditions (horizontal, vertical, and diagonal lines) after each player's move.
Input Validation: The program validates user inputs to ensure they are within the valid range of columns and not attempting to play in a full column.
Main Loop: The main game loop runs until a player wins or the game ends in a draw, allowing continuous play.

# How to Play
Clone the repository to your local machine.
Compile the Java source files using javac.
Run the compiled .class file using java Connect4.
Follow the on-screen instructions to take turns and play the game.
When the game ends, you have the option to restart or exit.

# Future Enhancements
Implement a graphical user interface (GUI) using JavaFX for a more visually appealing gameplay experience.
Integrate an AI opponent with varying levels of difficulty for single-player mode.
Add an option for players to choose their colors and names.
Acknowledgments
The project was inspired by the classic Connect4 game and is intended for educational purposes to practice Java programming concepts.

# Contributing
Contributions to this project are welcome! Feel free to fork the repository, make improvements, and submit pull requests.

# License
This project is licensed under MIT License.
