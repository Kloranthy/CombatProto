import java.util.Random;

/**
 */
public class Dice
{
	private Random random;

	public Dice()
	{
		random = new Random();
	}

	public int roll(
		int numberOfDice,
		int numberOfSides
						)
	{
		int roll = 0;
		for (
			int i = 0;
			i < numberOfDice;
			i++
			)
		{
			roll += random.nextInt( numberOfSides ) + 1;
		}
		return roll;
	}

	public int rollFor(TrainingLevel trainingLevel)
	{
		switch ( trainingLevel )
		{
			case NONE:
				return roll(
					1,
					8
											 );
			case BASIC:
				return roll(
					2,
					6
											 );
			case INTERMEDIATE:
				return roll(
					3,
					5
											 );
			case ADVANCED:
				return roll(
					4,
					4
											 );
			default:
				return 0;
		}
	}
}
