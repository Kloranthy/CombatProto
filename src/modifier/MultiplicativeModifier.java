package modifier;

/**
 */
public class MultiplicativeModifier
{
	private String name;
	private double value;

	public MultiplicativeModifier()
	{}

	public String getName()
	{
		return name;
	}

	public MultiplicativeModifier setName( String name )
	{
		this.name = name;
		return this;
	}

	public double getValue()
	{
		return value;
	}

	public MultiplicativeModifier setValue( double value )
	{
		this.value = value;
		return this;
	}

	public double applyTo( double score )
	{
		return score * value;
	}
}
