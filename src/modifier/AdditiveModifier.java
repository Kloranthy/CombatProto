package modifier;

/**
 */
public class AdditiveModifier
{
	private String name;
	private double value;

	public AdditiveModifier()
	{}

	public String getName()
	{
		return name;
	}

	public AdditiveModifier setName( String name )
	{
		this.name = name;
		return this;
	}

	public double getValue()
	{
		return value;
	}

	public AdditiveModifier setValue( double value )
	{
		this.value = value;
		return this;
	}

	public double applyTo( double score )
	{
		return score + value;
	}
}
