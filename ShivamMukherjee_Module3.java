/**
 * Club Gen-Y, Programming Domain Assignments Module
 * @author Shivam Mukherjee
 * @version 3.0
*/

import java.util.*;

interface StaticScanner
{
	Scanner scan = new Scanner(System.in);
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  1   )    }        ]                |
|___________________________________________________________________________|

QUESTION Write a program to store the price of items and to print the largest price as well as the sum of prices.

*/

class Solution_01 implements StaticScanner
{
	// the list for storing the prices
	private List<Float> priceList = new ArrayList<>();

	Solution_01()
	{
		float price;

		// works with System.out.println(), allows one to add prices endlessly
		System.out.println("Enter a list of prices. Hit ^C to stop.");
		while (scan.hasNext())
		{
			price = scan.nextFloat();
			priceList.add(price);
			System.out.println("Read value: " + price);
		}
	}

	// get the largest price
	float largest()
	{
		float datPrice = 0;
		for (int i = 0; i < priceList.size(); i++)
		{
			if (datPrice < priceList.get(i))
			{
				datPrice = priceList.get(i);
			}
		}

		return datPrice;
	}

	// get the sum of all prices
	float sum()
	{
		float demPrice = 0;
		for (float datPrice: priceList)
		{
			demPrice += datPrice;
		}

		return demPrice;
	}

	public static void main(String[] args)
	{
		// this line runs endlessly until one hits ^C
		Solution_01 thePriceIsRight = new Solution_01();
		// this step works for System.out.println(), right after the previous function is stopped
		System.out.println("Prices entered.\nLargest: " + thePriceIsRight.largest() + "\nSum: " + thePriceIsRight.sum());
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  2   )    }        ]                |
|___________________________________________________________________________|

QUESTION Write a program to hold the details of 10 students and provide the facility of viewing details of the topper as well as of a specific student by providing his/her roll number.

*/

class Solution_02 implements StaticScanner
{
	// represents each student
	// can be Plain Old Data
	class Student
	{
		// dem fields
		private String roll;
		private String[] name = new String[2];
		private float cgpa;
		private String branch;
		private int combo;

		// scan details of student, while ensuring constraints on content
		// an improvement could be to handle each exception so that the code never crashes
		Student scan()
		{
			System.out.println();
			System.out.print("Enter roll number (must look like \"RA<13 digit number>\"): ");
			this.roll = scan.next("RA[0-9]{13}");
			System.out.print("Enter first and last name (put a 0 if there's no last name): ");
			this.name[0] = scan.next("[A-Z][a-z]*");
			this.name[1] = scan.next("[A-Z0][a-z]*");
			System.out.print("Enter CGPA (0.0 < CGPA < 10.0): ");
			this.cgpa = scan.nextFloat();
			while (this.cgpa < 0 || this.cgpa > 10)
			{
				// re-check CGPA in case it's out of bounds
				System.out.print("Please re-enter CGPA: ");
				this.cgpa = scan.nextFloat();
			}
			System.out.print("Enter branch: ");
			this.branch = scan.next("[A-Za-z]*");
			System.out.print("Enter combination: ");
			this.combo = scan.nextInt();

			return this;
		}

		// print all dem details
		@Override
		public String toString()
		{
			return "\n"
			// there are people without last names
				+ "Name   : " + this.name[0] + (this.name[1].equals("0")? "" : " " + this.name[1]) + "\n"
				+ "Roll   : " + this.roll + "\n"
				+ "CGPA   : " + this.cgpa + "\n"
				+ "Branch : " + this.branch + "\n"
				+ "Combo  : " + this.combo + "\n";
		}
	}

	// the list of students
	private List<Student> records = new ArrayList<>();

	// take details of 10 students
	void scan10Students()
	{
		for (int i = 0; i < 10; i++)
		{
			System.out.println("\nStudent record #" + (i+1));
			records.add(new Student().scan());
		}
	}

	// checks for any collisions in roll numbers
	// an improvement would be to return the roll number itself to avoid typing the whole list again 
	boolean rollsCollide()
	{
		boolean error = false;
		for (Student datStudent: records)
		{
			for (Student proxy: records)
			{
				// checks if the roll numbers match but the objects are different
				if (proxy.roll.equals(datStudent.roll) && proxy != datStudent)
				{
					error = true;
				}
			}
		}

		return error;
	}

	// not too accurate: returns only one topper even if many share the same grade
	// considering grading criteria to be based on other factors like attendance could reduce collisions
	Student getATopper()
	{
		Student datTopper = new Student();
		for (Student someStudent: records)
		{
			if (datTopper.cgpa < someStudent.cgpa)
			{
				datTopper = someStudent;
			}
		}

		return datTopper;
	}

	// gets the student by the numeric part of the roll number
	Student getStudentByRollNumber(String roll)
	{
		Student datStudent = new Student();
		for (Student student: records)
		{
			if (student.roll.endsWith(roll))
			{
				datStudent = student;
			}
		}

		return datStudent;
	}

	public static void main(String[] args)
	{
		// instantiate!
		Solution_02 zohoho = new Solution_02();

		// get 10 students' details
		zohoho.scan10Students();

		// check for collisions of roll numbers
		while (zohoho.rollsCollide())
		{
			System.out.println("\nERROR: There seems to be an issue. Please re-enter the details.");
			zohoho.scan10Students();
		}
		
		// display topper's details by default
		System.out.println("\nOne of the toppers: " + zohoho.getATopper());

		// allow user to inspect other students and the topper
		String choice = "yes";
		while (!choice.startsWith("n"))
		{
			System.out.print("\nCare to ask for any other student's details (yes/no/topper)? ");
			choice = scan.next();
			if (choice.startsWith("y"))
			{
				// allow users to observe roll numbers by typing the last few digits of the roll number
				System.out.print("Enter a few digits from the roll number: ");
				System.out.println("\nYou asked for: " + zohoho.getStudentByRollNumber(scan.next("[0-9]{2,13}")));
			}
			else if (choice.equalsIgnoreCase("topper"))
			{
				System.out.println("\nYou asked for that topper, again: " + zohoho.getATopper());	
			}
		}
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  3   )    }        ]                |
|___________________________________________________________________________|

QUESTION Design a class to represent a bank account. Include the folowing members.
	Data Members
	> Name of depositor
	> Account Number
	> Type of account
	> Balance amount of account

	Methods
	> to assign initial values
	> to deposit an amount
	> to withdraw an amount after checking balance
	> To display name and balance

*/

class Solution_03 implements StaticScanner
{
	Random random = new Random();

	// make some values look like money
	// VS$ is voidstar dollars. (._.) ( ._.) ( '~') ( 'v') ('v') (^v^)
	String money(double in)
	{
		return String.format("VS$ %7.2f", in);
	}

	// an enum to store specific data about fixed account types with different withdrawal and balance limits
	enum AccountType
	{
		SAVINGS(2000, 100000000), CURRENT(2000, 10000000), BUSINESS(10000, 100000000), CREDIT(2000, 10000);
		final double withdrawLimit, balanceLimit;

		AccountType(int w, int d)
		{
			this.withdrawLimit = w;
			this.balanceLimit = d;
		}
	}

	class UnsafeBankAccount
	{
		// dem fields
		private String[] name = new String[2];
		private String id;
		private AccountType type;
		private double balance;

		// make a bank account, with free VS$ 10,000!
		void init()
		{
			System.out.println("\n\tMAKE UNSAFE BANK ACCOUNT.\n");
			System.out.print("Enter your name: ");
			this.name[0] = scan.next("[A-Z][a-z]*");
			this.name[1] = scan.next("[A-Z][a-z]*");
			System.out.print("Specify account type (1: SAVINGS /2: CURRENT /3: BUSINESS /4: CREDIT): ");
			this.type = AccountType.values()[scan.nextInt() - 1];
			// other details are auto-completed
			System.out.println("\nYour account number is " + (this.id = Integer.toString(random.nextInt(100000) + 100000)) + ".");
			System.out.println("You also have a balance of " + money(this.balance = 10000));
			System.out.println("View more details using option (3) in the following menu!");
			System.out.println("\n~ Thank you for choosing Unsafe Bank! ~");
		}

		// display details for that account
		@Override
		public String toString()
		{
			return "\n"
				 + "Account number  : " + this.id + "\n"
				 + "Name            : " + this.name[0] + " " + this.name[1] + "\n"
				 + "Account type    : " + this.type + "\n"
				 + "Withdraw limit  : " + money(this.type.withdrawLimit) + "\n"
				 + "Balance limit   : " + money(this.type.balanceLimit) + "\n"
				 + "Current balance : " + money(this.balance);
		}

		// well, withdraw cash ...
		void withdraw()
		{
			if (this.balance <= 0)
			{
				this.balance = 0;
				System.out.println("You have no balance. Please deposit some cash first.");
				return;
			}

			// bounds-checks the input
			System.out.print("Specify the amount to be withdrawn (< " + money(this.type.withdrawLimit) + "): VS$ ");
			double take = scan.nextDouble();
			while (take < 0 || take > this.type.withdrawLimit)
			{
				System.out.print("Please specify a valid amount for withdrawal: VS$ ");
				take = scan.nextDouble();
			}
			
			this.balance -= take;
			System.out.println("Successfully withdrawn " + money(take) + ". Current balance: " + money(this.balance));
		}

		// ... and deposit cash
		void deposit()
		{
			if (this.balance >= this.type.balanceLimit)
			{
				this.balance = this.type.balanceLimit;
				System.out.println("You are at your balance limit of " + money(this.balance) +". Consider withdrawing cash first.");
				return;
			}

			// bounds-checks the input
			System.out.print("Specify the amount to be deposited: (< " + money(this.type.balanceLimit) + "): VS$ ");
			double give = scan.nextDouble();
			while (give < 0 || give > this.type.balanceLimit)
			{
				System.out.print("Please specify a valid amount for deposit: VS$ ");
				give = scan.nextDouble();
			}

			this.balance += give;
			System.out.println("Successfully deposited " + money(give) + ". Current balance: " + money(this.balance));
		}
	}

	UnsafeBankAccount account = new UnsafeBankAccount();

	// do stuff, like show a menu, select some options ... that sorta stuff
	Solution_03()
	{
		account.init();

		String choice = "yes";
		while (!choice.startsWith("q"))
		{
			System.out.print("\nYou can\n1. Withdraw cash\n2. Deposit cash\n3. Check your account's details. \n(1/2/3/quit): ");
			choice = scan.next();
			switch (choice)
			{
				case "1":
					account.withdraw();
					break;
				case "2":
					account.deposit();
					break;
				case "3":
					System.out.println(account);
					break;
				case "q": case "quit": 
					System.out.println("\n~ Thank you for choosing Unsafe Bank! ~");
					break;
				default:
					System.out.println("Incorrect choice.");
			}
		}
	}

	public static void main(String[] args)
	{
		Solution_03 doStuff = new Solution_03();
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  4   )    }        ]                |
|___________________________________________________________________________|

QUESTION Pattern printing using the concept of classes and objects.

*
**
***
****
*****

*/

class Solution_04 implements StaticScanner
{
	// Classes and objects? Eh? Does using a constructor count?
	Solution_04()
	{
		// running the loop 5 + 1 = 6 times also gives a blank line in the beginning
		for (int i = 0; i <= 5; i++)
		{
			// smart eh?
			for (int j = 0; j < i; j++)
			{
				System.out.print("*");
			}
			// eh?
			System.out.println();
		}
	}

	// ...
	public static void main(String[] args)
	{
		// ... forget it.
		Solution_04 pattyThePattern = new Solution_04();
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  5   )    }        ]                |
|___________________________________________________________________________|

QUESTION Printing UNICODE from 0-100.

*/

class Solution_05 implements StaticScanner
{
	// seems to work perfectly well
	// some characters just can't be printed though
	Solution_05()
	{
		System.out.println("\n\tUNICODE 0-100\n");
		for (char i = 0; i <= 100; i++)
		{
			System.out.println((int)i + ": " + i);
		}
	}

	public static void main(String[] args)
	{
		Solution_05 codePoint = new Solution_05();
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  6   )    }        ]                |
|___________________________________________________________________________|

QUESTION Program to display all prime numbers upto n using a single loop.

*/

class Solution_06 implements StaticScanner
{
	// primes are found recursively
	boolean recurseIsPrime(int x, int i)
	{
		if (i == 1)
		{
			return true;
		}
		if (x % i == 0)
		{
			return false;
		}
		else
		{
			return recurseIsPrime(x, i-1);
		}
	}
	// and a loop to print elements on the list
	void listPrimes(int limit)
	{
		for (int i = 2; i < limit; i++)
		{
			if (recurseIsPrime(i, i/2))
			{
				System.out.print(i + " ");
			}
		}
	}

	// make it work
	Solution_06()
	{
		System.out.print("Gimme n: ");
		listPrimes(scan.nextInt());
	}

	public static void main(String[] args)
	{
		Solution_06 primeTime = new Solution_06();
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  7   )    }        ]     INCOMPLETE |
|___________________________________________________________________________|

QUESTION Write a program to print scoreboard of a cricket match in real time. The display should contain batsman’s name, runs scored, indication for caught, bowled, or run out, bowler’s score (overs bowled, maiden overs, runs given, wickets taken). As and when the ball is thrown, the score should be updated.

*/
//*/
class Solution_07 implements StaticScanner
{
	Random random = new Random();

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
            scan.next();
        }
    }

    class Batsman
    {
    	String name;
    	WicketType wicket;
    	int outAtBall;

    	Batsman()
    	{
    		name = scan.next("[A-Z][a-z]*[ ]?");
    	}
    }

    // for every little mishap on the field
	enum WicketType
	{
		NOT_OUT, CAUGHT, BOWLED, RUN;
	}

	// Are we talking T20 or ODI?
	enum GameType
	{
		T20(120), ODI(300);
		final int overs_x6;

		// overs_x6 is basically 20 or 50 times 6, the number of balls per over
		GameType(int overs_x6)
		{
			this.overs_x6 = overs_x6;
		}
	}

	class TextCricket //extends Thread
	{
		Batsman[] batsmanLineUp = new Batsman[11];
		String[] bowlerLineUp = new String[5];
		int[] battersIndex = new int[2];
		int bowlerIndex;
		GameType type;
		int[] ballStats;
		int maiden;

		TextCricket(GameType type)
		{
			this.type = type;
			if (this.type == GameType.T20)
			{
				this.ballStats = new int[GameType.T20.overs_x6];
			}
			else
			{
				this.ballStats = new int[GameType.ODI.overs_x6];
			}

			for (WicketType x: wickets)
			{
				x = WicketType.NOT_OUT;
			}

			for (int i = 0; i < batsmanLineUp.length; i++)
			{
				System.out.print("Enter the printed name for batsmen's line up (#" + (i+1) + ": ");
				batsmanLineUp[i] = new Batsman();
			}

			for (int i = 0; i < bowlerLineUp.length; i++)
			{
				System.out.print("Enter the printed name for bowlers' line up (#" + (i+1) + ": ");
				bowlerLineUp[i] = scan.nextLine();
			}
		}

		/*/
		@Override
		public void run()
		{

		}
		//*/
	}

	public static void main(String[] args)
	{
		
	}
}
//*/
/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  8   )    }        ]                |
|___________________________________________________________________________|

QUESTION A college maintains a list of  its students graduating every year. At the end of the year, the college produces a report that lists the following :

Year ___________
Number of Working Graduates     :
Number of non-Working Graduates :
Details of the Top-most Scorer
		Name		   :
		Age			   :
		Subject        :
		Average Marks  :
x% of the graduates this year are non-working and n% are first divisioners.


Write a program for it that uses the following inheritance path:

 Person     ----->  Student    ------> Graduate Student
(name, age)	    (rollno, average      (subject, employed)
				 marks)

*/

class Solution_08 implements StaticScanner
{
	static double working = 0, nonWorking = 0;
	static double averageMarks;
	List<GradStudent> list = new ArrayList<>();
	
	String point00(double in)
	{
		return String.format("%.2f", in);
	}

	// A person, with a name and age
	abstract class Person
	{
		String[] name = new String[2];
		int age;

		// an empty constructor helps do certain things, like create placeholder instances
		Person() {}

		// int x is a dummy attribute, for polymorphing over the empty constructor
		Person(int x)
		{
			System.out.print("Enter name: ");
			name[0] = scan.next("[A-Z][a-z]*");
			name[1] = scan.next("[A-Z]?[a-z]*");
			System.out.print("Enter age: ");
			age = scan.nextInt();
		}
	}

	// a Student, inherently also a Person
	abstract class Student extends Person
	{
		String roll;
		double marks;

		Student() {}

		Student(int x)
		{
			super(0xfa15e);
			System.out.print("Enter roll number (RA<13 digits>): ");
			roll = scan.next("RA[0-9]{13}");
			System.out.print("Enter marks (0-100): ");
			marks = scan.nextDouble();
			while (marks < 0 || marks > 100)
			{
				System.out.print("Please re-enter marks: ");
				marks = scan.nextDouble();
			}
		}
	}

	class GradStudent extends Student
	{
		String subject;
		boolean employed;

		GradStudent() {}

		// Construct a grad-student's details and compute mother metrics
		GradStudent(int x)
		{
			super(0xfa15e);
			System.out.print("Enter subject: ");
			subject = scan.next("[A-Za-z]*");
			System.out.print("Enter employment status (true/false): ");
			employed = Boolean.parseBoolean(scan.next("(true|false)"));

			// stores the older total before changing it
			double oldTotal = working + nonWorking;

			if (employed)
			{
				working++;
			}
			else
			{
				nonWorking++;
			}

			// updates average marks
			averageMarks = (averageMarks*oldTotal + marks)/(working + nonWorking);
		}

		@Override
		public String toString()
		{
			return ""
				+ "\tName          : " + name[0] + " " + name[1] + "\n"
				+ "\tAge           : " + age + "\n"
				+ "\tSubject       : " + subject + "\n";
		}
	}

	// checks for any collisions in roll numbers
	// an improvement would be to return the roll number itself to avoid typing the whole list again 
	boolean rollsCollide()
	{
		boolean error = false;
		for (Student datStudent: list)
		{
			for (Student proxy: list)
			{
				// checks if the roll numbers match but the objects are different
				if (proxy.roll.equals(datStudent.roll) && proxy != datStudent)
				{
					error = true;
				}
			}
		}

		return error;
	}

	// returns data for grad-students with first division eligibility
	int getGradsWithFirstDivision()
	{
		int sum = 0;
		for (GradStudent grad: list)
		{
			if (grad.marks > 90)
			{
				sum++;
			}
		}

		return sum;
	}

	// get details of one topper
	GradStudent getATopper()
	{
		GradStudent datTopper = new GradStudent();
		for (GradStudent someStudent: list)
		{
			if (datTopper.marks < someStudent.marks)
			{
				datTopper = someStudent;
			}
		}

		return datTopper;
	}

	Solution_08()
	{
		for (int i = 0; i < 5; i++)
		{
			System.out.println("\nDetails for graduated student #" + (i+1));
			list.add(new GradStudent(0xfa15e));
		}
	}

	@Override
	public String toString()
	{
		return "\n"
			+ "Year 2017\n"
			+ "Number of Working Graduates : " + working + "\n"
			+ "Number of non-Working Graduates : " + nonWorking + "\n"
			+ "Details of the Top-most Scorer\n"
			+ getATopper()
			+ "University average marks : " + point00(averageMarks) + "\n"
			+ point00(nonWorking/(working + nonWorking)*100)
			 + "% of the graduates this year are non-working and " 
			 + point00(getGradsWithFirstDivision()/(working + nonWorking)*100)
			 + "% are first divisioners.";
	}

	public static void main(String[] args)
	{
		System.out.println("\n\tGRADUATION DAY");
		Solution_08 gradifying = new Solution_08();
		while (gradifying.rollsCollide())
		{
			System.out.println("\nERROR: There seems to be an issue. Please re-enter the details.");
			gradifying = new Solution_08();
		}

		System.out.println(gradifying);
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task  9   )    }        ]                |
|___________________________________________________________________________|

QUESTION Imagine a publishing company that markets both books and audio-cassettes versions of its works. Create a class Publication that stores the title and price of a publication. From this class derive two classes :  Book, which adds a page count; and Tape, which adds a playing time in minutes. Each of these classes should have a getdata() function to get its data from the user at the keyboard, and a putdata() function to display its data.

Write a main() program to test the book and tape classes by creating instances of them, asking the user to fill in their data
With getdata(), and then displaying the data with putdata().

*/

class Solution_09 implements StaticScanner
{
	String money(double in)
	{
		return String.format("VS$ %.2f", in);
	}

	abstract class Publication
	{
		String title;
		double price;

		void getdata()
		{
			scan.reset();
			System.out.print("Enter the title <and then hit enter>: ");
			title = scan.nextLine();
			// Scanner#nextLine behaves a bit like gets(), it doesn't consume an additional new line, so here, we make that happen
			// this new line is consumed the next time we use Scanner#nextLine
			scan.nextLine();
			System.out.print("Enter the price: VS$ ");
			price = scan.nextDouble();
		}

		void putdata()
		{
			System.out.print("\n"
				 + "\tTitle: " + title + "\n"
				 + "\tPrice: VS$ " + price + "\n");
		}
	}

	class Book extends Publication
	{
		int pageCount;

		@Override
		void getdata()
		{
			super.getdata();
			System.out.print("Enter a page count: ");
			pageCount = scan.nextInt();
		}

		@Override
		void putdata()
		{
			super.putdata();
			System.out.println("\tPage count: " + pageCount + "\n");
		}
	}

	class Tape extends Publication
	{
		float playTime;

		@Override
		void getdata()
		{
			super.getdata();
			System.out.print("Enter a playback time in minutes: ");
			playTime = scan.nextFloat();
		}

		@Override
		void putdata()
		{
			super.putdata();
			System.out.println("\tPlayback time: " + playTime + "\n");
		}
	}

	Solution_09()
	{
		Publication book = new Book();
		Publication tape = new Tape();

		book.getdata(); book.putdata();
		tape.getdata(); tape.putdata();	
	}

	public static void main(String[] args)
	{
		Solution_09 doStuff = new Solution_09();
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task 10   )    }        ]                |
|___________________________________________________________________________|

QUESTION Define a class called Triangle with three integer data members a, b, and c as the lengths of its three edges. This class should have the following methods: 

(a) a constructor with 3 parameters representing the 3 edges 
(b) a method isTriangle() which returns true if the 3 edges are all positive and they satisfy the triangle inequality where a+b > c, a+c > b, b+c > a. 
(c) a method getAngle() with 1 parameter, an edge, which returns the angle in degrees of the angle facing the given edge. 

The signature of these methods are given below: 

public Triangle(int newa, int newb, int newc) 
public boolean isTriangle() 
public double getAngle(int edge)

 Note: getAngle() should return zero if the triangle is not really a triangle. Also, here are a few formulas to help you define the class: 

FYI, if A is the angle facing side a, then the following formula should help: 

               cosA = (b^2 + c^2 – a^2)/2bc


*/

class Solution_10 implements StaticScanner
{
	String point00(double in)
	{
		return String.format("%.2f", in);
	}

	class Triangle
	{
		// we may use a dictionary in some cases, but plain old double also do the trick
		double a, b, c;

		Triangle(double a, double b, double c)
		{
			this.a = a;
			this.b = b;
			this.c = c;
		}

		public boolean isTriangle()
		{
			boolean arePositive = (a > 0) && (b > 0) && (c > 0);
			boolean satisphyInequality = (a + b > c) && (b + c > a) && (c + a > b);
			return (arePositive && satisphyInequality);
		}

		// get the angle with respect to a given side
		public double getAngle(double l)
		{
			// the other two sides must be defined as well
			double m = 0, n = 0;
			if (l == a)
			{
				m = b;
				n = c;
			}
			else if (l == b)
			{
				m = c;
				n = a;
			}
			else if (l == c)
			{
				m = a;
				n = b;
			}
			else
			{
				return -1.0;
			}

			return Math.acos((m*m + n*n - l*l)/(2*m*n)) * (180/Math.PI);
		}
	}

	Solution_10(double a, double b, double c)
	{
		Triangle trigon = new Triangle(a, b, c);
		if (trigon.isTriangle())
		{
			System.out.println("Triangle(a:" + a + ", b:" + b + ", c:" + c + ")");

			System.out.println(""
				+ "angle A: " + point00(trigon.getAngle(a)) + " degrees\n"
				+ "angle B: " + point00(trigon.getAngle(b)) + " degrees\n"
				+ "angle C: " + point00(trigon.getAngle(c)) + " degrees\n"
				);
		}
		else
		{
			System.out.println("Triangle(ERROR: quitting)");
		}
	}

	public static void main(String[] args)
	{
		Solution_10 tryangel = new Solution_10(3, 4, 5);
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task 11   )    }        ]                |
|___________________________________________________________________________|

QUESTION Write a program to store the price of items and to print the largest price as well as the sum of prices.

*/

class Solution_11 implements StaticScanner
{
	class Student
	{
		private int score;
		private double hoursAttended;
		private static final double totalHours = 69;
		private int decibels;

		Student(int score, double hoursAttended, int decibels)
		{
			this.score = score;
			this.hoursAttended = hoursAttended;
			this.decibels = decibels;
		}

		public int getScore()
		{
			return score;
		}

		public boolean isAttendanceGood()
		{
			return (hoursAttended/totalHours * 100 >= 75);
		}

		public boolean isTalkative()
		{
			return (decibels >= 80);
		}
	}

	void printGrade(Student s1)
	{
		System.out.print("Student supplied has been graded ");
		if (s1.score > 80)
		{
			System.out.println("A.");
		}
		else if (s1.score > 60 && s1.score < 80)
		{
			System.out.println("B.");
		}
		else
		{
			if (s1.score == 80)
			{
				if (s1.isAttendanceGood())
				{
					System.out.println("A.");
				}
				else
				{
					System.out.println("B.");
				}
			}
			else if (s1.score == 60)
			{
				if (s1.isTalkative())
				{
					System.out.println("C.");
				}
				else
				{
					System.out.println("B.");
				}
			}
			else
			{
				System.out.println("C.");
			}
		}
	}

	Solution_11()
	{
		Student padhaku = new Student(95, 62, 74);
		Student aam = new Student(69, 42, 83);
		Student pencho = new Student(60, 50, 91);

		this.printGrade(padhaku);
		this.printGrade(aam);
		this.printGrade(pencho);
	}

	public static void main(String[] args)
	{
		Solution_11 judgemental = new Solution_11();
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task 12   )    }        ]                |
|___________________________________________________________________________|

QUESTION Write a program to store the price of items and to print the largest price as well as the sum of prices.

*/

class Solution_12 implements StaticScanner
{
	int n;

	Solution_12()
	{
		System.out.print("Enter a string: ");
		String pal = scan.nextLine();
		n = pal.length();

		if (isPalindrome(pal))
		{
			System.out.println("\"" + pal + "\" is a palindrome.");
		}
		else
		{
			System.out.println("\"" + pal + "\" isn't a palindrome.");
		}
	}

	boolean isPalindrome(String str, int i)
	{
		if (i < n/2)
		{	
			if (Character.toLowerCase(str.charAt(i)) == Character.toLowerCase(str.charAt(n-i-1)))
			{
				return isPalindrome(str, ++i);
			}
			else
			{
				return false;
			}
		}

		return true;
	}

	boolean isPalindrome(String str)
	{
		return isPalindrome(str, 0);
	}

	public static void main(String[] args)
	{
		Solution_12 myPalInRome = new Solution_12();
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task 13   )    }        ]                |
|___________________________________________________________________________|

QUESTION Several design properties for a problem are presented below. Use these properties in order to write all the necessary classes and/or interfaces for a solution to the problem. Focus on class structure and interaction. You may implement your solution however you wish, but you will be graded on the appropriateness of your solution to the requirements. Note the use of capitalization and parentheses for clarification. You may use whatever constructors or additional methods you wish. 

You must define a structure that can represent Animals. Animals have two behaviors; they can speak() and they can move(). By default, when an animal moves, the text "This animal moves forward" is displayed. By default, when an animal speaks, the text "This animal speaks" is displayed. A general Animal should not be able to be instantiated. 

Define also two classes, Goose and Lynx, that are Animals. Both Goose and Lynx behave such that where "animal" is displayed in speak() or move(), "goose" or "lynx" is displayed by the appropriate classes. 

Finally, any instance of Goose can fly(), just as any Flying object can. An Airplane is also a Flying object. Define the Airplane class such that it is Flying and make sure that any instance of Goose is also Flying. The specific behaviors when instances of either class fly() are up to you. Instances of either Goose or Airplane should be able to be stored in a variable of type Flying.

*/

class Solution_13 implements StaticScanner
{
	// Easy stuff.
	abstract class Animal
	{
		String what; // reflection is performance intensive

		Animal()
		{
			this.what = "animal";
		}

		void speak()
		{
			System.out.println("This " + what + " speaks");
		}

		void move()
		{
			System.out.println("This " + what + " moves forward");
		}
	}

	@FunctionalInterface
	interface Flying
	{
		// public by default!
		void fly();
	}

	class Airplane implements Flying
	{
		@Override
		public void fly()
		{
			System.out.println("The skies are now the domain of humankind");
		}
	}

	class Lynx extends Animal
	{
		Lynx()
		{
			this.what = "lynx";
		}
	}

	class Goose extends Animal implements Flying
	{
		Goose()
		{
			this.what = "goose";
		}

		public void fly()
		{
			System.out.println("Geese can fly, since they're birds");
		}
	}

	Solution_13()
	{
		// Inheritance allows us to store subclass instances in super class/interface references
		Animal lynux = new Lynx();
		Animal goosling = new Goose();
		Flying b21 = new Airplane();
		Flying sikh = new Goose();

		// methods common to the subclass will be okay
		System.out.println("\tLynux the lynx.");
		lynux.speak();
		lynux.move();
		System.out.println();

		System.out.println("\tB21 the airplane.");
		b21.fly();
		System.out.println();

		System.out.println("\tGoosling the goose.");
		goosling.speak();
		goosling.move();
		// but a type conversion to the subclass allows usage of subclass-specific functions
		((Goose)goosling).fly();
		System.out.println();

		System.out.println("\tSikh the goose.");
		((Goose)sikh).speak();
		((Goose)sikh).move();
		sikh.fly();
	}

	public static void main(String[] args)
	{
		Solution_13 geneticsKaBhai = new Solution_13();
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task 14   )    }        ]     INCOMPLETE |
|___________________________________________________________________________|

QUESTION Write a class, call it GradesCount, to read a list of grades from the keyboard (integer numbers in the range 0 to 100). Prompt the user with "Please enter a grade between 0 to 100 or -1 to quit: " each time before reading the next integer. Store each grade in a A, B, C, D or F Vector as follows: 90 to 100 = A, 80 to 89 = B, 70 to 79 = C, 60 to 69 = D, and 0 to 59 = F. (Hint: You cannot store ints as Vector elements, but you can store Integers.) 

Output the total number of grades entered, the number of A, B, C, D and F, and a list of the A’s. For example, if the input is... 
 38
 86
 92
 55
 83
 42 
 90 
 -1 
then the output should be:
 Total number of grades = 7
 Number of A = 2
 Number of B = 2
 Number of C = 0 
 Number of D = 0 
 Number of F = 3 
 The A grades are: 92, 90
*/

//*/
class Solution_14 implements StaticScanner
{
	Vector<Integer> A = new Vector<>();
	Vector<Integer> B = new Vector<>();
	Vector<Integer> C = new Vector<>();
	Vector<Integer> D = new Vector<>();
	Vector<Integer> F = new Vector<>();

	String getStraightAs()
	{
		String stringOfAs = "";	
		for (int i = 0; i < A.size(); i++)
		{
			if (i == A.size()-1)
			{
				stringOfAs += A.get(i);
			}
			else
			{
				stringOfAs += (A.get(i) + ", ");
			}
		}

		return stringOfAs;
	}

	@Override
	public String toString()
	{
		return "\n"
			+ "Total number of grades = " + (A.size() + B.size() + C.size() + D.size() + F.size()) + "\n"
			+ "Number of A = " + A.size() + "\n"
			+ "Number of B = " + B.size() + "\n"
			+ "Number of C = " + C.size() + "\n"
			+ "Number of D = " + D.size() + "\n"
			+ "Number of F = " + F.size() + "\n"
			+ "The A grade" + (A.size() > 1? "s are: ": " is: ") + getStraightAs();

	}

	Solution_14()
	{
		System.out.print("Please enter a grade between 0 to 100 or -1 to quit: ");

		while(scan.hasNext())
		{
			System.out.print("Please enter a grade between 0 to 100 or -1 to quit: ");
			int grade = scan.nextInt();
			while (grade < -1 || grade > 100)
			{
				System.out.print("Enter a valid grade: ");
				grade = scan.nextInt();
			}

			if (grade == -1)
			{
				break;
			}
			
			if (grade >= 90 && grade <= 100)
			{
				A.add(grade);
			}
			else if (grade >= 80 && grade < 90)
			{
				B.add(grade);
			}
			else if (grade >= 70 && grade < 80)
			{
				C.add(grade);
			}
			else if (grade >= 60 && grade < 70)
			{
				D.add(grade);
			}
			else
			{
				F.add(grade);
			}
		}

		System.out.println(this);
	}

	public static void main(String[] args)
	{
		Solution_14 straightAs = new Solution_14();
	}
}
//*/

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task 15   )    }        ]                |
|___________________________________________________________________________|

QUESTION Pattern priting using concepts of class and objects.

     *
    * *
   * * *
  * * * *
 * * * * *

*/

class Solution_15
{
	// A little constructor
	Solution_15()
	{
		for (int i = 0; i <= 5; i++)
		{
			// two j's for the same level
			for (int j = 0; j < 5-i; j++)
			{
				System.out.print(" ");
			}

			for (int j = 0; j < i; j++)
			{
				System.out.print("* ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args)
	{
		Solution_15 pattinsonThePattern = new Solution_15();
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task 16   )    }        ]                |
|___________________________________________________________________________|

QUESTION Pattern priting using concepts of class and objects.

     *
    * *
   * * *
  * * * *
 * * * * *
  * * * *
   * * *
    * *
     *
*/

class Solution_16 extends Solution_15
{
	// I'm lazy.
	Solution_16()
	{
		// Solution_15() runs once again
		super(); // INHERITANCE!!!

		for (int i = 0; i < 4; i++)
		{
			// this looks familar
			for (int j = 0; j <= i; j++)
			{
				System.out.print(" ");
			}

			for (int j = 0; j < 4-i; j++)
			{
				System.out.print("* ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args)
	{
		Solution_16 pattinsonJrThePattern = new Solution_16();
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task 17   )    }        ]                |
|___________________________________________________________________________|

QUESTION Pattern printing using a single for loop.

     *
    * *
   * * *
  * * * *
 * * * * *

*/

class Solution_17
{
	// Three words: recursion.
	static void recursivePrint(String s, int j, int n)
	{
		if (j < n)
		{
			// be really careful about the pre-increment!
			// post incrementing crashes the program since the value supplied won't change for the next function call
			System.out.print(s);
			recursivePrint(s, ++j, n);
		}
	}

	public static void main(String[] args)
	{
		// one for-loop for 'em
		for (int i = 0; i <= 5; i++)
		{
			recursivePrint(" ",  0, 5-i);
			recursivePrint("* ", 0, i);
			System.out.println();
		}
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task 18   )    }        ]                |
|___________________________________________________________________________|

QUESTION Pattern priting using the concept of class and objects.

*****
 ****
  ***
   **
    *

*/

class Solution_18
{
	public static void main(String[] args)
	{
		System.out.println();
		for (int i = 0; i < 5; i++)
		{
			// '<=' allows an additional space, which looks neat
			for (int j = 0; j <= i; j++)
			{
				System.out.print(" ");
			}
			for (int j = 0; j < 5-i; j++)
			{
				System.out.print("*");
			}
			System.out.println();
		}
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task 19   )    }        ]                |
|___________________________________________________________________________|

QUESTION Write a program to reverse the string word by word.

E.g. "My name is definitely not Dipanshu"

Output should be: "Dipanshu not definitely is name My"

*/

class Solution_19 implements StaticScanner
{
	public static void main(String[] args)
	{
		System.out.print("Enter the string: ");
		// long line does the magic
		List<String> viceVersa = Arrays.asList(scan.nextLine().split(" "));
		Collections.reverse(viceVersa);
		// print it back in a way which looks good
		for (String word: viceVersa)
		{
			System.out.print(word + " ");
		}
	}
}

/*
 ___________________________________________________________________________
|                                                                           |
|                [        {    (   Task 20   )    }        ]                |
|___________________________________________________________________________|

QUESTION Write a program to calculate surface area and volume of the given figure:

(Figure shows a pencil stub-like frame with a cone on top of a cylinder, on top of a hemisphere. We may assume radius, cylinder's height and cone's altitude as parameters.)

Side view:
      ^
     /|\              ^
    / |-\--->altitude | cone
   /__v__\            v
   <----->-->radius
   |  ^  |            ^
   |  |--|-->height   | cylinder
   |  v  |            v
   <--^-->-->radius   ^
   '  |--'-->radius   | hemisphere
   '._v_.'            v

*/

class Solution_20 implements StaticScanner
{
	// the parameters
	double radius, cylinderHeight, coneAltitude;

	// construct the object from these requirements
	Solution_20(double r, double h, double a)
	{
		radius = r;
		cylinderHeight = h;
		coneAltitude = a;
	}

	double surfaceArea()
	{
		// Equations for surface area
		double cone = Math.PI * radius * Math.hypot(radius, coneAltitude);
		double cylinder = 2 * Math.PI * radius * cylinderHeight;
		double hemisphere = 2 * Math.PI * Math.pow(radius, 2);
		return (cone + cylinder + hemisphere);
	}

	double volume()
	{
		// Equations for volume
		double cone = 1/3 * Math.PI * Math.pow(radius, 2) * coneAltitude;
		double cylinder = Math.PI * Math.pow(radius, 2) * cylinderHeight;
		double hemisphere = 2/3 * Math.PI * Math.pow(radius, 3);
		return (cone + cylinder + hemisphere);
	}

	public static void main(String[] args)
	{
		System.out.println("\n\tPENCIL CROSS-SECTION\n\n"
			+ "\t   ^\n"
			+ "\t  /|\\              ^\n"
			+ "\t / |-\\--->altitude | cone\n"
			+ "\t/__v__\\            v\n"
			+ "\t<----->-->radius\n"
			+ "\t|  ^  |            ^\n"
			+ "\t|  |--|-->height   | cylinder\n"
			+ "\t|  v  |            v\n"
			+ "\t<--^-->-->radius   ^\n"
			+ "\t'  |--'-->radius   | hemisphere\n"
			+ "\t'._v_.'            v\n");

		System.out.print("Enter the radius, cylinder height and cone altitude: ");
		Solution_20 pencil = new Solution_20(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
		System.out.println("Surface area : " + pencil.surfaceArea());
		System.out.println("Volume       : " + pencil.volume());
	}
}