/**
 * GenY Programming Club - Tasks Module
 * @author  Shivam Mukherjee
 * @version 2.0
 * Java SE 8 only
 * Task 5
 */

// Here's all the imports I'd need for this file
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  5   )    }        ]                |
|___________________________________________________________________________|

 I decided to start afresh. The Pokemon class encapsulates most of the ideas that were suggested in the instructions list. However, I added some of my own methods, and even though this isn't anywhere near what actually any of the original games or Pokemon Go itself accomplishes or what becomes the whim and fancy of skilled developers who recreate these games or mod them, I tried to capture some of the most basic facets that the Pokemon genre is known for, such as Pokemon types and the Rock-Paper-Scissors style mechanic that renders some types stronger than other types but weaker to still other types, adding to the Poke-feel of this pet project.

*/

/***************
CLASS PGINSTANCE
****************
 The class which consists of the program's entry point and houses instances of all other classes. Preferably kept away from the rest of the classes, say in a parent directory.
*/

final class PGInstance
{
    // No imports to Utilities since it is included dynamically
    static Scanner input = new Scanner(System.in);
    static Random random = new Random();

    // returns title of the game
    static String title()
    {
        return "POKEMON DON'T GO\n";
    }

    // shows concise stats on gems and pokeballs present
    static String showPlayerGemsAndPokeballs()
    {
        return "\nYou have " + Player.current.gems + " gems and " + Player.current.pokeballs + " pokeball(s).\n";
    }

    // quits with a deep message (...)
    static void quit()
    {
        clrscr();
        System.out.println("You don't suck in life human. Life sucks you in.");
        System.exit(0);
    }

    // names the player playing the game
    static void setCurrentPlayer()
    {
        System.out.println("Name yourself, primary player of this humble game (or type \"I suck in life\") to quit: ");

        String text = input.nextLine();

        if (text.equalsIgnoreCase("I suck in life"))
            quit();
        else
            Player.current.name = text;
    }

    // a logical branch, where one traces back while the other does something else
    static void fork(Callback goback, Callback next)
    {
        System.out.print("\nAre you sure (y/n)? ");
        String option = input.next().toLowerCase();

        if (option.contains("y"))
        {
            System.out.println("A'ight then.");
            next.call();
        }
        else if (option.contains("n"))
        {
            System.out.println("Yeah I wasn't sure either.");
            goback.call();
        }
        else
        {
            System.out.println("Didn't get that. You need to be specific.");
            fork(goback, next);
        }
    }

    // waits for user input
    static void pause()
    {
        // No non-blocking IO (raw input and non echoing inputs, please google these don't disturb me) support without diving into third party land, or hideously long code; be content with regular old interactive input
        System.out.println("\nType \"cls\" or \"clear\" (or anything really) to clear the screen ...");
        input.next();
    }

    // clears the screen - easy right? ...
    static void clrscr()
    {
        // ... wrong.
        
        // clearing the screen for a console is system specific.
        String osName = System.getProperty("os.name");
        
        // if it's Windows, simply doing Runtime.getRuntime().exec("cls") doesn't work since "cls" is not an executible
        // (getRuntime() runs an executible on the given runtime)
        if (osName.startsWith("Windows"))
        {
            // instead, make a process that refers to the current cmd window and make that do a "cls", like so:
            try
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            // catch any exceptions if thrown (should be an IOException)
            catch (Exception e)
            {
                // print detailed info that helps debug said exception
                e.printStackTrace();
            }
        }
        // if its a Unix-like shell, pass an ANSI escape-code that's composed of
        // \033 (octal) => ESC as an ASCII character (== \0x1b (hexadecimal) == \27 (decimal))
        // [2J          => clear the screen, bring position to top of console window
        // [H           => display the prompt (sort of) 
        else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix"))
        {
            System.out.print("\033[2J\033[H");
        }
        else // do something weird ... which works, somewhat
        {
            System.out.println("No screen clearing on this console. Hold enter for some time and then type something.");
            // inundates whitespace, and any other input isn't stored
            input.next();
        }
    }

    // the main menu in the game
    static void mainMenu()
    {
        clrscr();
        System.out.print("\n"
            + title()
            + "Go on, " + Player.current.name + ", choose away ...\n"
            + "1. Match time!\n"
            + "2. Pokedex pls\n"
            + "3. Show me my stats\n"
            + "4. I suck at life and want to quit\n"
            + "(1/2/3/4): ");

        // checks for int input, or routes back to mainMenu()
        int option = input.hasNextInt()? input.nextInt() : mainCallbacks.length - 1;

        // just defaults to quit if options > length of array of callbacks (here 5)
        mainCallbacks[Math.min(option, mainCallbacks.length - 1)].call();
        mainMenu();
    }

    // all the options possible from mainMenu
    static Callback[] mainCallbacks = {
    //#0 - the first method reference is always the menu
        PGInstance::mainMenu,
    //#1 Match time
        PGInstance::startMatch,
    //#2 Pokedex
        PGInstance::pokedexMenu,
    //#3 Player stats
        () -> {
            System.out.println(Player.current);
            pause();
        },
    //#4 Quit
        PGInstance::quit
    };

    // represents the main part of gameplay
    static void startMatch()
    {
        // suggests that we should first use the pokedex menu
        if (Player.current.pokedex.isEmpty())
        {
            System.out.println("\nYou have no pokemon to enter the match with kid. Use your pokedex and get some pokemon first.");
            pause();
            return;
        }

        // print stuff
        clrscr();
        System.out.println(title());

        // print more stuff
        System.out.println(Player.opponent.name.toUpperCase() + " IS PREPARING HIS POKEMON FOR BATTLE!");
        
        // initialises the opponent logic - pokemon often enter evolved states
        Pokemon two = Opponent.init();

        System.out.println("\n"
            + Player.opponent.name.toUpperCase() + " HAS CHOSEN " + two +"\n"
            + Player.current.name.toUpperCase() + "! CHOOSE A POKEMON!!");

        // displays the list of pokemon available for battle
        Player.current.showNamesPokedex();
        System.out.print("Gimme an index (Or type anything else to return)! ");
        int option = input.hasNextInt()? input.nextInt() - 1 : Player.current.pokedex.size();

        // sends back to mainMenu if option is out of bounds        
        if (option >= Player.current.pokedex.size() || option < 0)
        {
            mainMenu();
        }

        // definitions, aliases for the two combatting pokemon
        Pokemon one = Player.current.pokedex.get(option);
        Pokemon.Move oneMove1 = one.listMoves.get(0);
        Pokemon.Move oneMove2 = one.listMoves.get(1);

        // if there's a bit of confusion regarding why two pokemon have the same nickname ...
        if (one.nickname.equals(two.nickname))
            System.out.println(one.nickname.toUpperCase() + " vs ... " + two.nickname.toUpperCase() + "?? Okey-dokey, LET'S RUMBLE!!");
        else // ... handle it
            System.out.println(one.nickname.toUpperCase() + " vs " + two.nickname.toUpperCase() + "!! LET'S RUMBLE!!");

        pause();
        clrscr();

        // Battle start!
        Pokemon winner = new Pokemon();
        // never give up!
        // unless it's addiction to narcotics 
        boolean giveUp = false;
        while (one.getHp() > 0 && two.getHp() > 0 && !giveUp)
        {
            // returns stats of the pokemon
            System.out.println("\nBATTLE STATS\n" + one + "\n" + two);

            // 30% chance dictates whether the pokemon recover in a battle, but naturally so
            if (random.nextInt(101) + 1 < 30 && one.getHp() < one.maxHp)
                ++one.hp;

            if (random.nextInt(101) + 1 < 30 && two.getHp() < two.maxHp)
                ++two.hp;

            // the in-game selection menu
            System.out.print("\n"
                + Player.current.name.toUpperCase() + "! WHAT WILL YOU HAVE " + one.getFullName().toUpperCase() + " DO?\n"
                + "(a) Attack\n"
                + (oneMove1.isUsable? "(1) Use " + oneMove1.name.toUpperCase() + "\n" : "")
                + (oneMove2.isUsable? "(2) Use " + oneMove2.name.toUpperCase() + "\n" : "")
                + "(f) Flee\n"
                + "Enter the index from the brackets (E.g. (a) -> 'A' key): ");

            char choice = input.hasNext("[aAfF12]")? input.next().charAt(0) : 'a';

            // a switch to handle the options supplied
            switch (choice)
            {
                case 'a': case 'A': default:
                    one.attack(two);
                    break;

                case 'f': case 'F':
                    winner = two;
                    giveUp = true;
                    break;

                case '1':
                    one.attack(two, oneMove1.name);
                    break;

                case '2':
                    one.attack(two, oneMove2.name);
                    break;
            }

            // Opponent's turn, always second
            pause();
            clrscr();

            System.out.println("\nBATTLE STATS\n" + one + "\n" + two);
            Opponent.fight(one);
            pause();
            clrscr();
        }

        // find out the winner from combat or withdrawal
        winner = giveUp? two : one.getHp() > 0? one : two;
        System.out.println("\nWINNER: " + winner.getFullName() + " of " + winner.owner.name + "!\n");

        if (winner == one)
        {
            // register the victory
            Player.current.gems += 100;
            System.out.println("You got 100 gems as a reward!\n");
            ++Player.current.wins;
        }
        else
        {
            // register the defeat
            ++Player.current.losses;
            System.out.println("Tough luck. Do not be worried! There's always a next time!\n");
        }

        pause();
    }

    // the menu displaying info on the player's pokemon list, allowing evolvution, recovery, or addition of new pokemon
    static void pokedexMenu()
    {
        clrscr();
        System.out.println("\n" + title());

        // shows up empty for the first time
        if (!Player.current.pokedex.isEmpty())
        {
            System.out.println("You currently have: ");
            Player.current.showFullPokedex();
        }
        else
        {
            System.out.println("Your Pokedex is empty.");
        }

        // the in-game menu
        System.out.print(showPlayerGemsAndPokeballs()
            + "1. New pokemon please!     (costs 1 pokeball)\n"
            + "2. Evolve one of em!       (costs gems)\n"
            + "3. More pokeballs!\n"
            + "4. Heal a hurt buddy :'(   (costs 1 pokeball)\n"
            + "5. Recharge a tired guy :| (costs 1 pokeball)\n"
            + "6. Wanna give a buddy a new nickname :P\n"
            + "7. Imma go back\n"
            + "(1/2/3/4/5/6/7): ");

        // safely routes input to mainMenu if in case of error
        int option = input.hasNextInt()? input.nextInt() : 0;
        
        // calls references to itself or the next callback function, based on choice, safely
        fork(PGInstance::pokedexMenu, pokedexCallbacks[Math.min(option, pokedexCallbacks.length - 1)]);
        pause();
        pokedexMenu();
    }

    // all options possible from pokedexMenu
    static Callback[] pokedexCallbacks = {
    //#0 - here too, the first method reference is always the menu
        PGInstance::pokedexMenu,
    //#1 - Catch a pokemon
        Player.current::catchPokemon,
    //#2 - Evolve a pokemon
        () -> {
            if (!Player.current.pokedex.isEmpty())
            {
                System.out.print("Select the pokemon you wanna evolve (by index): ");
                int option = input.hasNextInt()? input.nextInt() - 1 : Player.current.pokedex.size();

                if (option >= Player.current.pokedex.size())
                    pokedexMenu();

                Player.current.evolvePokemon(Player.current.pokedex.get(option));
            }
            else
            {
                System.out.println("\nYou have no pokemon bro. You can't do what you just said.");
            }

            System.out.println(showPlayerGemsAndPokeballs());
        },
    //#3 - Get more pokeballs
        Player.current::collectPokeballs,
    //#4 - Heal a pokemon
        () -> {
            if (!Player.current.pokedex.isEmpty())
            {
                System.out.print("Select the pokemon you wanna heal (by index): ");
                int option = input.hasNextInt()? input.nextInt() - 1 : Player.current.pokedex.size();

                if (option >= Player.current.pokedex.size())
                    pokedexMenu();

                Player.current.useHealPokeball(Player.current.pokedex.get(option));
            }
            else
            {
                System.out.println("\nYou have no pokemon bro. You can't do what you just said.");
            }


            System.out.println(showPlayerGemsAndPokeballs());
        },
    //#5 - Recharge a pokemon
        () -> {
            if (!Player.current.pokedex.isEmpty())
            {
                System.out.print("Select the pokemon you wanna recharge (by index): ");
                int option = input.hasNextInt()? input.nextInt() - 1 : Player.current.pokedex.size();

                if (option >= Player.current.pokedex.size())
                    pokedexMenu();

                Player.current.useRechargePokeball(Player.current.pokedex.get(option));
            }
            else
            {
                System.out.println("\nYou have no pokemon bro. You can't do what you just said.");
            }


            System.out.println(showPlayerGemsAndPokeballs());            
        },
    //#6 - Rename pokemon
        () -> {
            if (!Player.current.pokedex.isEmpty())
            {

                System.out.print("Which pokemon, and what nickname (type <index> <enter> <nickname>)? ");
                int option      = input.hasNextInt()? input.nextInt() - 1 : Player.current.pokedex.size();
                String nickname = input.nextLine().trim();

                if (option >= Player.current.pokedex.size())
                    pokedexMenu();

                Player.current.rename(Player.current.pokedex.get(option), input.nextLine());
            }
            else
            {
                System.out.println("\nYou have no pokemon bro. You can't do what you just said.");
            }
        },
    //#7 - Back to Main Menu
        PGInstance::mainMenu
    };

    // a rudimentary command line parsing method, enables or disables options passed via the console
    static void selectOptions(String[] args)
    {
        if (args.length > 0)
        {
            for (String arg : args)
            {
                switch(arg)
                {
                    default:
                        System.out.println("Usage: java PGInstance [-options]\n"
                            + "where options include\n"
                            + "    <an invalid option>   to print this help message\n"
                            + "    -m -more -v -verbose  to turn on verbose console messages\n"
                            + "    -c -cheat             to turn on cheats\n"
                            + "    <no options>          to run program as is"
                        );
                        System.exit(0);
                        break;

                    // this silences all calls to Verbose.println()
                    case "-m": case "-more": case "-v": case "-verbose":
                        if (!Verbose.on)
                        {
                            System.out.println("[Verbose messages enabled.]");
                            Verbose.on = true;
                        }
                        break;

                    // a Pokemon object will always output an invincible Pokemon with this option
                    case "-c": case "-cheat":
                        if (!Pokemon.usingCheats)
                        {
                            System.out.println("[Cheats on. (69/420/\"ONE PUNCH MON\")]");
                            Pokemon.usingCheats = true;
                        }
                        break;
                }
            }
        }
    }

    // the main method
    public static void main(String[] args)
    {
        selectOptions(args);
        Pokemon.initGlobalPokedex();

        System.out.println("WELCOME TO " + title());
        setCurrentPlayer();

        mainMenu();
    }
}