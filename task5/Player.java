/**
 * GenY Programming Club - Tasks Module
 * @author  Shivam Mukherjee
 * @version 2.0 
 * Task 5
 */

// Here's all the imports I'd need for this file
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  5   )    }        ]                |
|___________________________________________________________________________|

 I decided to start afresh. The Pokemon class encapsulates most of the ideas that were suggested in the instructions list. However, I added some of my own methods, and even though this isn't anywhere near what actually any of the original games or Pokemon Go itself accomplishes or what becomes the whim and fancy of skilled developers who recreate these games or mod them, I tried to capture some of the most basic facets that the Pokemon genre is known for, such as Pokemon types and the Rock-Paper-Scissors style mechanic that renders some types stronger than other types but weaker to still other types, adding to the Poke-feel of this pet project.

*/

/***********
CLASS PLAYER
************
 Encapsulates a player, with a name, gems, pokeballs and a pokedex consisting of captured pokemon. Also extends Utilities to access its random number generator instance.
*/
final class Player extends Utilities
{
    // instance fields
    String      name     = "Professor Oak";
    int         gems     = 400, pokeballs       = 1, heals = 0, recharges = 0, gemsSpent = 0,
                captures = 0, fullyEvolvedCount = 0, wins  = 0, losses    = 0;
    List<Pokemon> pokedex = new ArrayList<>();
    
    // the only Players allowed
    static Player current = new Player(), opponent = new Player("Gary"), neutral = new Player();

    // hidden constructors so no one can make more players. Mwuhahahaha!
    private Player()
    {
    }

    private Player(String name)
    {
        if (!this.name.equals(name))
            this.name = name;
    }

    // prints all Player stats
    @Override
    public String toString()
    {
        return "\n"
            + "Your stats, "             + name                                 + "...\n"
            + "Pokeballs             : " + pokeballs                            + "\n"
            + "Gems                  : " + gems                                 + "\n\n"

            + "Pokemon captured      : " + pokedex.size()                       + "\n"
            + "Times healed          : " + heals                                + "\n"
            + "Times recharged       : " + recharges                            + "\n"
            + "Total Pokeballs spent : " + (heals + recharges + pokedex.size()) + "\n\n"

            + "Pokemon fully evolved : " + fullyEvolvedCount                    + "\n"
            + "Gems spent            : " + gemsSpent                            + "\n\n"

            + "Wins                  : " + wins                                 + "\n"
            + "Losses                : " + losses                               + "\n";
    }

    // reduces a pokeball and captures a random pokemon from a global pokedex (list of pokemon)
    void catchPokemon()
    {
        // fails if there are no pokeballs
        if (pokeballs <= 0)
        {
            System.out.println("Warning: insufficient pokeballs.");
            return;
        }

        // or if there are 5 pokemon on you
        if (pokedex.size() >= 5)
        {
            System.out.println("It's difficult carrying around so many pokemon!");
            return;
        }

        // selects a random pokemon from the given global pokedex by looking at it like a list and accessing using an index
        Pokemon caught = new Pokemon(Pokemon.globalPokedex.get(random.nextInt(Pokemon.globalPokedex.size())));

        // if it's Mewtwo, cast down the probability of owning it to about 5%
        if (caught.listNames.get(0).equals("Mewtwo"))
        {
            if (random.nextInt(101) + 1 < 95)
                caught = new Pokemon(Pokemon.globalPokedex.get(random.nextInt(Pokemon.globalPokedex.size())));
        }

        System.out.print("\nYou went to catch some pokemon ... ");

        // puts it in the list
        if (!pokedex.contains(caught) && pokedex.size() <= 5)
        {
            System.out.println("you caught a(n) " + caught.listNames.get(0) + ".");
            pokedex.add(caught);
            --pokeballs;
            ++captures;
        } // maxes at 5
        
        // may optionally fail
        else
        {
            System.out.println("you weren't too lucky.");
            return;
        }

        caught.owner = Player.current;
    }

    // fully restores HP of a pokemon at the cost of one pokeball ... cheap huh?
    // it uses generics to enable this function for all of Pokemon's subclasses
    <T extends Pokemon> void useHealPokeball(T pokemon)
    {
        // fails if there are no pokeballs
        if (pokeballs <= 0)
        {
            Verbose.println("Warning: insufficient pokeballs.");
            return;
        }

        // handles the full hp condition, registers it
        if (pokemon.getHp() < pokemon.maxHp)
        {
            --pokeballs;
            ++heals;

            // the heal
            pokemon.hp = pokemon.maxHp;
            System.out.println(pokemon.getFullName() + " is healed and battle ready!");
        }
        else
        {
            System.out.println("You noticed that " + pokemon.getFullName() + " was totally unblemished, so you let it be.");
        }
    }

    // fully recharges all moves for one pokeball ... yeah I got ya
    <T extends Pokemon> void useRechargePokeball(T pokemon)
    {
        // fails if there are no pokeballs
        if (pokeballs <= 0)
        {
            System.out.println("Warning: insufficient pokeballs.");
            return;
        }

        // handles the fully charged condition
        boolean tired = false;
        for (Pokemon.Move move : pokemon.listMoves)
        {
            if (move.charges < move.maxCharges)
                tired = true;
        }

        // registers it
        if (tired)
        {
            --pokeballs;
            ++recharges;
    
            // the recharge
            for (Pokemon.Move move : pokemon.listMoves)
            {
                move.charges = move.maxCharges;
            }
            System.out.println(pokemon.getFullName() + " is fully recharged!");
        }
        else
        {
            System.out.println("You noticed that " + pokemon.getFullName() + " is bursting with energy, so you let it be.");
        }
    }

    // randomly increases pokeballs by a number between one and five, maxes out at 5 pokeballs
    void collectPokeballs()
    {
        pokeballs += random.nextInt(5) + 1;

        // brings the value back to five if it exceeds
        if (pokeballs > 5)
            pokeballs = 5;

        // brings it to zero if less
        if (pokeballs < 0)
            pokeballs = 0;
    }

    // evolves the given pokemon by deducting the required cost
    void evolvePokemon(Pokemon pokemon)
    {
        int cost = pokemon.getEvolveCost();

        // fails if the pokemon is fully evolved
        if (pokemon.stage == pokemon.maxStage)
        {
            System.out.println(pokemon.getFullName() + " is already at its final stage!");
            return;
        }

        // fails if gems are less than cost
        if (gems <= cost)
        {
            System.out.println("Warning: insufficient gems for evolving " + pokemon.getFullName() + ".\nWin some gems and return.");
            return;
        }

        // updates counters 
        if (pokemon.stage < pokemon.maxStage)
        {
            // adds the pokemon back as copied version of the original
            int index = pokedex.indexOf(pokemon);
            String oldName = pokemon.getFullName();
            pokedex.remove(pokemon);
            pokemon = pokemon.evolve();
            pokedex.add(index, pokemon);

            if (!oldName.equals(pokemon.getFullName()))
            {
                gems      -= cost;
                gemsSpent += cost;
            }

            System.out.println(pokemon.stage);
            System.out.println(pokemon.nickname + " is now a(n) " + pokemon.listNames.get(pokemon.stage - 1));

            // a free type can be added to a Pokemon with one type only
            if (pokemon.stage == pokemon.maxStage && pokemon.listTypes.size() == 1)
            {
                PGInstance.pause();
                PGInstance.clrscr();
                System.out.print("\nYou can add a new type to " + pokemon.getFullName() + " for free!\n"
                    + "However, keep in mind that a type carries both positive and negative buffs against other types.\n"
                    + "You can also choose the same type again - stronger positive buffs but more vulnerabilities.\n\n"
                    + "Choose a type (or not, but this will be final):\n"
                    + "      1. BUG      2. DARK     3. DRAGON    4. ELECTRIC   5. FAIRY    6. FIGHTING\n"
                    + "      7. FIRE     8. FLYING   9. GHOST    10. GRASS     11. GROUND  12. ICE\n"
                    + "     13. NORMAL  14. POISON  15. PSYCHIC  16. ROCK      17. STEEL   18. WATER\n"
                    + "(1/2/3.../18, or anything else to cancel): ");

                // an option is safely accepted
                int option = input.hasNextInt()? input.nextInt() : 0;
            
                // adds the correct type or leaves with a message
                if (option >= 1 && option <= 18)
                {
                    Pokemon.Type type = Pokemon.Type.values()[option - 1];
                    pokemon.addType(type);

                    if (type != pokemon.listTypes.get(0))
                        System.out.println(pokemon.getFullName() + " now qualified as a(n) " + type + " type!");
                    else
                        System.out.println(pokemon.getFullName() + " has fully awakened as a(n) " + type + " type!" );

                    Verbose.println("(If it's illiogical, use your imagination.)");
                }
                else
                    System.out.println("No problemo, but this is final and binding unless you're a hacker.");
            }

            return;
        }
    }

    // allows a player to rename a specific pokemon, which can be any of Pokemon's subclasses
    <T extends Pokemon> void rename(T pokemon, String name)
    {
        Verbose.println(this.name + " renamed " + pokemon.getFullName());
        pokemon.nickname = name; 
    }

    // shows the complete pokedex, with anticipated evolution costs
    void showFullPokedex()
    {
        int i = 1;
        for (Pokemon entry : pokedex)
        {
            System.out.println("\n"
                + i + ". " + entry
                + (entry.stage != entry.maxStage ?
                    "    - Evolution cost: " + entry.getEvolveCost() + " (to stage " + (entry.stage + 1) + " of " + entry.maxStage + ")"
                    : 
                    "    At final stage."));
            ++i;
        }
    }

    void showNamesPokedex()
    {
        int i = 1;
        for (Pokemon entry : pokedex)
        {
            System.out.println("\n" + i + ". " + entry.getFullName());
            ++i;
        }   
    }
}

/*************
CLASS OPPONENT
**************
 The game's AI, which tries its best to beat the player. Probably fails too.
*/

class Opponent extends Utilities
{
    static Random random = new Random();
    static Pokemon pawn;

    // initialises the opponent pokemon
    static Pokemon init()
    {
        // gets access to all globally defined pokemon
        Player.opponent.pokedex = cloneList(Pokemon.globalPokedex);
        Player.opponent.pokedex.replaceAll(pokemon -> { pokemon.owner = Player.opponent; return pokemon; });
        pawn = Player.opponent.pokedex.get(random.nextInt(Player.opponent.pokedex.size() - 1) + 1);


        // if it's Mewtwo, cast down the probability of owning it
        if (pawn.listNames.get(0).equals("Mewtwo"))
        {
            if (random.nextInt(101) + 1 < 95)
                pawn = new Pokemon(Pokemon.globalPokedex.get(random.nextInt(Pokemon.globalPokedex.size())));
        }

        // randomly evolves the pokemon selected
        for (int i = 0; i < random.nextInt(3); i++)
        {
            int index = Player.opponent.pokedex.indexOf(pawn);
            Player.opponent.pokedex.remove(pawn);
            pawn = pawn.evolve();
            Player.opponent.pokedex.add(index, pawn);
        }

        return pawn;
    }

    // the random decision made by the computer
    static void fight(Pokemon us)
    {
        int chance = random.nextInt(101) + 1;
        Pokemon.Move move1 = pawn.listMoves.get(0);
        Pokemon.Move move2 = pawn.listMoves.get(1);

        System.out.println("\n" + pawn.owner.name + "'s move.\n");

        if (chance > 70 && chance <= 90 && move1.isUsable)
        {
            pawn.attack(us, move1.name);
        }
        else if (chance > 90 && move2.isUsable)
        {
            pawn.attack(us, move2.name);
        }
        else
        {
            pawn.attack(us);
        }
    }
}