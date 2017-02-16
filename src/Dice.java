import java.util.Random;

/**
 */
public class Dice
{
	private Random random;
	public final int sides;

	public Dice( int sides )
	{
		this.sides = sides;
		random = new Random();
	}

	public int roll()
	{
		return random.nextInt( sides ) + 1;
	}
}
