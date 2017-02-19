package kloranthy.github.io.stat;

import java.util.UUID;

/**
 */
public
class Stat
{
	private UUID statId;
	private String statName;
	private String statDescription;
	private double baseValue;
	private double additiveModifier;
	private double multiplicativeModifier;

	public
	Stat()
	{
		initModifierValues();
	}

	private
	void initModifierValues()
	{
		additiveModifier = 0;
		multiplicativeModifier = 1;
	}

	public
	UUID getStatId()
	{
		return statId;
	}

	public
	Stat setStatId( UUID statId )
	{
		this.statId = statId;
		return this;
	}

	public
	String getStatName()
	{
		return statName;
	}

	public
	Stat setStatName( String statName )
	{
		this.statName = statName;
		return this;
	}

	public
	String getStatDescription()
	{
		return statDescription;
	}

	public
	Stat setStatDescription( String statDescription )
	{
		this.statDescription = statDescription;
		return this;
	}

	public
	double getBaseValue()
	{
		return baseValue;
	}

	public
	Stat setBaseValue( double baseValue )
	{
		this.baseValue = baseValue;
		return this;
	}

	public
	Stat modifyBaseValue( double amount )
	{
		baseValue += amount;
		return this;
	}

	public
	double getAdditiveModifier()
	{
		return additiveModifier;
	}

	public
	Stat setAdditiveModifier( double additiveModifier )
	{
		this.additiveModifier = additiveModifier;
		return this;
	}

	public Stat modifyAdditiveModifier( double amount )
	{
		additiveModifier += amount;
		return this;
	}

	public
	double getMultiplicativeModifier()
	{
		return multiplicativeModifier;
	}

	public
	Stat setMultiplicativeModifier( double multiplicativeModifier )
	{
		this.multiplicativeModifier = multiplicativeModifier;
		return this;
	}

	public Stat modifyMultiplicativeModifier( double amount )
	{
		multiplicativeModifier += amount;
		return this;
	}
}
