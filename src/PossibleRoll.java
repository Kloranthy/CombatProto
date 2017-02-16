/**
 */
public class PossibleRoll
{
	private double value;
	private int count;
	private double probability;

	public PossibleRoll()
	{
		value = 0;
		count = 0;
		probability = 0;
	}

	public double getValue()
	{
		return value;
	}

	public PossibleRoll setValue( double value )
	{
		this.value = value;
		return this;
	}

	public int getCount()
	{
		return count;
	}

	public PossibleRoll setCount( int count )
	{
		this.count = count;
		return this;
	}

	public double getProbability()
	{
		return probability;
	}

	public PossibleRoll setProbability( double probability )
	{
		this.probability = probability;
		return this;
	}
}
