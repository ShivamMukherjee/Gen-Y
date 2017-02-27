/**
 * GenY Programming Club - Tasks Module
 * @author  Shivam Mukherjee
 * @version 2.0 
 *
 */

/*
Note: Task 5 sold separately!
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  1   )    }        ]                |
|___________________________________________________________________________|

We notice first off from the classic "Dating GF" introductory program that Java is designed for immediate use as an Object Oriented Programming (OOP) Language,  while C++, having its foundations in C, looks more procedural in nature at first glance.

Simply put: consider between dating GF#1 who's a wiz at the art of tech versus GF#2 who acquired talent in techy things over time - GF#1 almost always types her assignments on her laptop, while GF#2 may Google facts like a pro but goes for old-fashioned pen and paper to settle things.

Java doesn't allow use global, non-class functions (experts - please don't pester me with lambdas), while C++ allows many things, since it is always compatible (to a large extent) with existing C code. 

To instantiate a class (read: make objects), which Java does dynamically - giving objects memory when the program is executed instead of during compile time, one uses the "new" keyword. Objects are something Java uses all the time, and are used to store (or not) data and do things  with said data (or just do things using static methods - task 2). Here we describe a rudimentary Pokemon class with some data and some methods. To do stuff.

Note: actual Pokemon GO data will make you go insane. I'll summarise the data for readability in this example.
 > No candy
 > no stardust
 > no weight or height.
*/

import java.util.*;
import java.lang.Math;
import java.math.RoundingMode;
import java.text.DecimalFormat;

abstract class NotPokemon
{
    protected List<String> names = new ArrayList<>();
    protected String currentName;
    // These are lists of strings for now.
    protected List<String> types = new ArrayList<>();
    protected List<String> moves = new ArrayList<>(); // Just strings for now.
    protected String nickname;
    protected double hp, maxHp, cp;
    protected int evolution;      // For now.


    // We'll need this for randomising things
    protected Random random = new Random();

    // And this to trim the decimal places (without using prin... *cough* ...tf)
    protected DecimalFormat boxOp = new DecimalFormat("#");
    // Seriously Java?

    // attack and dodge functions as per requirement
    // some generic magic used here (no pun intended)
    abstract public double getHp();
    abstract public <T extends NotPokemon> void attack(T opponent);
    abstract public double dodge();
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  2   )    }        ]                |
|___________________________________________________________________________|

Now, Java has a tendency to look like C++ in some respects - just some respects.
 
In C++ (from C), a variable or function with the "static" specifier has a non-local, file binding. This is something like saying that the entity's lifespan is not simply the scope in which it is declared, but the entire file that it is contained in. This extends to classes where static functions and data members are shared among all objects of a given class, and can be scoped with the class' name itself (main() in C/C++ itself is global in scope, thus actually also can be called beyond the file it is declared in).

It is so even for Java - but here, the scoping operator is the same as the member operator - "." - and thus the syntax looks more simplified compared to C++, with individual scope resolution, member of object and member of pointer to object operators ("::", ".", "->"). This allows for things via java.lang.Math and java.util.regex.Pattern, which have static methods and constants, like Math.PI, Math.pow(), Pattern.match(), etc.

Now Java makes it slightly more convenient for doing things with a main() (note: not "The" main()) method in a class. After compilation, one can make any class an entry point in the program: so any class can have a public static function called main() which takes a list of String arguments as an array (or a variadic list - String... - that's the syntax. ... So much ... to explain ... later ...) and returns void. In short - anything that looks like "public static void main(String[] argzz)" is a valid entry-point if deemed so by using "java EntryPointClass" on the command line/shell after compiling a java file/package. In C++, without invalidating the standard usage by changing it in the linkers (please just Google things for once), this is not supported - there is one global main(), the only entry-point of execution.

Okay, so Java may not look like C++ in most respects after all.

There are implications of declaring fields or methods "static" when it comes to compilation, such as linking and other other complex nerdspeak (Google).
*/


//__Trivial Solution__
// public
class BeforeJavaSE7
{
    static {
        System.out.println("Buahahaha there will be no main!!");
        System.exit(0);
    }
}

/*
In the aforementioned monstrosity, the statements in the curly braces right after "static" execute even before the compiler looks for the main() function, and System.exit(0) kills the program before a main() is even found.Et tu static? Then fall main();

These "static blocks" could be executed before calls to anything, including main(), until Java SE 7, when this evil practice was abolished.

Now the code above is just a delusional psychopath crying from within its bounds. For no way am I running that class. Even if it's JavaSE 8 already.

Imscaredpleasecanwejustadvance
*/

/* 
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  3   )    }        ]                |
|___________________________________________________________________________|

One can always use special constructor methods to initialise class objects. Constructors share the name of the class they are contained in. For other initialising purposes, we look at accessors and mutators (or their more morose names, getters and setters, respectively).
*/

// We inherit most of the jazz from NotPokemon, see Task 1
// demo class, not inherited everywhere
class AlmostPokemon extends NotPokemon
{
    // We make sure no instance is inititialised without its name(s)
    // this replaces the implicitly defined constructor.
    // Without setters for these fields, there wouldn't be a way to edit these 
    // parameters easily, unless the object is reinitialised.
    public AlmostPokemon(String name1, String name2, String name3)
    {
        this.names.addAll(Arrays.asList(name1, name2, name3));
        this.evolution = 0;
        this.currentName = this.names.get(this.evolution);
    }

    protected AlmostPokemon(AlmostPokemon copy)
    {
        // Empty constructor - since it's not needed here
        // this allows the constructor to be visible to subclasses
    }

    protected AlmostPokemon()
    {
        // this one too
    }
    
    // We can check the stats of these AlmostPokemon
    @Override
    public String toString()
    {
        return ("\n"
                + "Nickname        : " + this.nickname              + "\n"
                + "Name            : " + this.currentName           + "\n"
                + "All names       : " + this.names                 + "\n"
                + "Types           : " + this.types                 + "\n"
                + "Evolution Stage : " + (this.evolution + 1)       + "\n"
                + "HP              : " + boxOp.format(this.getHp()) + "/"
                                       + boxOp.format(this.maxHp)   + "\n"
                + "CP              : " + this.cp                    + "\n"
                + "Moves           : " + this.moves                 + "\n");
    }

    // setter methods: note how they can share the same name as their parameters
    // no way to add moves to regular AlmostPokemon objects

    // Oh, did I mention they almost looked like I picked them up from a Builder?
    public AlmostPokemon setTypes(String firstType, String secondType)
    {
        this.types.add(firstType);
        this.types.add(secondType);
        return this;
    }

    public AlmostPokemon setNickname(String nickname)
    {
        this.nickname = nickname;
        return this;
    }

    public AlmostPokemon setHp(double maxHp)
    {
        this.maxHp = maxHp;
        this.hp    = maxHp;
        return this;
    }

    public AlmostPokemon setCp(double cp)
    {
        this.cp = cp;
        return this;
    }
    // So now I can make objects for this class and initialise its fields too!


    // Now for the generic methods - some misc methods, attack(), dodge()

    @Override
    // This makes hp look like a health bar and keeps it from looking crazy
    public double getHp()
    {
        if (this.hp > this.maxHp)
            this.hp = this.maxHp;

        if (this.hp > 0)
            return this.hp;
        else
            return 0;
    }

    // Full restore ability, was using it in tests
    public void fullRestore()
    {
        if (this.hp < this.maxHp)
        {
            this.hp = this.maxHp;
//*/
            System.out.println(this.nickname + " (" + this.currentName + ") "
            + "miraculously recovered all HP!");
//*/
        }
        else
        {
            System.out.println("Best wishes to " + this.nickname + ". ");
        }
    }

    @Override
    public <T extends NotPokemon> void attack(T opponent)
    {
        if (this.nickname == opponent.nickname)
        {
            // Unless you're emo
            System.out.println(this.nickname + ": cannot harm self.");
            return;
        }

        if (opponent.hp > 0 && this.hp > 0)
        {
            // Some math
            double damage = this.cp * 0.1 * opponent.dodge();
            opponent.hp -= damage;
 //*/
            System.out.println(
                this.nickname + " dealt " + boxOp.format(damage) + " damage to "
                + opponent.nickname + ".\n"
                + opponent.nickname + "(" + opponent.currentName + ")" + " now has "
                + boxOp.format(opponent.getHp()) + "HP left.\n");
//*/
            return;
        }
//*/
        else if (opponent.hp <= 0)
        {
            System.out.println(opponent.nickname + "(" + opponent.currentName + ")"
             + " has fainted. No damage dealt by " + this.nickname + ".\n");
            return;
        }
        else// if (this.hp <= 0)
        {
            System.out.println(this.nickname + "(" + this.currentName + ")"
             + " has fainted. No damage dealt to " + opponent.nickname + ".\n");
            return;
        }
//*/
    }

    @Override
    public double dodge()
    {
        int dieRoll  = random.nextInt(6) + 1;
        double skill = this.cp/1000, multiplier;

        if (dieRoll == 6)
        {
            // successful dodge
            multiplier = 0;
        }
        else if (dieRoll == 4 || dieRoll == 5)
        {
            // 20% less damage
            multiplier = 0.8;
        }
        else if (dieRoll == 2 || dieRoll == 3)
        {
            // normal damage
            multiplier = 1;
        }
        else
        {
            // 20% more damage
            multiplier = 1.25;
        }

//*/
        System.out.println(""
            + "Die roll: " + dieRoll + ", "
            + "Result: "   
            + boxOp.format(multiplier*100)
            + (multiplier != 0? "% - " + boxOp.format(skill*100) : "")
            + "% = "
            + (multiplier != 0? boxOp.format(100*(multiplier - skill)) : "0")
            + "% damage to " + this.nickname + "(" + this.currentName + ").");
//*/
        return multiplier != 0? multiplier - skill : multiplier;
    }

    // Finally, the evolve()
    public AlmostPokemon1 evolve()
    {
        return new AlmostPokemon1(this);
    }
}

/* 
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  4   )    }        ]                |
|___________________________________________________________________________|

Evolving these so-called Pokemon? Pfft. Easy-peasy. Or is it?

Let's do some more inheritence (sorry for doing it earlier than asked _/\_) 
with our original NotPokemon class, which should look a wee-bit more like 
some workable type. To the exceptions: let's catch 'em all.

Jk I'm not using exceptions I'm still a n00b

Oh, I also assume that Eevee doesn't exist, for now  - I am not implementing a way to 
express multiple evolution here: this is a simple example.

Also note that Task 5 is gonna be way bigger than this - no kidding.
*/

class AlmostPokemon1 extends AlmostPokemon // should be fairly obvious
{
    AlmostPokemon1(AlmostPokemon copy)
    {
        this.names       = copy.names;
        this.types       = copy.types;
        this.moves       = copy.moves;
        this.nickname    = copy.nickname;
        // Here's how AlmostPokemon2 never has to do anything to tweak values
        this.evolution   = copy.evolution + 1;
        this.maxHp       = copy.maxHp + 10 * this.evolution;
        this.hp          = this.maxHp;
        this.cp          = copy.cp    + 50 * this.evolution;
        this.currentName = copy.names.get(this.evolution);
    }

    public AlmostPokemon1 addMove(String move)
    {
        this.moves.add(move);
        return this;
    }

    // No more evolves
    public final AlmostPokemon2 evolve()
    {
        return new AlmostPokemon2(this);
    }
}

// No more evolutions past Stage 2, for now
final class AlmostPokemon2 extends AlmostPokemon1
{
    AlmostPokemon2(AlmostPokemon copy)
    {
        // calls AlmostPokemon1() with tweaks on values.
        super(copy);
    }

    // So an AlmostPokemon2 doesn't get back an AlmostPokemon1
    // why did I make this naming convention even
    public AlmostPokemon2 addMove(String move)
    {
        this.moves.add(move);
        return this;    
    }
}

final class Test
{
    public static void combat(AlmostPokemon p, AlmostPokemon q)
    {
        // CAUTION - EXPERIMENTAL TESTING ZONE
        // I used this to test for winning probability/bias
        int winP = 0, winQ = 0;
        for (int i = 0; i < 100; i++)
        {
            // Let my instances start battle-ready
            System.out.println("[Match: " + (i+1) + "]");
            p.fullRestore();
            q.fullRestore();

            for (int j = 0; p.getHp() > 0 && q.getHp() > 0; j++)
            {
                // After thorough tests,
                // I noticed for the same level the first to attack has greater odds
                // this could be an important mechanic
 
                System.out.println("[Turn: " + (j+1) + "]");
                
                
                // p goes first
                p.attack(q);
                q.attack(p);
            }

            // Check who got knocked flat
            if (p.getHp() > 0)
                pWins++;
            else
                qWins++;

            System.out.println("REMATCH");
        }

        System.out.println("\nWinner: \n"
                 + ((pWins > qWins && qWins < pWins)? 
                   p + " with " + pWins
                 : q + " with " + qWins) + " wins.");


    }

    // There were some lies about main()
    public static void main(String[] args)
    {
        // New instance!
        AlmostPokemon p = new AlmostPokemon("Charmander", "Charmeleon", "Charizard")
                              .setTypes("Fire", "").setHp(25).setCp(100)
                              .setNickname("Burnie Sanders");
        // Gimme stats!
        System.out.println(p);

        // Evolve my instance! Add a move! Show me new stats!
        AlmostPokemon1 p1 = p.evolve().addMove("King of the hillary");
        System.out.println(p1);

        // Do that again!
        AlmostPokemon2 p2 = p1.evolve().addMove("trump Drumpf");
        System.out.println(p2);

        // Rinse and repeat!
        AlmostPokemon q = new AlmostPokemon("Bulbasaur", "Ivysaur", "Venusaur")
                            .setTypes("Grass", "Poison").setHp(25).setCp(100)
                            .setNickname("Neil deGrasse Tyson");
        System.out.println(q);

        AlmostPokemon1 q1 = q.evolve().addMove("Sciens");
        System.out.println(q1);

        AlmostPokemon2 q2 = q1.evolve().addMove("Astrophyjx");
        System.out.println(q2);

        // testing attacks after hp reduces to 0
        // combat(q2, p2);
    }
}

/*//////////////////////////////////////////////////////////////////

Conclusively - I did accomplish the behaviour of evolution in Pokemon using Inheritance (and broke character from the rest of my document). 

*/

/* 
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  5   )    }        ]                |
|___________________________________________________________________________|

... err, sorry:

 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  6   )    }        ]                |
|___________________________________________________________________________|

(Task 5 sold separately)

We descend from the turbidity of defining things to simpler tasks - String Manipulation. However, we must recall that Strings in Java are immutable - their inner state can't be altered. Unless it's very important to manage resources, well ... there's nothing that can be done simply. There are some ingenuities used in Java such as String-literal interned pools, classes such as StringBuilder and StringBuffer (the latter can be used with threads) to manipulate the same data referred to by a String. But, you may never know their advantages - compilers are smarter these days, trimming away at inefficient looking constructs. E.g. (errr scroll please)
*/

interface HasAScanner
{
    final static Scanner scan = new Scanner(System.in);
}


class C implements HasAScanner
{
    // the second class with a main
    public static void main(String[] args)
    {
        // I got fed up and decided to do some C
        printf("Enter an int and a float: ");

        // Of course, how could I? There's no '&'
        // I passed an array. *Sigh*
        // That too, to Boxed classes *sob*
        Integer d[] = {0};
        Float   f[] = {0.0f};

        // C is incomplete without '&'
        // i cry everytime
        scanf("%d %f", d, f);
        printf("Int: %d, Float: %.1f\n", d[0], f[0]);

        // strings are more natural to pass to, though
        printf("Enter a string to mirror: ");
        //Forget what I said
        String x[] = {""};

        // Remember, only one string is scanned, so multiple words get ignored
        scanf("%s", x);

        // next time, let's just use Java, k?
        printf("\n %s | %s\n\n", x[0], strrev(x[0]));

        // Ahem ... let's move on (this is Task 7 turf)
        System.out.print(
            "Now gimme a string and make me do something with it.\n"
            + "A. Self-concatenate said string.\n"
            + "B. Replace alternate positions with a character ... \n"
            + "C. Remove duplicate characters in said string.\n"
            + "D. Change alternate characters to uppercase.\n"
            + "(<input> <space> A/B/C/D): ");

        String input = scan.next();

        switch (scan.next().charAt(0))
        {
            case 'a': case 'A':
            // we could use a StringBuilder but meh
                System.out.println(input + " => " + input + input);
                break;

            case 'b': case 'B':
                System.out.print("... Enter it: ");
                System.out.println(
                    input + " => " + replaceAltChars(input, scan.next().charAt(0)));
                break;

            case 'c': case'C':
                System.out.println(input + " => " + removeDuplicates(input));
                break;

            case 'd': case 'D':
                System.out.println(input + " => " + toUpperAltChars(input));
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    // Java makes things easier for new programmers: Exhibit A
    static String strrev(String toRev)
    {
        // Used a StringBuilder/StringBuffer to trivialise task 6
        return new StringBuilder(toRev).reverse().toString();
        // Task 7 uses a s**t ton of 'em
    }

    // C! Oh my!

    /*
    ASIDE: variadic arguments using "..."

    The tradition of C and C++ in having functions that mysteriously take infinite arguments (read: the printf()/scanf() family) continues in many programming languages - even Java.

    Java makes it simple for taking variadic arguments by allowing the developer to consider the Type... list as a Type[] array, without any more cumbersome syntax. Thus, System.out.printf() exists, and I can make my own scanf() function, though it's simply a farce.
    */

    // Note: please don't use Object for raw generic-like behaviour in production code
    static void printf(String format, Object... objects)
    {
        // This ... exists. What even
        System.out.printf(format, objects);
    }

    // again, please don't use raw generic-like behaviour in production code
    // btw use spaces inside "fmt", or else it suddenly misbehaves
    static void scanf(String fmt, Object[]... objects)
    {
        Scanner format = new Scanner(fmt);

        // lol, using regex to simulate far inferior C-style formatting
        for (int i = 0; format.hasNext(); i++, format.next())
        {
            if (format.hasNext("%s"))
            {
                objects[i][0] =  scan.next();
            }
            if (format.hasNext("%c"))
            {
                objects[i][0] = scan.next().charAt(0);
            }
            else if (format.hasNext("%[di]"))
            {
                objects[i][0] = scan.nextInt();
            }
            else if (format.hasNext("%f"))
            {
                objects[i][0] =  scan.nextFloat();
            }
            else if (format.hasNext("%l[di]"))
            {
                objects[i][0] =  scan.nextLong();
            }
            else if (format.hasNext("%lf"))
            {
                objects[i][0] =  scan.nextDouble();
            }
        }
    }

/*

And finally,
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  7   )    }        ]                |
|___________________________________________________________________________|

*/
    static String replaceAltChars(String input, char ch)
    {
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < input.length(); i++)
        {
            // All odd places altered (0-index is even)
            if (i % 2 == 1)
            {
                sb.setCharAt(i, ch);
            }
        }
        return sb.toString();
    }

    static String removeDuplicates(String input)
    {
        // Being implemented as a linked list, this also preserves the order
        Set<Character> hashSet = new LinkedHashSet<>();

        for(int i = 0; i < input.length(); i++)
            // Here's the trick
            hashSet.add(input.charAt(i));
            // Now, duplicates are automagically removed

        // return a String after deleting duplicate elements
        StringBuilder sb = new StringBuilder(hashSet.size());
        for(Character ch : hashSet)
        {
            sb.append(ch);
        }
        return sb.toString();
    }

    static String toUpperAltChars(String input)
    {
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < input.length(); i++)
        {
            // All odd places altered (0-index is even)
            if (i % 2 == 1)
            {
                sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
            }
        }
        return sb.toString();
    }
}

/*
So that would be that with module 2, excluding Task 5. Learning a new coding language is always a refreshing experience, where the attempt always lies in generalising the knowledge so that a point of time comes when we can disassociate basic constructs from any one specific language, and become super awesome coders who can learn any language in a matter of days.
*/