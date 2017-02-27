/**
 * GenY Programming Club - Tasks Module
 * @author  Shivam Mukherjee
 * @version 2.0 
 * Task 5
 */

// Here's all the imports I'd need for this file
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  5   )    }        ]                |
|___________________________________________________________________________|

 I decided to start afresh. The Pokemon class encapsulates most of the ideas that were suggested in the instructions list. However, I added some of my own methods, and even though this isn't anywhere near what actually any of the original games or Pokemon Go itself accomplishes or what becomes the whim and fancy of skilled developers who recreate these games or mod them, I tried to capture some of the most basic facets that the Pokemon genre is known for, such as Pokemon types and the Rock-Paper-Scissors style mechanic that renders some types stronger than other types but weaker to still other types, adding to the Poke-feel of this pet project.

*/

/***********************
ABSTRACT CLASS UTILITIES
************************
 An abstract class to trivialise some things; rest assured, Pokemon, Player, and PGInstance extend nothing else, so it was safe to not implement Utilities as an interface.
*/
abstract class Utilities
{
    Random  random = new Random();
    Scanner input  = new Scanner(System.in);

    String intify(double in)
    {
        return String.format("%.0f", in);
    }

    // clones a list of Pokemon by copying each value
    static ArrayList<Pokemon> cloneList(ArrayList<Pokemon> from)
    {
        ArrayList<Pokemon> to = new ArrayList<>(from.size());

        for (Pokemon element : from)
        {
            to.add(new Pokemon(element));
        }

        return to;
    }
}

/************
CLASS VERBOSE
*************
 Enables longer optional messages to be printed.
*/

class Verbose
{
    public static boolean on = false;

    static void println(String string)
    {
        if (on)
        {
            System.out.println(string);
        }
    }
}

/*****************
INTERFACE CALLBACK
******************
 An interface that represents a function that doesn't have arguments and results (like Runnable).
*/
@FunctionalInterface
interface Callback
{
    // this function is what constitutes the lambda or method reference to be stored
    void call();
}