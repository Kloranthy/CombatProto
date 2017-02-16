import java.util.ArrayList;
import java.util.List;

/**
 */
public class DiceTest
{
	List<Dice> diceUsed;
	int[] results;

	public static void main( String[] args )
	{
		DiceTest diceTest = new DiceTest();
		diceTest.addDice( new Dice().setSides( 4 ) )
				  .addDice( new Dice().setSides( 4 ) )
				  .addDice( new Dice().setSides( 4 ) )
				  .addDice( new Dice().setSides( 4 ) );
		diceTest.calc();
	}

	public DiceTest()
	{
		diceUsed = new ArrayList<Dice>();
	}

	public DiceTest addDice( Dice dice )
	{
		diceUsed.add( dice );
		return this;
	}

	public void calc()
	{
		// create an array to hold results
		results = new int[1];
		results[0] = 0;
		for ( int diceIndex = 0; diceIndex < diceUsed.size(); diceIndex++ )
		{
			Dice dice = diceUsed.get( diceIndex );
			System.out.println( "dice index: " + diceIndex );
			// create a new array to avoid modifying the existing results
			int[] nextResults = new int[dice.sides * results.length];
			//System.out.println( "next results length: " + nextResults.length );
			// for each existing result
			for ( int resultsIndex = 0; resultsIndex < results.length; resultsIndex++ )
			{
				//System.out.println( "results index: " + resultsIndex );
				// for each side of the current dice
				for ( int sideIndex = 0; sideIndex < dice.sides; sideIndex++ )
				{
					//System.out.println( "side " + sideIndex + " of dice " + diceIndex );
					// side index goes from 0 to 5, side values go from 1 to 6
					int sideValue = sideIndex + 1;
					// copy the old results into the new results while adding the current side's value
					int nextResultsIndex = sideIndex + resultsIndex * dice.sides ;
					//System.out.println( "next results index: " + nextResultsIndex );
					//System.out.println( "adding " + sideValue + " to " + results[resultsIndex] );
					nextResults[nextResultsIndex] = results[resultsIndex] + sideValue;
				}
			}
			// the new results are now the existing results
			results = nextResults;
		}
		for ( int resultsIndex = 0; resultsIndex < results.length; resultsIndex++ )
		{
			System.out.println( "result " + resultsIndex + " is " + results[resultsIndex] );
		}
		// todo: combine results and calculatePossibleRolls probabilities
	}

	private static class Dice
	{
		int sides;

		public Dice()
		{
		}

		public Dice setSides( int sides )
		{
			this.sides = sides;
			return this;
		}
	}
}
