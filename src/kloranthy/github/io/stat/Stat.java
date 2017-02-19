package kloranthy.github.io.stat;

import java.util.UUID;

/**
 * possible solution
 * although a one size fits all stat model might not
 * work for all cases
 * ex: health doesn't take in rolls > doesn't need variable mods
 * ex: range has to return values for hit score and damage based on
 * the range that is passed in
 */
public class Stat
{
	private UUID statId;
	private String statName;
	private String statDescription;
	private double baseValue;
	private double additiveModifier;
	private double multiplicativeModifier;

	public Stat()
	{
		initModifierValues();
	}

	private void initModifierValues()
	{
		additiveModifier = 0;
		multiplicativeModifier = 1;
	}

	public UUID getStatId()
	{
		return statId;
	}

	public Stat setStatId( UUID statId )
	{
		this.statId = statId;
		return this;
	}

	public String getStatName()
	{
		return statName;
	}

	public Stat setStatName( String statName )
	{
		this.statName = statName;
		return this;
	}

	public String getStatDescription()
	{
		return statDescription;
	}

	public Stat setStatDescription( String statDescription )
	{
		this.statDescription = statDescription;
		return this;
	}

	public double getBaseValue()
	{
		return baseValue;
	}

	public Stat setBaseValue( double baseValue )
	{
		this.baseValue = baseValue;
		return this;
	}

	public double getAdditiveModifier()
	{
		return additiveModifier;
	}

	public Stat setAdditiveModifier( double additiveModifier )
	{
		this.additiveModifier = additiveModifier;
		return this;
	}

	public double getMultiplicativeModifier()
	{
		return multiplicativeModifier;
	}

	public Stat setMultiplicativeModifier( double multiplicativeModifier )
	{
		this.multiplicativeModifier = multiplicativeModifier;
		return this;
	}
}
