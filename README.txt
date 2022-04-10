CS5004 Creative Lab Summary
Group Members: Duc Anh Than, Mia Gates


For this project, we created a game with three heroes and three bad guys that would fight against each other, aligning with the project description guidelines. Each hero has different weapons they can choose from, and different types of attack. In addition, we added images and sounds to the game. 


We use a loop to run our game, iterating through each of the heroes, and allowing the user to select which attack type they want to implement. Then, each of the heroes responds with an attack. We use performAttack() and update() methods to execute the attack and continue the game after the input is selected. The heroes require no user input, they just use a normal attack. When all of the members of one team have died, the game stops running, and the system prints which team won. 


In our visuals, we use JPanel and JFrame to set up a character selection panel and a game panel. In the character selection panel, the user sees each of the heroes and is able to select which weapon they will each have. We have enabled buttons for this purpose. In the game panel, we set a background and then display each of the characters on the panel. The screen above instructs the user to press different keys in order to select an attack type. Then, the screen displays the amount of attack that was dealt by each character. It does this for the heroes too. Additionally, bars above each character’s head display the amount of health they have left. When one team is fully eliminated, the screen shows which team has won. The game panel also has a background that sets the stage for the Epic Battle. 


Additionally, we added sounds for each different weapon, and a different sound for the bad guys’ attack. There is also a separate sound that is triggered when a character dies. 


The game loop, images, and sounds extend our project beyond the scope of what was detailed in the instructions, and adds significant creative components.
