# Battle-Ship
Battle-Ship is a java program that plays battleship against the user. It includes machine learning algorithms that can find the user's ships

The functions of the code and its technicalities are documented within the program :)
-----------------------------------------------------------------------------------------------------------------
Program design:
My Battleship program is designed to act as a PvE platform to play with a player. It should simulate the experience
of the battleship boardgame but against the computer instead of another player. The player should set up a board 
with ships placed on (on a piece of paper or on a battleship boardgame set) it and play against the computer 
in that way.

Features
- Easy and advanced difficulty (includes advances algorithms to find player ships)
- A readme.txt 
- GUI and rules
-----------------------------------------------------------------------------------------------------------------
Commons questions

Q: What the purpose of readme.txt?
A: To make sure the computer doesn't cheat as it records the location of the computer ships when the game starts

Q: How does search work?
A: Search is the algorithm used to find ships when none are currently found and all found ships are sank. It uses 
   combinatorics to find the number of ways remaining ships can exists within a given a square and gives it a 
   score. The program then hits the the square with the highest score. If there is a tie the program will hit a
   random one. 
   
   ==============================================================================================================
   
   Example: 
   Note: 0s are unhit spaces, 1-5 are ships, H signifies a spot that has already been hit. Letters are used to 
   illustrate squares
   
   Remaining ships: Destroyer(2 spaces), Battleship(4 spaces)
   Board:
   
      0 0 0 0 0
      0 H A H 0
      0 0 B 0 0
      0 0 0 0 0
      0 0 0 0 0
   
   If we consider the square A, ships can exist on A like this:
   
      0 0 2 0 0     0 0 0 0 0     0 0 4 0 0     0 0 0 0 0
      0 H 2 H 0     0 H 2 H 0     0 H 4 H 0     0 H 4 H 0
      0 0 0 0 0     0 0 2 0 0     0 0 4 0 0     0 0 4 0 0 
      0 0 0 0 0     0 0 0 0 0     0 0 4 0 0     0 0 4 0 0
      0 0 0 0 0     0 0 0 0 0     0 0 0 0 0     0 0 4 0 0
   
   As a result, A will have a score of 4. If we applie a similar method, we will find that B has a score of 8.
   This means that the computer will favor hitting square B over square A. 
   
   Note: knowing the remaining ships is important. For example, if only the carrier is left(5 spaces) it would
   be useless to hit any segment that cannot contain a carrier.
   
   ==============================================================================================================
   
   
 Q: How does hunt work? What is hunt report? What is hun direction?
 
 A: Hunt is the algorithm that sinks the ship after hunt has found it. It uses the 2D array huntReport (5 by 5) 
    and huntDirection (1 by 5) to record and process hit data. 
    
    =============================================================================================================
    
    HuntReport:
    
    [0] The first index of huntReport is to store the type(length) of the ship. For example, the huntReport of 
        The destroyer will be 2.
    
    [1] The second index of huntReport is to store whether or not the ship has been found. 1 states that the 
        ship has been found and 0 indicates that it has not
        
    [2] and [3] stores the x and y coordinates of the first strike that hits the ship
    
    [4] The fifth index stores whether or not the orientation of the ship has been set. 1 means that the 
        orientation has been set and 0 means that it has not
        
    ==============================================================================================================
    
    HuntDirection: Stores the direction of the ship. It is randomly generated when the direction of the ship is
                   not known yet. Direction is one of u, d, l ,r
                   
                   in this game it is set as              
                                                           left
                                                       down     up
                                                           right
                                                           
    ==============================================================================================================
    
    Algorithm summary: Every time the computer fires, it checks the direction listed in HuntDirection and hits in
                       that direction. (Note: hitting means that the computer keeps checking in that direction until 
                       it finds an unhit square. If the computer hits the same ship again, it locks the ship
                       orientation. If the ship sinks and there are no more ships to hunt, the computer exists to search
                       mode. If the computer misses, it will check if the orientation is locked. If it is, it will
                       flip the direction (ex. up -> down). Otherwise it will rotate the direction (ex up -> right).
                       
    ==============================================================================================================
    
    
 Q: What is maxPriority and priorityIndex?
 
 A: They are a way to deal with this rare specific scenario.
 
    ==============================================================================================================
    
    Note: 0s are unhit spaces, 1-5 are ships, H signifies a spot that has already been hit. Letters are used to 
    illustrate squares
    
    0 0 0 0 0       0 0 0 0 0
    0 0 3 0 0       0 0 3 0 0 
    0 0 3 0 0  ->   0 0 3 0 0 
    0 0 3 0 0       0 0 H 0 0 
    0 2 H 0 0       0 2 H 0 0 
    
    In the case that the computer finds another ship while hunting for the previous one, it need a variable to 
    keep track of how many ships it need to keep track of (maxPriority) and which ship is it currently trying
    to hunt (priorityIndex).
    
    ==============================================================================================================
                                                      
        
   
