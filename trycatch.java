class Exc69ption extends Exception
{
	@Override
	public String toString()
	{
		return ("wow lol it's an Exc69ption.");
	}
}

class LolItsAlwaysLong
{
	static void pls() throws Exc69ption
	{
		int x = 23;
		if (x+x+x == 69)
		{
			throw new Exc69ption();
		}
	}

	public static void main(String[] args)
	{
		int[] array = new int[5];
		
		try
		{
			pls();
		}
		catch (Exc69ption e)
		{
			System.out.println("Oh noez. It's a \n" + e);
		}
		catch (Exception l)
		{
			System.out.println("Oh noez again. It's a \n" + l);
		}
		finally
		{
			System.out.println("gg bruv, I executed safely.");
		}
	}
}