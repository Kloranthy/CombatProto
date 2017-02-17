package kloranthy.github.io;

import java.util.Random;

/**
 */
public class Dice
{
	public final int sides;
	private Random random;

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
