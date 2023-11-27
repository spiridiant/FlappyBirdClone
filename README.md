# The Flappy Bird

## A recreation of the popular game *Flappy Bird*

The application presents a game which the user will control a bird, the object of the game
is to prevent the bird from falling to the ground or hitting a tube.

**A *models* list :**
- Bird: A class that represents the bird
- Tube： A class that represents the tubes which the bird **MUST NOT** hit
- FBGame: A class that represents the game board
- Score: A class that that represents the score a player earned
- LeaderBoard: A class that represents the leaderboard which the user score will be displayed on
- Position: A class that represents the position of an object in a game

### User Stories:
**Phase 0 :**
- As a user, I want to be able to create a new game.
- As a user, I want to be able to control the **Bird** through press of the *Space* key.
- As a user, I want to view the **Score** I earned during the present game.
- As a user, I want to be able to add **Score** with a *username* I chose to the **Leaderboard** after my game is finished.
- As a user, I want to be able to view the all the **Score**s in **Leaderboard**.
 
**Phase 2 :**
- As a user, I want to be able to press "s" during game to save my progress.
- As a user, I want to be able to resume my saved game by select the option at the start menu.
- As a user, I want to be able to save the **Leaderboard** by selecting the option at the start menu.
- As a user, I want to be able to load the saved **Leaderboard** by selecting the option at the start menu.


### Instructions for Grader

- You can add your **Score** to the **Leaderboard** after the game is over by entering your username into the prompt box and click *Add to Leaderboard*
- You can view the **Score**s in the **Leaderboard** by clicking **View Leaderboard** at Start Menu
- You can locate my visual component by clicking *Start New Game* at Start Menu
- During the game user can press *space bar* or click on window to make the bird flap
- You can save the state of the **Leaderboard** by clicking *Save* at Leaderboard Panel
- You can reload the state of the **Leaderboard** by clicking *Load* at Leaderboard Panel
- You can save the state of the **Game** by clicking save 's' at during a game
- You can reload the state of the **Game** by clicking *Resume Game* at Start Menu

### Phase 4: Task 2
**EventLog output example:**

    Mon Nov 27 02:46:07 PST 2023
    Leaderboard Loaded.


    Mon Nov 27 02:46:20 PST 2023
    Game started.


    Mon Nov 27 02:46:28 PST 2023
    Score 'Josh: 1' has been added to the Leaderboard


    Mon Nov 27 02:46:37 PST 2023
    Leaderboard Saved