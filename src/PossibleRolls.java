import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * todo add a way of calculating possible rolls for 2 training levels that isn't an abomination
 */
public class PossibleRolls
{
	private EnumMap<TrainingLevel, List<PossibleRoll>> possibleRollsForTrainingLevel;
	private EnumMap<TrainingLevel, EnumMap<TrainingLevel, List<PossibleRoll>>> possibleRollsForTwoTrainingLevels;

	public static PossibleRolls getInstance()
	{
		if ( instance == null )
		{
			instance = new PossibleRolls();
			instance.calculatePossibleRolls();
		}
		return instance;
	}

	private static PossibleRolls instance;

	private PossibleRolls()
	{
		possibleRollsForTrainingLevel = new EnumMap<TrainingLevel, List<PossibleRoll>>( TrainingLevel.class );
		possibleRollsForTwoTrainingLevels = new EnumMap<TrainingLevel, EnumMap<TrainingLevel, List<PossibleRoll>>>( TrainingLevel.class );
	}

	public static void main( String[] args )
	{
		Scanner scanner = new Scanner( System.in );
		PossibleRolls possibleRolls = new PossibleRolls();
		possibleRolls.calculatePossibleRolls();
		System.out.println( "possible rolls calculated" );
		System.out.println( "" );
		System.out.println( "press enter to continue" );
		scanner.nextLine();
		System.out.println( "" );
		System.out.println( "expected rolls for each training level" );
		for ( TrainingLevel trainingLevel : TrainingLevel.values() )
		{
			System.out.println( "expected roll for training level " + trainingLevel.name + " is " +
									  possibleRolls.getExpectedRoll( trainingLevel ) );
		}
		System.out.println( "" );
		System.out.println( "press enter to continue" );
		scanner.nextLine();
		System.out.println( "" );
		System.out.println( "expected rolls for each combination of training levels" );
		for ( TrainingLevel trainingLevel1 : TrainingLevel.values() )
		{
			for ( TrainingLevel trainingLevel2 : TrainingLevel.values() )
			{
				System.out.println( "expected roll for training level 1 (" + trainingLevel1.name +
										  ") and training level 2 (" + trainingLevel2.name + ") is " +
										  possibleRolls.getExpectedRoll(
											  trainingLevel1,
											  trainingLevel2
																				 ) );
			}
		}
		System.out.println( "" );
		System.out.println( "press enter to continue" );
		scanner.nextLine();
		System.out.println( "" );
		System.out.println( "chance of each training level rolling a value greater than " +
								  "the expected roll of each training level" );
		for ( TrainingLevel trainingLevel1 : TrainingLevel.values() )
		{
			for ( TrainingLevel trainingLevel2 : TrainingLevel.values() )
			{
				double expectedRollForTrainingLevel2 = possibleRolls.getExpectedRoll( trainingLevel2 );
				double probabilityOfRollGreaterThan = possibleRolls.getProbabilityOfRollGreaterThan(
					expectedRollForTrainingLevel2,
					trainingLevel1
																															  );
				probabilityOfRollGreaterThan = Utilities.convertToPercentileFormat( probabilityOfRollGreaterThan );
				System.out.println( "training level 1 (" + trainingLevel1.name + ") has a " +
										  probabilityOfRollGreaterThan + "% chance of rolling greater than " +
										  "the expected value (" + expectedRollForTrainingLevel2 + ") of " +
										  "training level 2 (" + trainingLevel2.name + ")" );
			}
		}
		System.out.println( "" );
		System.out.println( "press enter to continue" );
		scanner.nextLine();
		System.out.println( "" );
		System.out.println( "chance of each training level rolling a value greater than " +
								  "or equal to the expected roll of each training level" );
		for ( TrainingLevel trainingLevel1 : TrainingLevel.values() )
		{
			for ( TrainingLevel trainingLevel2 : TrainingLevel.values() )
			{
				double expectedRollForTrainingLevel2 = possibleRolls.getExpectedRoll( trainingLevel2 );
				double probabilityOfRollGreaterThanOrEqualTo = possibleRolls.getProbabilityOfRollGreaterThanOrEqualTo(
					expectedRollForTrainingLevel2,
					trainingLevel1
																																					  );
				probabilityOfRollGreaterThanOrEqualTo = Utilities.convertToPercentileFormat( probabilityOfRollGreaterThanOrEqualTo );
				System.out.println( "training level 1 (" + trainingLevel1.name + ") has a " +
										  probabilityOfRollGreaterThanOrEqualTo +
										  "% chance of rolling greater than " +
										  "or equal to the expected value (" + expectedRollForTrainingLevel2 +
										  ") of " + "training level 2 (" + trainingLevel2.name + ")" );
			}
		}
	}

	public double getExpectedRoll(
		TrainingLevel trainingLevel
										  )
	{
		List<PossibleRoll> possibleRolls = getPossibleRollsFor( trainingLevel );
		return getExpectedRoll( possibleRolls );
	}

	public double getExpectedRoll(
		TrainingLevel trainingLevel1,
		TrainingLevel trainingLevel2
										  )
	{
		List<PossibleRoll> possibleRolls = getPossibleRollsFor(
			trainingLevel1,
			trainingLevel2
																				);
		return getExpectedRoll( possibleRolls );
	}

	public double getLowestRoll( List<PossibleRoll> possibleRolls )
	{
		sortByValue( possibleRolls );
		return possibleRolls.get( 0 )
								  .getValue();
	}

	public double getHighestRoll( List<PossibleRoll> possibleRolls )
	{
		sortByValue( possibleRolls );
		return possibleRolls.get( possibleRolls.size() - 1 )
								  .getValue();
	}

	public double getProbabilityOfRollGreaterThan(
		double roll,
		TrainingLevel trainingLevel
																)
	{
		List<PossibleRoll> possibleRolls = getPossibleRollsFor( trainingLevel );
		return getProbabilityOfRollGreaterThan(
			roll,
			possibleRolls
														  );
	}

	public double getProbabilityOfRollGreaterThan(
		double roll,
		TrainingLevel trainingLevel1,
		TrainingLevel trainingLevel2
																)
	{
		List<PossibleRoll> possibleRolls = getPossibleRollsFor(
			trainingLevel1,
			trainingLevel2
																				);
		return getProbabilityOfRollGreaterThan(
			roll,
			possibleRolls
														  );
	}

	public double getProbabilityOfRollGreaterThan(
		double roll,
		List<PossibleRoll> possibleRolls
																)
	{
		double probability = 0.00;
		for ( PossibleRoll possibleRoll : possibleRolls )
		{
			if ( possibleRoll.getValue() > roll )
			{
				probability += possibleRoll.getProbability();
			}
		}
		return probability;
	}

	public double getProbabilityOfRollGreaterThanOrEqualTo(
		double roll,
		TrainingLevel trainingLevel
																			)
	{
		List<PossibleRoll> possibleRolls = getPossibleRollsFor( trainingLevel );
		return getProbabilityOfRollGreaterThanOrEqualTo(
			roll,
			possibleRolls
																	  );
	}

	public double getProbabilityOfRollGreaterThanOrEqualTo(
		double roll,
		TrainingLevel trainingLevel1,
		TrainingLevel trainingLevel2
																			)
	{
		List<PossibleRoll> possibleRolls = getPossibleRollsFor(
			trainingLevel1,
			trainingLevel2
																				);
		return getProbabilityOfRollGreaterThanOrEqualTo(
			roll,
			possibleRolls
																	  );
	}

	public double getProbabilityOfRollGreaterThanOrEqualTo(
		double roll,
		List<PossibleRoll> possibleRolls
																			)
	{
		double probability = 0.00;
		for ( PossibleRoll possibleRoll : possibleRolls )
		{
			if ( possibleRoll.getValue() >= roll )
			{
				probability += possibleRoll.getProbability();
			}
		}
		return probability;
	}

	public double getProbabilityOfRollLessThan(
		double roll,
		TrainingLevel trainingLevel
															)
	{
		List<PossibleRoll> possibleRolls = getPossibleRollsFor( trainingLevel );
		return getProbabilityOfRollLessThan(
			roll,
			possibleRolls
													  );
	}

	public double getProbabilityOfRollLessThan(
		double roll,
		TrainingLevel trainingLevel1,
		TrainingLevel trainingLevel2
															)
	{
		List<PossibleRoll> possibleRolls = getPossibleRollsFor(
			trainingLevel1,
			trainingLevel2
																				);
		return getProbabilityOfRollLessThan(
			roll,
			possibleRolls
													  );
	}

	public double getProbabilityOfRollLessThan(
		double roll,
		List<PossibleRoll> possibleRolls
															)
	{
		double probability = 0.00;
		for ( PossibleRoll possibleRoll : possibleRolls )
		{
			if ( possibleRoll.getValue() < roll )
			{
				probability += possibleRoll.getProbability();
			}
		}
		return probability;
	}

	public double getProbabilityOfRollLessThanOrEqualTo(
		double roll,
		TrainingLevel trainingLevel
																		)
	{
		List<PossibleRoll> possibleRolls = getPossibleRollsFor( trainingLevel );
		return getProbabilityOfRollLessThanOrEqualTo(
			roll,
			possibleRolls
																  );
	}

	public double getProbabilityOfRollLessThanOrEqualTo(
		double roll,
		TrainingLevel trainingLevel1,
		TrainingLevel trainingLevel2
																		)
	{
		List<PossibleRoll> possibleRolls = getPossibleRollsFor(
			trainingLevel1,
			trainingLevel2
																				);
		return getProbabilityOfRollLessThanOrEqualTo(
			roll,
			possibleRolls
																  );
	}

	public double getProbabilityOfRollLessThanOrEqualTo(
		double roll,
		List<PossibleRoll> possibleRolls
																		)
	{
		double probability = 0.00;
		for ( PossibleRoll possibleRoll : possibleRolls )
		{
			if ( possibleRoll.getValue() <= roll )
			{
				probability += possibleRoll.getProbability();
			}
		}
		return probability;
	}

	private void calculatePossibleRolls()
	{
		System.out.println( "calculating possible rolls for single training level" );
		// calculatePossibleRolls results for single training level
		for ( TrainingLevel trainingLevel : TrainingLevel.values() )
		{
			List<PossibleRoll> possibleRolls = calculatePossibleRollsFor( trainingLevel );
			possibleRollsForTrainingLevel.put(
				trainingLevel,
				possibleRolls
														);
		}
		System.out.println( "calculating possible rolls for combinations of 2 training levels" );
		// calculatePossibleRolls the results for combinations of 2 training levels
		for ( TrainingLevel trainingLevel1 : TrainingLevel.values() )
		{
			for ( TrainingLevel trainingLevel2 : TrainingLevel.values() )
			{
				// check if the enum map for training level 1 exists
				EnumMap<TrainingLevel, List<PossibleRoll>> trainingLevel1EnumMap = possibleRollsForTwoTrainingLevels.get( trainingLevel1 );
				if ( trainingLevel1EnumMap == null )
				{
					System.out.println(
						"training level 1 (" + trainingLevel1.name + ") had no enum map" );
					trainingLevel1EnumMap = new EnumMap<TrainingLevel, List<PossibleRoll>>( TrainingLevel.class );
					possibleRollsForTwoTrainingLevels.put(
						trainingLevel1,
						trainingLevel1EnumMap
																	 );
				}
				// check if the enum map for training level 2 exists
				EnumMap<TrainingLevel, List<PossibleRoll>> trainingLevel2EnumMap = possibleRollsForTwoTrainingLevels.get( trainingLevel2 );
				if ( trainingLevel2EnumMap == null )
				{
					System.out.println(
						"training level 2 (" + trainingLevel2.name + ") had no enum map" );
					trainingLevel2EnumMap = new EnumMap<TrainingLevel, List<PossibleRoll>>( TrainingLevel.class );
					possibleRollsForTwoTrainingLevels.put(
						trainingLevel2,
						trainingLevel2EnumMap
																	 );
				}
				// check if neither enum map has an entry for the other training level
				if ( trainingLevel1EnumMap.get( trainingLevel2 ) == null &&
					  trainingLevel2EnumMap.get( trainingLevel1 ) == null )
				{
					System.out.println(
						"both training level 1 (" + trainingLevel1.name + ") and training level 2 (" +
						trainingLevel2.name + ") needed entries for each other" );
					// calculatePossibleRolls the possible rolls for the combination
					List<PossibleRoll> possibleRolls = calculatePossibleRollsFor(
						trainingLevel1,
						trainingLevel2
																									);
					// add references to the possible rolls to both training levels enum maps
					// with both training levels using the other training level as the key
					trainingLevel1EnumMap.put(
						trainingLevel2,
						possibleRolls
													 );
					trainingLevel2EnumMap.put(
						trainingLevel1,
						possibleRolls
													 );
				}
				// only training level 1 needs a reference for the possible rolls
				else if ( trainingLevel1EnumMap.get( trainingLevel2 ) == null )
				{
					System.out.println( "training level 1 (" + trainingLevel1.name +
											  ") needed an entry for training level 2 (" +
											  trainingLevel2.name + ")" );
					List<PossibleRoll> possibleRolls = possibleRollsForTwoTrainingLevels.get( trainingLevel2 )
																											  .get( trainingLevel1 );
					trainingLevel1EnumMap.put(
						trainingLevel2,
						possibleRolls
													 );
				}
				// only training level 2 needs a reference for the possible rolls
				else if ( trainingLevel2EnumMap.get( trainingLevel1 ) == null )
				{
					System.out.println( "training level 2 (" + trainingLevel2.name +
											  ") needed an entry for training level 1 (" +
											  trainingLevel1.name + ")" );
					List<PossibleRoll> possibleRolls = possibleRollsForTwoTrainingLevels.get( trainingLevel1 )
																											  .get( trainingLevel2 );
					trainingLevel2EnumMap.put(
						trainingLevel1,
						possibleRolls
													 );
				}
				else
				{
					System.out.println(
						"neither training level 1 (" + trainingLevel1.name + ") nor training level 2 (" +
						trainingLevel2.name + ") needed an entry" );
				}
			}
		}
	}

	private List<PossibleRoll> calculatePossibleRollsFor( TrainingLevel trainingLevel )
	{
		System.out.println( "calculating possible rolls for training level " + trainingLevel.name );
		List<Dice> diceUsed = trainingLevel.getDiceUsed();
		return calculatePossibleRollsFor( diceUsed );
	}

	private List<PossibleRoll> calculatePossibleRollsFor(
		TrainingLevel trainingLevel1,
		TrainingLevel trainingLevel2
																		 )
	{
		List<Dice> diceUsed = new LinkedList<Dice>();
		diceUsed.addAll( trainingLevel1.getDiceUsed() );
		diceUsed.addAll( trainingLevel2.getDiceUsed() );
		return calculatePossibleRollsFor( diceUsed );
	}

	private List<PossibleRoll> calculatePossibleRollsFor( List<Dice> diceUsed )
	{
		List<PossibleRoll> possibleRolls = new LinkedList<PossibleRoll>();
		// create and initialize an array to hold results
		int[] results = new int[ 1 ];
		results[ 0 ] = 0;
		// for each dice used
		for (
			int diceIndex = 0;
			diceIndex < diceUsed.size();
			diceIndex++
			)
		{
			Dice dice = diceUsed.get( diceIndex );
			// create a new array to hold the new results
			// since we are still referencing the existing results
			int[] nextResults = new int[ dice.sides * results.length ];
			// for each existing result
			for (
				int resultsIndex = 0;
				resultsIndex < results.length;
				resultsIndex++
				)
			{
				// for each side of the current dice
				for (
					int sideIndex = 0;
					sideIndex < dice.sides;
					sideIndex++
					)
				{
					int sideValue = sideIndex + 1;
					// copy the old results into the new results
					// while adding the current side's value
					int nextResultsIndex = sideIndex + resultsIndex * dice.sides;
					nextResults[ nextResultsIndex ] = results[ resultsIndex ] + sideValue;
				}
			}
			// the new results are now the existing results
			results = nextResults;
		}
		for ( int result : results )
		{
			// add the result to the possible rolls
			addPossibleRoll(
				result,
				possibleRolls
								);
		}
		calculateProbabilities( possibleRolls );
		return possibleRolls;
	}

	private void addPossibleRoll(
		double value,
		List<PossibleRoll> possibleRolls
										 )
	{
		// check if the value is already present in the possible rolls
		if ( hasPossibleRollWithValue(
			value,
			possibleRolls
											  ) )
		{
			// get the entry with that value
			PossibleRoll possibleRoll = getPossibleRollWithValue(
				value,
				possibleRolls
																				 );
			// increment the count of that entry
			possibleRoll.setCount( possibleRoll.getCount() + 1 );
		}
		else
		{
			// create a new entry for the value
			PossibleRoll possibleRoll = new PossibleRoll();
			possibleRoll.setValue( value )
							.setCount( 1 );
			// add the entry to the possible rolls
			possibleRolls.add( possibleRoll );
		}
	}

	private boolean hasPossibleRollWithValue(
		double value,
		List<PossibleRoll> possibleRolls
														 )
	{
		// if there are no entries, the value is not present
		if ( possibleRolls.isEmpty() )
		{
			return false;
		}
		for ( PossibleRoll possibleRoll : possibleRolls )
		{
			if ( possibleRoll.getValue() == value )
			{
				return true;
			}
		}
		return false;
	}

	private PossibleRoll getPossibleRollWithValue(
		double value,
		List<PossibleRoll> possibleRolls
																)
	{
		for ( PossibleRoll possibleRoll : possibleRolls )
		{
			if ( possibleRoll.getValue() == value )
			{
				return possibleRoll;
			}
		}
		// shouldn't happen since we check if the entry exists first
		return null;
	}

	private void calculateProbabilities( List<PossibleRoll> possibleRolls )
	{
		int totalCount = 0;
		for ( PossibleRoll possibleRoll : possibleRolls )
		{
			// to get the total number of possible rolls
			// we have to add up the number of times each
			// possible roll occurred
			totalCount += possibleRoll.getCount();
		}
		for ( PossibleRoll possibleRoll : possibleRolls )
		{
			// the probability of a possible roll occurring
			// is equal to the number of times it occurred
			// divided by the total number of possible rolls
			double outcomeProbability = (double) possibleRoll.getCount() / totalCount;
			possibleRoll.setProbability( outcomeProbability );
		}
	}

	private void sortByValue( List<PossibleRoll> possibleRolls )
	{
		possibleRolls.sort( (Comparator.comparingDouble( PossibleRoll::getValue )) );
	}

	private void sortByProbability( List<PossibleRoll> possibleRolls )
	{
		// count is a proxy for probability
		possibleRolls.sort( (Comparator.comparingInt( PossibleRoll::getCount )) );
	}

	private List<PossibleRoll> getPossibleRollsFor( TrainingLevel trainingLevel )
	{
		return possibleRollsForTrainingLevel.get( trainingLevel );
	}

	private List<PossibleRoll> getPossibleRollsFor(
		TrainingLevel trainingLevel1,
		TrainingLevel trainingLevel2
																 )
	{
		return possibleRollsForTwoTrainingLevels.get( trainingLevel1 )
															 .get( trainingLevel2 );
	}

	private double getExpectedRoll(
		List<PossibleRoll> possibleRolls
											)
	{
		double expected = 0;
		for ( PossibleRoll possibleRoll : possibleRolls )
		{
			expected += possibleRoll.getValue() * possibleRoll.getProbability();
		}
		expected = Utilities.round(
			expected,
			2
										  );
		return expected;
	}
}
