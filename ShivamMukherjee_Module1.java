import java.util.*;
import java.lang.Math;

class First
{
	private static boolean isPrime(int i, int divisor)
	{
		if (i == 1)
		{
			return false;
		}

		if (divisor <= 1)
		{
			return true;
		}

		if (i % divisor == 0)
		{
			return false;
		}

		return isPrime(i, divisor-1);
	}

	public static void dysfunctionalIDE()
	{
		Scanner scan = new Scanner(System.in);
        int n = 0;

        System.out.print("[First]\nEnter a number: ");
                
		if (scan.hasNextInt())
		{
			n = scan.nextInt();
		}

		System.out.println
		(
			n + " is "
			+ ((n%2 == 0)? "even and " : "odd and ")
			+ ((isPrime(n, n-1))? "prime." : "not prime.")
		);
	}
}

class Second
{
	public static void pattern12345()
	{
		System.out.println("\n[Second]");

		for (int i = 5; i > 0; i--)
		{
			for (int j = 1; j <= i; j++)
			{
				System.out.print(j);
			}

			System.out.println();
		}

		for (int i = 1+1; i <= 5; i++)
		{
			for (int j = 1; j <= i; j++)
			{
				System.out.print(j);
			}

			System.out.println();
		}
	}
}

class Third
{
	public static void patternBinary()
	{
		System.out.println("\n[Third]");

		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				switch (i * j % 2)
				{
					case 0:
						System.out.print((i+1) * (j+1) % 2);
						break;
					case 1:
						System.out.print(i * j % 2);
						break;
				}
			}

			System.out.println();
		}
	}
}

class Fourth
{
	public static void theAnswerIs42()
	{
		System.out.print("\n[Fourth]\nEnter a list of numbers: ");

		List<Integer> list = new ArrayList<Integer>();
		Scanner scan = new Scanner(System.in);

		while (!scan.hasNext("42"))
		{
			list.add(scan.nextInt());
		}

		System.out.println(list);
	}
}

class Fifth
{
	public static void freeCalculator()
	{
		System.out.print("\n[Fifth]\n Enter a binary operation: ");

		Scanner scan = new Scanner(System.in);
		double a = 0, b = 0, c = Double.NaN;
		char o = '\u0000';

		if (scan.hasNextDouble())
		{
			a = scan.nextDouble();
		}

		if (scan.hasNext("[+-/%^*]"))
		{
			o = scan.next().charAt(0);
		}

		if (scan.hasNextDouble())
		{
			b = scan.nextDouble();
		}

		switch (o)
		{
			case '+': c = a + b; break;
			case '-': c = a - b; break;
			case '*': c = a * b; break;
			case '/': c = a / b; break;
			case '%': c = a % b; break;
			case '^': c = Math.pow(a, b); break;
			default: System.out.println("Invalid operator (or) spaces skipped.");
		}

		if (!Double.isNaN(c))
		{
			System.out.println(a + " " + o + " " + b + " = " + c);
		}
	}
}

public class ShivamMukherjee_Module1
{
	public static void main(String args[])
	{
		First.dysfunctionalIDE();
		Second.pattern12345();
		Third.patternBinary();
		Fourth.theAnswerIs42();
		Fifth.freeCalculator();
	}
}