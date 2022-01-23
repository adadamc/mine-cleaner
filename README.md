# Mine Cleaner

## Table of Contents
[Requirements and Code Info](#requirements-and-code-info)
[Description](#description)
[Running the Program](#running-the-program)
[Play Guide](#play-guide)

## Requirements and Code Info
Programming Language: Java
Version Used / Tested With: openjdk version 17.0.1
Other Requirements: None
Note: To play the game with large grids (for example 20x20) a display with a vertical resolution of 1080 pixels (1080p) or higher is recommended

## Description
Your objective in Mine Cleaner is to locate all the bombs without hitting them. Upon selecting a tile that is not a bomb you will be able to see how many bombs are touching (including diagonally) that tile from 0 to 8. You can use this information to help determine good guesses for where to click next. Use the flag tool to mark where bombs are, this will also prevent you from clicking these tiles accidently which would cause the game to end. To win make sure you flag all bombs and click on all tiles that are not bombs.

## Running the Program
To start the program run the client (clientMine.java).

## Play Guide
Upon running the game you will be greeted with two options. You may play the game by clicking the `Mine Cleaner` button. To change your settings click the `Settings` button.

### Settings
The settings menu provides some basic options for the game.

The `Dark Theme` and `Light Theme` button allows you to change the theme of the graphical interface. Below are some comparisons.
![The settings menu in light theme and dark theme][resources/settingsMenuComparison.png]
![The size and difficulty selection menu in light theme and dark theme][resources/sizeSelectComparison.png]
![The game interface in light theme and dark theme][resources/inGameComparison.png]

The `Safety` button allows you to toggle the safety mode setting. When enabled, clicking a bomb on your first turn will remove that bomb and allow you to continue the game. This setting prevents the user from losing on the first turn. When enabled and the first click is on a bomb, the count in the top middle of the screen (above the `Flag Mode` button) will be updated to reflect this. The tile will also display the number of touching bombs in green as shown below.
![Green text appears when you click a bomb on the first turn with safety mode enabled][resources/greenTile.png]

### Gameplay
Upon clicking the `Mine Cleaner` button you will be taken to the size select screen. Select the grid size, you **may select a size between 3 to 20**. The grid size you enter will be squared to determine the number of tiles. For example, selecting 5 will produce a 5x5 grid consisting of 25 tiles.

There are also three difficulties to select from. Click the `Easy`, `Medium`, or `Hard` button to make your selection. The selection made will determine how likely it is for a tile to be a bomb on your grid.

Upon selecting the grid size and difficulty click the `Submit` button.

When the game starts click on a tile. If the tile becomes invisible that means there are 0 bombs touching it, the tiles touching will either be removed and this process will continue (if they also have no bombs touching) or display a number indicating the amount of bombs touching that tile. If there are bombs touching the tile you select a number will appear indicating the amount of bombs touching.

You can use the numbers on tiles to help you determine if a tile is a bomb or not. If you believe a tile is a bomb you can enable `Flag Mode` in the top middle of the screen and click on the tile to mark it as such. The tile will become purple, the bombs left text will update and clicking that tile with bomb mode will not do anything preventing you from losing the game accidently.

After using flag mode, you can go back into bomb mode by clicking the `Bomb Mode` button. If you are sure a tile is not a bomb or have to guess due to lack of conclusive evidence, you can click another tile to get more information.

In the below image:
**missing tiles**: no bombs are touching the tile
**tiles with text**: the number displayed is the amount of touching tiles with bombs (diagonal tiles count)
**purple tiles**: marked as bombs using flag mode, you can un-mark these in flag mode

![What a typical game may look like while playing][resources/mineCleanerGame.png]

Upon losing the game the following screen will appear and this info can be gathered:
**brightest red tile**: the bomb you clicked that caused you to lose the game
**next brightest red tiles**: bombs in the game that you did not flag or click
**least bright red tiles**: tiles not clicked that are not bombs

**green tiles**: tiles that you marked in flag mode correctly (are bombs)
**yellow tiles**: tiles that you marked in flag mode incorrectly (are not bombs)

The time the game took is displayed in the top middle of the screen. Click the `Return` button below the timer to go back to the main menu.

![What is displayed after losing a game][resources/lostGame.png]

Upon winning a simple win screen will appear. The time it took to win will be displayed in minutes, in the following example it took 6 seconds to win. Click the `Back to Main Menu` button to return to the menu.

![What is displayed after winning a game][resources/winScreen.png]

