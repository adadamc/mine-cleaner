import java.util.*;
import java.util.ArrayList;
import java.io.*;

public class mineCleaner
{
    Random rng = new Random();
    int size; //Size of the mine cleaner grid
    int dif; // The difficulty (1 = easy, 2 = medium, 3 = hard)
    int bombsInGame; // Amount of bombs in the game

    ArrayList<ArrayList<Integer>> tiles = new ArrayList<ArrayList<Integer>>();

    //Constructor
    public mineCleaner(int gridSize, int difficulty)
    {
        size = gridSize;
        dif = difficulty;
    }

    // Return the amount of bombs in the game
    public int howManyBombs()
    {
        return bombsInGame;
    }


    // Generate a list with entries signaling if a tile is a bomb or not. The chances of a tile being a bomb is based on the difficulty selected by the user.
    public ArrayList<ArrayList<Integer>> generateBombs()
    {
        tiles.clear(); // Reset the list incase it was used in previous games
        for(int i =0; i<=(size-1); i++) // For the amount of tiles in each row (for example with 100 (10x10) tiles, this will run 10 times)
        {
            tiles.add(new ArrayList<Integer>()); // Insert an array list in the array list (each inner list will contain the amount of tiles in each row)
            for(int y = 0; y<=(size-1); y++)
            {
                int randomChance = rng.nextInt(10);
                int decider = 0;
                if(dif == 1) // Easy mode
                {   
                    if(randomChance == 2 || randomChance == 4) // 2/10 chances for bomb (AT FIRST)
                    {
                        decider = 1;
                    }
                    else
                    {
                        decider = 0;
                    }

                }
                else if(dif == 2)
                {
                    if(randomChance == 2 || randomChance == 3 || randomChance == 6 || randomChance == 7) // 4/10 chances for bomb (AT FIRST)
                    {
                        decider = 1;
                    }
                    else
                    {
                        decider = 0;
                    }
                }
                else if(dif == 3)
                {
                    if(randomChance != 2 && randomChance != 4 && randomChance != 6) // 7/10 chances for bomb (AT FIRST)
                    {
                        decider = 1;
                    }
                    else
                    {
                        decider = 0;
                    }
                }

                if(decider == 1) // If space is a bomb
                {
                    bombsInGame = bombsInGame + 1;
                }

                if((i-1) >= 0 && ((tiles.get(i).size()-1) >= 0))
                {
                    if(tiles.get(i-1).get(tiles.get(i).size()-1) == 0 || tiles.get(i-1).get(tiles.get(i).size()) == 0)
                    {
                        int thenewRand = rng.nextInt(2);
                        if(thenewRand != 1 && decider == 1) // Chance to get rid of a bomb
                        {
                            decider = 0;
                            bombsInGame = bombsInGame - 1;
                        }
                    }
                }
                tiles.get(i).add(decider); // Add the final bomb or non-bomb decision to the array
            }
        }
        return tiles; // To let client know we finished generating the bombs!
    }

    public int getSize()
    {
        return size;
    }

    //Checks if the space is legal, within the array size (not under 0 and not over the size)
    public boolean isLegal(int xVal, int yVal)
    {
        if(xVal >= 0 && yVal >= 0 && xVal < size && yVal < size)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // Some old code may not currently have safety mode (first bomb safe) as an option, so this will convert it to make it compatible with the new function which does include it.
    public int isBomb(int spot, int times, int amt, ArrayList<ArrayList<Integer>> theAr)
    {
        int gotit = isBomb(spot, times, amt, theAr, false, false);
        return gotit; // Sending back
    }

    // Checks if a space is a bomb (return of 10 = is a bomb), otherwise it will return the amount of bombs touching the space
    public int isBomb(int spot, int times, int amt, ArrayList<ArrayList<Integer>> theAr, boolean firstSafe, boolean used)
    {
        boolean addonek = false; // Fix for the non-first bomb
        tiles = theAr;
        int xVal = spot%size;
        double yVal = Math.floor(spot/size);
        boolean wentIn = false;

        try
        {
            if(firstSafe && !used) // If user has safety mode on and it was not already used
            {
                if(tiles.get(xVal).get((int)yVal) == 1)
                {
                    addonek = true; // We will add 1000 to the return to signify that it would have been a bomb but is no longer one
                    tiles.get(xVal).set( ((int)(yVal)), 0); // Tile will no longer be a bomb
                }
            }
        }
        catch(Exception e)
        {

        }

        try
        {
            if(tiles.get(xVal).get((int)yVal) == 1) // Bomb in the spot originally pressed!
            {
                wentIn = true;
                return 10; 
            }
        }
        catch(Exception e)
        {

        }

        if(times <= 8 && wentIn == false) // Recursion stop statement (wentIn checks to see if it was a bomb (did if statement in try catch))
        {
            times += 1;
            int xInt = (int)xVal;
            int yInt = (int)yVal;

            //(int spot, int times, int amt, ArrayList<ArrayList<Integer>> theAr)
            amt = isBomb(spot, times, amt, theAr);

            switch(times) // Checking the spaces around the place that was clicked, since there are 8 times this occurs (there are 8 cases)
            {
                case 1: // 1/8 blocks touching checked
                    if(isLegal(xInt-1,yInt-1))
                    {
                        if(tiles.get(xInt-1).get(yInt-1) == 1)
                        {
                            if(!addonek)
                            {
                                return amt+1;
                            }
                            else
                            {
                                return amt+1+1000;
                            }
                        }
                        else
                        {
                            if(!addonek)
                            {
                                return amt+0;
                            }
                            else
                            {
                                return amt+1000;
                            }
                        }
                    }
                    else
                    {
                        if(addonek)
                        {
                            return amt + 1000;
                        }
                    }
                    break; // End of case 1


                case 2:
                    if(isLegal(xInt,yInt-1))
                    {
                        if(tiles.get(xInt).get(yInt-1) == 1)
                        {
                            return amt+1;
                        }
                        else
                        {
                            return amt+0;
                        }
                    }
                    break; // End of case 2


                case 3:
                    if(isLegal(xInt+1,yInt-1))
                    {
                        if(tiles.get(xInt+1).get(yInt-1) == 1)
                        {
                            return amt+1;
                        }
                        else
                        {
                            return amt+0;
                        }
                    }
                    break; // End of case 3


                case 4:
                    if(isLegal(xInt-1,yInt))
                    {
                        if(tiles.get(xInt-1).get(yInt) == 1)
                        {
                            return amt+1;
                        }
                        else
                        {
                            return amt+0;
                        }
                    }
                    break; // End of case 4


                case 5:
                    if(isLegal(xInt+1,yInt))
                    {
                        if(tiles.get(xInt+1).get(yInt) == 1)
                        {
                            return amt+1;
                        }
                        else
                        {
                            return amt+0;
                        }
                    }
                    break; // End of case 5


                case 6:
                    if(isLegal(xInt-1,yInt+1))
                    {
                        if(tiles.get(xInt-1).get(yInt+1) == 1)
                        {
                            return amt+1;
                        }
                        else
                        {
                            return amt+0;
                        }
                    }
                    break; // End of case 6


                case 7:
                    if(isLegal(xInt,yInt+1))
                    {
                        if(tiles.get(xInt).get(yInt+1) == 1)
                        {
                            return amt+1;
                        }
                        else
                        {
                            return amt+0;
                        }
                    }
                    break; // End of case 7


                case 8:
                    if(isLegal(xInt+1,yInt+1))
                    {
                        if(tiles.get(xInt+1).get(yInt+1) == 1)
                        {
                            return amt+1;
                        }
                        else
                        {
                            return amt+0;
                        }
                    }
                    break; // End of case 8

            }
        }

    // If 10 is returned, that means it is a bomb, otherwise, it is the number of bombs touching it.
    if(amt !=0)
    {
        return amt;
    }
    return 0;
    }
}