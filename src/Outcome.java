/**
 */
public class Outcome
{
	private double value;
	private int count;
	private double probability;

	public Outcome()
	{
	}

	public double getValue()
	{
		return value;
	}

	public Outcome setValue( double value )
	{
		this.value = value;
		return this;
	}

	public int getCount()
	{
		return count;
	}

	public Outcome setCount( int count )
	{
		this.count = count;
		return this;
	}

	public double getProbability()
	{
		return probability;
	}

	public Outcome setProbability( double probability )
	{
		this.probability = probability;
		return this;
	}
}
