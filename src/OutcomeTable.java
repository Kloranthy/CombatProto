import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 */
public class OutcomeTable
{
	private List<Outcome> outcomes;

	public OutcomeTable()
	{
		outcomes = new LinkedList<Outcome>();
	}

	public void addOutcome( double value )
	{
		if ( hasOutcome( value ) )
		{
			Outcome outcome = getOutcomeWithValue( value );
			outcome.setCount( outcome.getCount() + 1 );
		}
		else
		{
			Outcome outcome = new Outcome();
			outcome.setValue( value )
					 .setCount( 1 );
			outcomes.add( outcome );
		}
	}

	public boolean hasOutcome( double value )
	{
		if ( outcomes.isEmpty() )
		{
			return false;
		}
		for ( Outcome outcome : outcomes )
		{
			if ( outcome.getValue() == value )
			{
				return true;
			}
		}
		return false;
	}

	private Outcome getOutcomeWithValue( double value )
	{
		for ( Outcome outcome : outcomes )
		{
			if ( outcome.getValue() == value )
			{
				return outcome;
			}
		}
		return null;
	}

	public void calculateProbabilities()
	{
		int totalCount = 0;
		for ( Outcome outcome : outcomes )
		{
			totalCount += outcome.getCount();
		}
		//print( "total count: " + totalCount );
		for ( Outcome outcome : outcomes )
		{
			double outcomeProbability = (double) outcome.getCount() / totalCount;
			//print( "p: " + outcomeProbability );
			outcome.setProbability( outcomeProbability );
		}
		//printOutcomes();
		//print( "expected outcome: " + getExpectedOutcome() );
	}

	public double getProbabilityOfRollGreaterThan( double roll )
	{
		double probability = 0.00;
		for ( Outcome outcome : outcomes )
		{
			if ( outcome.getValue() > roll )
			{
				probability += outcome.getProbability();
			}
		}
		return probability;
	}

	public double getProbabilityOfRollGreaterThanOrEqualTo( double roll )
	{
		double probability = 0.00;
		for ( Outcome outcome : outcomes )
		{
			if ( outcome.getValue() >= roll )
			{
				probability += outcome.getProbability();
			}
		}
		return probability;
	}

	public double getExpectedOutcome()
	{
		double expected = 0;
		for ( Outcome outcome : outcomes )
		{
			expected += outcome.getValue() * outcome.getProbability();
		}
		return expected;
	}

	public double getLowestOutcome()
	{
		sortByValue();
		return outcomes.get( 0 )
							.getValue();
	}

	private void sortByValue()
	{
		outcomes.sort( (Comparator.comparingDouble( Outcome::getValue )) );
	}

	public double getHighestOutcome()
	{
		sortByValue();
		return outcomes.get( outcomes.size() - 1 )
							.getValue();
	}

	private void sortByProbability()
	{
		outcomes.sort( (Comparator.comparingInt( Outcome::getCount )) );
	}
		  /*
		private void printOutcomes()
		{
			sortByValue();
			for ( Outcome outcome : outcomes )
			{
				print(
					"value: " + outcome.getValue()
					+ " count: " + outcome.getCount()
					+ " probability: " + outcome.getProbability()
				     );
			}
		}
		*/
}
