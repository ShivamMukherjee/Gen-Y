/**
 * GenY Programming Club - Tasks Module
 * @author  Shivam Mukherjee
 * @version 2.0 
 * Task 5
 */

// Here's all the imports I'd need for this file
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  5   )    }        ]                |
|___________________________________________________________________________|

 I decided to start afresh. The Pokemon class encapsulates most of the ideas that were suggested in the instructions list. However, I added some of my own methods, and even though this isn't anywhere near what actually any of the original games or Pokemon Go itself accomplishes or what becomes the whim and fancy of skilled developers who recreate these games or mod them, I tried to capture some of the most basic facets that the Pokemon genre is known for, such as Pokemon types and the Rock-Paper-Scissors style mechanic that renders some types stronger than other types but weaker to still other types, adding to the Poke-feel of this pet project.

*/

/************
CLASS POKEMON
*************
 The main class in the program. Several methods and constants declared "final" for ensuring no further changes if accidentally extended. 
*/
class Pokemon extends Utilities
{
    // the instance fields to be used
    String            nickname;
    ArrayList<String> listNames = new ArrayList<>();
    ArrayList<Move>   listMoves = new ArrayList<>();
    ArrayList<Type>   listTypes = new ArrayList<>();
    double            hp = 25, maxHp = 25, cp = 100;
    int               stage = 1, maxStage = 3;
    Player            owner = Player.neutral;

    // some static fields to check "overpowered" instances ...
    static boolean   usingCheats = false;
    final static int thresholdHp = 100;
    final static int thresholdCp = 400;

    // ... and one Builder
    public static Builder whosThatPokemon = new Builder();

    // this will contain all the unique pokemon in the game - 10 as per instructions
    static ArrayList<Pokemon> globalPokedex = new ArrayList<>();

    Pokemon()
    {
        // allows usage of given constructor in subclasses, but otherwise does nothing additionally
    }

    Pokemon(Pokemon p)
    {
        nickname  = p.nickname;
        listNames = p.listNames;
        listTypes = p.listTypes;

        listMoves = new ArrayList<>(p.listMoves.size());
        for (Move move : p.listMoves)
            listMoves.add(new Move(move));

        hp = maxHp = p.maxHp;
        cp       = p.cp;
        stage    = p.stage;
        maxStage = p.maxStage;
        owner    = p.owner;
    }

    // Overrides Object's toString() method, instead printing stats
    @Override
    final public String toString()
    {
        if (Verbose.on)
        {
            return "\n"
                    + "Name            : " + getFullName()   + "\n"
                    + "Owner           : " + owner.name      + "\n"
                    + "All Names       : " + listNames       + "\n"
                    + "Types           : " + listTypes       + "\n"
                    + "Evolution Stage : " + stage           + "/"
                                           + maxStage        + "\n" 
                    + "HP              : " + intify(getHp()) + "/"
                                           + intify(maxHp)   + "\n"
                    + "CP              : " + intify(cp)      + "\n"
                    + "Moves           : " + listMoves       + "\n";
        }
        
        return "\n[" + getFullName() + " of " + owner.name + "]" + listTypes + "[" + intify(hp) + "/" + intify(maxHp) + "HP|" + intify(cp) + "CP].\n" + listMoves + "\n";
    }

    // returns HP and checks it against limiting values
    // could be improved by introducing concurrency and unsigned values but meh
    final public double getHp()
    {
        if (hp > maxHp)
            hp = maxHp;
        
        if (hp < 0)
            hp = 0;
        
        return hp;
    }

    // handy way to return a Pokemon's name like "Flowey (Bulbasaur)"
    final public String getFullName()
    {
        return this.nickname + " (" + this.listNames.get(stage-1) + ")";
    }

    /**************************
    INNER CLASS POKEMON.BUILDER
    ***************************
     Builds a Pokemon object as class Pokemon's constructors haven't been assigned; verbose, but again, handy.    
    */
    final static class Builder
    {
        // a copy of Pokemon's instance fields, all private
        private String            nickname;
        private ArrayList<String> listNames = new ArrayList<>();
        private ArrayList<Move>   listMoves = new ArrayList<>();
        private ArrayList<Type>   listTypes = new ArrayList<>();
        private double            hp = 25, maxHp = 25, cp = 100;
        private int               stage = 1, maxStage = 3;
        private Player            owner;

        // No other builders allowed apart from the static builder present
        private Builder()
        {
        }

        public Builder nickname(String nickname)
        {
            this.nickname = nickname;
            return this;
        }

        // the following mutators use variadic argument lists ...
        public Builder names(String... names)
        {
            if (names.length > 0)
                listNames = new ArrayList<>(Arrays.asList(names));

            return this;
        }

        // ... variadic lists behave like arrays, have lengths ...
        public Builder types(Type... types)
        {
            if (types.length > 0)
                listTypes = new ArrayList<>(Arrays.asList(types));
            
            return this;
        }

        // ... on failure, the state of the list fields remain unchanged
        public Builder moves(Move... moves)
        {
            if (moves.length > 0)
                listMoves = new ArrayList<>(Arrays.asList(moves));

            return this;
        }

        // the following mutators simulate default values and perform conditional assignment ...
        public Builder hp(double maxHp)
        {
            if (maxHp > thresholdHp/5 && maxHp < thresholdHp/3)
                this.hp = this.maxHp = maxHp;

            // "69" is thus a cheat code
            else if (usingCheats && maxHp == 69)
                // Invulnerable! But only if cheats are enabled and you know the cheat codes
                this.hp = this.maxHp = 10000;
            
            return this;
        }

        // ... class Pokemon's static fields are used for checking assignment of numeric fields
        public Builder cp(double cp)
        {
            if (cp > thresholdCp/5 && cp < thresholdCp/3)
                this.cp = cp;

            // again, "420" is a cheat code
            else if (usingCheats && cp == 420)
                // Invincible! But only if cheats are enabled and you know the cheat codes
                this.cp = 40000;

            return this;
        }

        // allows for accomodating Pokemon without a third stage
        public Builder maxStage(int maxStage)
        {
            if (maxStage == 2 || maxStage == 3)
            {    
                this.maxStage = maxStage;
                this.stage    = 1;
            }
            return this;
        }

        // sets current owner of pokemon
        public Builder owner(Player owner)
        {
            this.owner = owner;
            return this;
        }

        // pieces together a Pokemon object using the implicit default constructor, then nulls out all values for reuse
        public Pokemon build()
        {
            Pokemon p = new Pokemon();

            // Pokemons without a third stage have their first move usable by default
            if (maxStage == 2)
            {
                listMoves.get(0).isUsable = true;
            }

            p.nickname   = nickname;
            p.listNames  = listNames;
            p.listTypes  = listTypes;
            p.listMoves  = listMoves;
            p.hp         = hp;
            p.maxHp      = maxHp;
            p.cp         = cp;
            p.stage      = stage;
            p.maxStage   = maxStage;

            // For sake of reuse, all values are nulled/zeroed
            nickname   = null;
            listNames  = null;
            listTypes  = null;
            listMoves  = null;
            hp         = 25;
            maxHp      = 25;
            cp         = cp;
            stage      = stage;
            maxStage   = maxStage;            

            Verbose.println("New Pokemon!\n" + p);

            return p;
        }

        // synonymous to build() but more poke-intuitively
        public Pokemon gotcha()
        {
            return this.build();
        }

    }

    // the predefined pokedex, as per instructions
    static void initGlobalPokedex()
    {
        // The one strongest pokemon
        // just as easily accessible as the rest ...
        Pokemon mewtwo = 
            Pokemon.whosThatPokemon
                .nickname("He whose name must not be told")
                .hp(69).cp(420).maxStage(1)
                .names("Mewtwo")
                .types(Pokemon.Type.PSYCHIC)
                .moves
                (
                    new Pokemon.Move("???", Pokemon.Type.PSYCHIC, 1),
                    new Pokemon.Move("???", Pokemon.Type.PSYCHIC, 1)
                )
                .gotcha();

        
        mewtwo.hp = mewtwo.maxHp = 100;
        mewtwo.cp = 400;

        for (Pokemon.Move move : mewtwo.listMoves)
        {
            move.isUsable = true;
        }

        Pokemon.globalPokedex = new ArrayList<>(Arrays.asList(
            Pokemon.whosThatPokemon
                .nickname("Burnie Manders")
                .hp(25).cp(110).maxStage(3)
                .names("Charmander", "Charmeleon", "Charizard")
                .types(Pokemon.Type.FIRE)
                .moves
                (
                    new Pokemon.Move("Flame tackle", Pokemon.Type.FIRE, 4), // usable at stage 2
                    new Pokemon.Move("Flamethrower", Pokemon.Type.FIRE, 2)  // usable at stage 3
                )
                .gotcha(),

            Pokemon.whosThatPokemon
                .nickname("Neil deGrass Tysaur")
                .hp(27).cp(115).maxStage(3)
                .names("Bulbasaur", "Ivysaur", "Venusaur")
                .types(Pokemon.Type.GRASS, Pokemon.Type.POISON)
                .moves
                (
                    new Pokemon.Move("Razor leaf", Pokemon.Type.GRASS, 5),
                    new Pokemon.Move("Sludge bomb", Pokemon.Type.POISON, 2)
                )
                .gotcha(),

            Pokemon.whosThatPokemon
                .nickname("Thorachu")
                .hp(25).cp(100).maxStage(3)
                .names("Pichu", "Pikachu", "Raichu")
                .types(Pokemon.Type.ELECTRIC)
                .moves
                (
                    new Pokemon.Move("Iron tail", Pokemon.Type.NORMAL, 4),
                    new Pokemon.Move("Thunder", Pokemon.Type.ELECTRIC, 1)
                )
                .gotcha(),

            Pokemon.whosThatPokemon
                .nickname("Teenage Mutant Ninja Squirtle")
                .hp(25).cp(100).maxStage(3)
                .names("Squirtle", "Wartortle", "Blastoise")
                .types(Pokemon.Type.WATER)
                .moves
                (
                    new Pokemon.Move("Water gun", Pokemon.Type.WATER, 3),
                    new Pokemon.Move("Hydro pump", Pokemon.Type.WATER, 2)
                )
                .gotcha(),

            Pokemon.whosThatPokemon
                .nickname("Feminist")
                .hp(27).cp(110).maxStage(3)
                .names("Nidoran (F)", "Nidorina", "Nidoqueen")
                .types(Pokemon.Type.POISON)
                .moves
                (
                    new Pokemon.Move("Poison sting", Pokemon.Type.POISON, 5),
                    new Pokemon.Move("Stone edge", Pokemon.Type.ROCK, 3)
                )
                .gotcha(),

            Pokemon.whosThatPokemon
                .nickname("Idgeot")
                .hp(22).cp(110).maxStage(3)
                .names("Pidgey", "Pidgeotto", "Pidgeot")
                .types(Pokemon.Type.FLYING)
                .moves
                (
                    new Pokemon.Move("Steel Wing", Pokemon.Type.NORMAL, 5),
                    new Pokemon.Move("Aerial Ace", Pokemon.Type.FLYING, 3)
                )
                .gotcha(),

            Pokemon.whosThatPokemon
                .nickname("Gentleman")
                .hp(27).cp(110).maxStage(3)
                .names("Nidoran (M)", "Nidorino", "Nidoking")
                .types(Pokemon.Type.POISON)
                .moves
                (
                    new Pokemon.Move("Poison sting", Pokemon.Type.POISON, 5),
                    new Pokemon.Move("Megahorn", Pokemon.Type.NORMAL, 3)
                )
                .gotcha(),

            Pokemon.whosThatPokemon
                .nickname("One Horseman")
                .hp(25).cp(120).maxStage(3)
                .names("Abra", "Kadabra", "Alakazam")
                .types(Pokemon.Type.PSYCHIC)
                .moves
                (
                    new Pokemon.Move("Zen headbutt", Pokemon.Type.PSYCHIC, 5),
                    new Pokemon.Move("Psybeam", Pokemon.Type.PSYCHIC, 2)
                )
                .gotcha(),

            Pokemon.whosThatPokemon
                .nickname("Fiddlyruff")
                .hp(27).cp(110).maxStage(3)
                .names("Iglybuff", "Jigglypuff", "Wigglytuff")
                .types(Pokemon.Type.FAIRY)
                .moves
                (
                    new Pokemon.Move("Feint attack", Pokemon.Type.DARK, 3),
                    new Pokemon.Move("Disarming voice", Pokemon.Type.FAIRY, 3)
                )
                .gotcha(), mewtwo     
        ));
    }


    /***********************
    INNER CLASS POKEMON.TYPE
    ************************
     Simulates the type system as observed in Pokemon Go, to the letter.
    */
    enum Type
    {
        // all 18 Pokemon types as observed in Pokemon Go, arranged alphabetically
        BUG, DARK, DRAGON, ELECTRIC, FAIRY, FIGHTING,
        FIRE, FLYING, GHOST, GRASS, GROUND, ICE,
        NORMAL, POISON, PSYCHIC, ROCK, STEEL, WATER;

        // the type chart with multipliers as matrix values, rows and columns arranged alphabetically
        // rows represent attack effectiveness, columns represent defense effectiveness
        final double[][] chart = 
        {                 //Bug|Dark|Dragon|Electric|Fairy|Fighting|Fire|Flying|Ghost|Grass|Ground|Ice|Normal|Poison|Psychic|Rock|Steel|Water
            /* Bug      */{1.00, 1.25, 1.00,  1.00,  0.80,  0.80,   0.80, 0.80, 0.80, 1.25, 1.00, 1.00, 1.00,  0.80, 1.25,  1.00, 0.80, 1.00},
            /* Dark     */{1.00, 0.80, 1.00,  1.00,  0.80,  0.80,   1.00, 1.00, 1.25, 1.00, 1.00, 1.00, 1.00,  1.00, 1.25,  1.00, 1.00, 1.00},
            /* Dragon   */{1.00, 1.00, 1.25,  1.00,  0.80,  1.00,   1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,  1.00, 1.00,  1.00, 0.80, 1.00},
            /* Electric */{1.00, 1.00, 0.80,  0.80,  1.00,  1.00,   1.00, 1.25, 1.00, 0.80, 0.80, 1.00, 1.00,  1.00, 1.00,  1.00, 1.00, 1.25},
            /* Fairy    */{1.00, 1.25, 1.25,  1.00,  1.00,  1.25,   0.80, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,  0.80, 1.00,  1.00, 0.80, 1.00},
            /* Fighting */{0.80, 1.25, 1.00,  1.00,  0.80,  1.00,   1.00, 0.80, 0.80, 1.00, 1.00, 1.25, 1.25,  0.80, 0.80,  1.25, 1.25, 1.00},
            /* Fire     */{1.25, 1.00, 0.80,  1.00,  1.00,  1.00,   0.80, 1.00, 1.00, 1.25, 1.00, 1.25, 1.00,  1.00, 1.00,  0.80, 1.25, 0.80},
            /* Flying   */{1.25, 1.00, 1.00,  0.80,  1.00,  1.25,   1.00, 1.00, 1.00, 1.25, 1.00, 1.00, 1.00,  1.00, 1.00,  0.80, 0.80, 1.00},
            /* Ghost    */{1.00, 0.80, 1.00,  1.00,  1.00,  1.00,   1.00, 1.00, 1.25, 1.00, 1.00, 1.00, 0.80,  1.00, 1.25,  1.00, 1.00, 1.00},
            /* Grass    */{0.80, 1.00, 0.80,  1.00,  1.00,  1.00,   0.80, 0.80, 1.00, 0.80, 1.25, 1.00, 1.00,  0.80, 1.00,  1.25, 0.80, 1.25},
            /* Ground   */{0.80, 1.00, 1.00,  1.25,  1.00,  1.00,   1.25, 0.80, 1.00, 0.80, 1.00, 1.00, 1.00,  1.25, 1.00,  1.25, 1.25, 1.00},
            /* Ice      */{1.00, 1.00, 1.25,  1.00,  1.00,  1.00,   0.80, 1.25, 1.00, 1.25, 1.25, 0.80, 1.00,  1.00, 1.00,  1.00, 0.80, 0.80},
            /* Normal   */{1.00, 1.00, 1.00,  1.00,  1.00,  1.00,   1.00, 1.00, 0.80, 1.00, 1.00, 1.00, 1.00,  1.00, 1.00,  0.80, 0.80, 1.00},
            /* Poison   */{1.00, 1.00, 1.00,  1.00,  1.25,  1.00,   1.00, 1.00, 0.80, 1.25, 0.80, 1.00, 1.00,  0.80, 1.00,  0.80, 0.80, 1.00},
            /* Psychic  */{1.00, 0.80, 1.00,  1.00,  1.00,  1.25,   1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00,  1.25, 0.80,  1.00, 0.80, 1.00},
            /* Rock     */{1.25, 1.00, 1.00,  1.00,  1.00,  0.80,   1.25, 1.25, 1.00, 1.00, 0.80, 1.25, 1.00,  1.00, 1.00,  1.00, 0.80, 1.00},
            /* Steel    */{1.00, 1.00, 1.00,  0.80,  1.25,  1.00,   0.80, 1.00, 1.00, 1.00, 1.00, 1.25, 1.00,  1.00, 1.00,  1.25, 0.80, 0.80},
            /* Water    */{1.00, 1.00, 0.80,  1.00,  1.00,  1.00,   1.25, 1.00, 1.00, 0.80, 1.25, 1.00, 1.00,  1.00, 1.00,  1.25, 1.00, 0.80}
        };

        // returns the attack effectiveness of a type 
        double damages(Type type)
        {
            return chart[this.ordinal()][type.ordinal()];
        }

        // returns defensive effectiveness of a type
        double blocks(Type type)
        {
            return chart[type.ordinal()][this.ordinal()];
        }
    }

    // allows addition of a type after object initialisation - used for introducing a new type to a pokemon
    public boolean addType(Type type)
    {
        if (listTypes.size() == 1)
        {
            listTypes.add(type);
            return true;
        }

        return false;
    }

    /***********************
    INNER CLASS POKEMON.MOVE
    ************************
     Encapsulates moves, each with a name, unique type and number of charges; a final list of Move instances was considered for universalising allowed moves.
    */
    static class Move
    {
        String  name;
        Type    type;
        int     charges, maxCharges;
        boolean isUsable = false;

        Move(String name, Type type, int charges)
        {
            this.name     = name;
            this.type     = type;
            // concisely defines minimum and maximum charges as zero and ten respectively, assigning one charge by default
            this.charges  = this.maxCharges = charges > 0? charges < 10? charges : 10 : 1;
        }

        Move(Move move)
        {
            this(move.name, move.type, move.charges);
            this.isUsable = move.isUsable;
        }

        // Overriding toString() to output move stats
        @Override
        public String toString()
        {
            return name + " [" + (isUsable? type + " - " + charges + "/" + maxCharges + " charge(s) left]" : "LOCKED]");
        }
    }

    // computes the effect of multiple types to generate a multiplier on attack
    // this is not the system used in Pokemon Go
    final static double hitDamagesTypes(Pokemon a, Pokemon b)
    {
        double effect = 1;
        for (Type i : a.listTypes)
        {
            for (Type j : b.listTypes)
            {
                Verbose.println("Comparing " + i + " of " + a.getFullName() + " to " + j + " of " + b.getFullName() + ": " + i.damages(j));
                effect *= i.damages(j);
            }
        }
        return effect;
    }

    // computes the effect of multiple types to generate a multiplier on defence
    // this is not the system used in Pokemon Go
    final static double typesBlockHit(Pokemon a, Pokemon b)
    {
        double effect = 1;
        for (Type i : a.listTypes)
        {
            for (Type j : b.listTypes)
            {
                Verbose.println("Comparing " + i + " of " + a.getFullName() + " to " + j + " of " + b.getFullName() + ": " + i.blocks(j));
                effect *= i.blocks(j);
            }
        }
        return effect;
    }

    // computes the effect of a move on a given Pokemon's types to generate a multiplier
    final static double moveDamagesTypes(Move x, Pokemon b)
    {
        double effect = 1;
        for (Type type : b.listTypes)
        {
            Verbose.println("Comparing " + x.type + " of " + x + " to " + type + " of " + b.getFullName() + ": " + x.type.damages(type));
            effect *= x.type.damages(type);
        }
        return effect;
    }

    // computes the effect of a move on a given Pokemon's types to generate a multiplier
    final static double typesBlockMove(Move x, Pokemon b)
    {
        double effect = 1;
        for (Type type : b.listTypes)
        {
            Verbose.println("Comparing " + x.type + " of " + x + " to " + type + " of " + b.getFullName() + ": " + x.type.damages(type));
            effect *= x.type.damages(type);
        }
        return effect;
    }

    // a generic method allowing Pokemon to attack Pokemon1 and Pokemon2 objects
    final public <T extends Pokemon> void attack(T opponent)
    {
        if (this.hp < 0)
        {
            System.out.println(this.getFullName() + " has fainted. Consider using a healing Pokeball.");
            return;
        }
        // variables calculated from existing values
        double atk       = (this.cp/10);
        double def       = (opponent.cp/10);

        // variables to introduce randomness
        double atkLuck   = random.nextDouble()* 0.2 + 0.9;
        double defLuck   = random.nextDouble()* 0.2 + 0.9;

        // effect multipliers computed from types of the two Pokemon
        double atkEfct    = hitDamagesTypes(this, opponent);
        double defEfct    = typesBlockHit(this, opponent);

        // prints effectiveness, if any, to console
        System.out.println(this.getFullName() + " hit " + opponent.getFullName() + ".");
        System.out.println(atkEfct/defEfct > 1? "It's super effective!\n" : "It's not so effective.\n");

        // compute damage
        double damage = 5 + (atk * atkEfct + atkLuck) / (def * defEfct + defLuck)  ;
        opponent.hp -= opponent.hp > 0? damage : 0;

        if (opponent.hp == 0)
            System.out.println(opponent.getFullName() + " has fainted.\n");
    }

    // overloads attack() to work for Moves, between Pokemon and its subclasses
    final public <T extends Pokemon> void attack(T opponent, String moveName)
    {
        // the attack/defence values are unchanged (suggested for changes)
        double atk        = (this.cp/10);
        double def        = (opponent.cp/10);
        double atkLuck    = random.nextDouble()* 0.1 + 0.9;
        double defLuck    = random.nextDouble()* 0.1 + 0.9;
        double moveBonus  = 0;

        // effect also depends on the move's effectiveness, irrespective of the attacking pokemon
        double atkEfct = 0, defEfct = 0;

        if (this.hp < 0)
        {
            System.out.println(this.getFullName() + " has fainted. Consider using a healing Pokeball.");
            return;
        }

        // handles all cases where a move may not be accessible/usable by switching to normal attack(<? extends Pokemon>)
        for (Move move : listMoves)
        {
            if (this.hp > 0 && move.name.equals(moveName))
            {
                if (!move.isUsable)
                {
                    Verbose.println(this.getFullName() + " doesn't understand " + moveName + " yet.");
                    this.attack(opponent);
                    return;
                }
                else if (move.charges == 0)
                {
                    Verbose.println(this.getFullName() + " can't use " + move + " for now. It hit " + opponent.getFullName());
                    this.attack(opponent);
                    System.out.println("Insufficient charges. Consider using an energy ball.");
                    return;
                }
                else // execute the move, print to console
                {
                    System.out.println(this.getFullName() + " used " + move + " on " + opponent.getFullName() + ".");
                    
                    // this encapsulates why a move is generically stronger than a normal attack if matched up properly
                    atkEfct   = hitDamagesTypes(this, opponent) * moveDamagesTypes(move, opponent);
                    defEfct   = typesBlockHit(this, opponent) * typesBlockMove(move, opponent);
                    moveBonus = 10/move.maxCharges;

                    move.charges--;

                    System.out.println(atkEfct/defEfct > 1? "It's super effective!" : "It's not so effective.");
                }
            }

            // cheats enabled?
            if (this.hp > 0 && usingCheats && moveName.equals("ONE PUNCH MON"))
            {
                System.out.println(this.getFullName() + " One Punched " + opponent.getFullName() + ".");
                Verbose.println("Uhh uhh uhh aaahhh ONE PUUUUUUUUNCH MOOOOOONNN!!!\n");

                  opponent.hp
                = opponent.maxHp
                = opponent.cp
                = opponent.owner.pokeballs
                = opponent.owner.gems
                = 0;

                opponent.listMoves.forEach(m -> m.charges = m.maxCharges = 0);

                opponent.nickname = "ONE PUNCHED.";
                System.out.println("It's effectively super ... but " + this.nickname + " says they didn't put anything in it. Well ...");

                return;
            }
        }

        // compute damage
        double damage = 10 + (atk * atkEfct + atkLuck)/(def * defEfct + defLuck) * moveBonus;
        opponent.hp -= opponent.hp > 0? damage : 0;
    }

    static double moveMultiplierAgainst(Move move, Pokemon opponent)
    {
        return moveDamagesTypes(move, opponent)/typesBlockMove(move, opponent) * 10/move.maxCharges;
    }

/*/ The dodge() method was considered to be unneeded, but will not impact execution if uncommented
    final private double dodge()
    {
        return 0;
    }
//*/    
    // evolves Pokemon, where the subclass constructor changes, rather than extends, instance properties
    Pokemon evolve()
    {
        if (owner.gems > getEvolveCost())
        {
            Verbose.println(this.getFullName() + " is evolving!");
            return new Pokemon1(this);
        }
        else
        {
            return this;
        }
    }

    // retrieves Pokemon's evolution cost in gems
    // a 2-staging pokemon pays the whole cost at once (500 gems)
    // a 3-staging pokemon pays the whole cost partially per stage  (200 gems, then 300 gems)
    // they shouldn't be evolved for free ...
    int getEvolveCost()
    {        
        int cost = 0;

        if (maxStage == 2)
        {
            cost = 400;
        }
        else if (maxStage == 3)
        {
            cost = 100 * (stage + 1);
        }

        return cost;
    }

}

/*************
CLASS POKEMON1
**************
 Extends Pokemon for first evolution stage, upgrades values and conditionally enables first or second move.
*/
class Pokemon1 extends Pokemon
{
    Pokemon1()
    {
        // allows usage of given constructor in subclasses, but otherwise does nothing additionally
    }

    // does the required upgrades
    Pokemon1(Pokemon p)
    {
        super(p);

        // none for Mewtwo
        if (p.listNames.get(0).equals("Mewtwo"))
        {
            System.out.println("That's not possible.");
            return;
        }

        hp = maxHp  = p.maxHp < thresholdHp? maxStage == 2? p.maxHp * 1.5 * 1.5 : p.maxHp * 1.5 : thresholdHp;
        cp          =    p.cp < thresholdCp? maxStage == 2?    p.cp * 1.5 * 1.5 :    p.cp * 1.5 : thresholdCp;
        stage = 2;
        // enables the next move according to maximum stage
        if (p.maxStage == 3)
            listMoves.get(0).isUsable = true;
        else if (p.maxStage == 2)
        {
            ++owner.fullyEvolvedCount;
            listMoves.get(0).isUsable = true;
            listMoves.get(1).isUsable = true;
        }

        Verbose.println("\n" + this.nickname + " is now a(n) " + this.listNames.get(stage-1) + ".");
    }

    // handles evolution cases and prints to console optionally    
    Pokemon evolve()
    {
        if (this.maxStage == 3 && owner.gems > getEvolveCost())
        {
            Verbose.println(this.getFullName() + " is evolving!");
            return new Pokemon2(this);
        }
        else
        {
            Verbose.println(this.getFullName() + " is at its final stage!");
            return this;
        }
    }
}

/*************
CLASS POKEMON2
**************
 Extends Pokemon1 for second evolution stage if applicable, currently halts further evolution, universally enables second move for usage.
*/
final class Pokemon2 extends Pokemon1
{
    Pokemon2(Pokemon p)
    {
        super(p);
        hp = maxHp  = p.maxHp < thresholdHp? p.maxHp * 1.5 : thresholdHp;
        cp          =    p.cp < thresholdCp? p.cp    * 1.5 : thresholdCp;
        stage       = 3;
        listMoves.get(1).isUsable = true;
        ++owner.fullyEvolvedCount;

        Verbose.println(this.nickname + " is now a(n) " + this.listNames.get(stage-1) + ".");
    }

    // no more evolutions
    final Pokemon evolve()
    {
        Verbose.println(this.getFullName() + " is at its final stage!");
        return this;
    }
}
