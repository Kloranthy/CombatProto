package kloranthy.github.io.stat;

/**
 */
public
class ScalingStat
{
	private String statName;
	private String statDescription;
	private double baseValue;
	private double additiveModifierForFixedValue;
	private double multiplicativeModifierForFixedValue;
	private double additiveModifierForVariableValue;
	private double multiplicativeModifierForVariableValue;
	private double additiveModifierForTotalValue;
	private double multiplicativeModifierForTotalValue;

	public
	ScalingStat()
	{
		initModifierValues();
	}

	private
	void initModifierValues()
	{
		additiveModifierForFixedValue = 0;
		multiplicativeModifierForFixedValue = 1;
		additiveModifierForVariableValue = 0;
		multiplicativeModifierForVariableValue = 1;
		additiveModifierForTotalValue = 0;
		multiplicativeModifierForTotalValue = 1;
	}

	public
	String getStatName()
	{
		return statName;
	}

	public
	ScalingStat setStatName( String statName )
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
	ScalingStat setStatDescription( String statDescription )
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
	ScalingStat setBaseValue( double baseValue )
	{
		this.baseValue = baseValue;
		return this;
	}

	public
	ScalingStat modifyBaseValue( double amount )
	{
		baseValue += amount;
		return this;
	}

	public
	double getAdditiveModifierForFixedValue()
	{
		return additiveModifierForFixedValue;
	}

	public
	ScalingStat setAdditiveModifierForFixedValue( double additiveModifierForFixedValue )
	{
		this.additiveModifierForFixedValue = additiveModifierForFixedValue;
		return this;
	}

	public
	ScalingStat modifyAdditiveModifierForFixedValue( double amount )
	{
		additiveModifierForFixedValue += amount;
		return this;
	}

	public
	double getMultiplicativeModifierForFixedValue()
	{
		return multiplicativeModifierForFixedValue;
	}

	public
	ScalingStat setMultiplicativeModifierForFixedValue( double multiplicativeModifierForFixedValue )
	{
		this.multiplicativeModifierForFixedValue = multiplicativeModifierForFixedValue;
		return this;
	}

	public
	ScalingStat modifyMultiplicativeModifierForFixedValue( double amount )
	{
		multiplicativeModifierForFixedValue += amount;
		return this;
	}

	public
	double getAdditiveModifierForVariableValue()
	{
		return additiveModifierForVariableValue;
	}

	public
	ScalingStat setAdditiveModifierForVariableValue( double additiveModifierForVariableValue )
	{
		this.additiveModifierForVariableValue = additiveModifierForVariableValue;
		return this;
	}

	public
	ScalingStat modifyAdditiveModifierForVariableValue( double amount )
	{
		additiveModifierForVariableValue += amount;
		return this;
	}

	public
	double getMultiplicativeModifierForVariableValue()
	{
		return multiplicativeModifierForVariableValue;
	}

	public
	ScalingStat setMultiplicativeModifierForVariableValue( double multiplicativeModifierForVariableValue )
	{
		this.multiplicativeModifierForVariableValue = multiplicativeModifierForVariableValue;
		return this;
	}

	public
	ScalingStat modifyMultiplicativeModifierForVariableValue( double amount )
	{
		multiplicativeModifierForVariableValue += amount;
		return this;
	}

	public
	double getAdditiveModifierForTotalValue()
	{
		return additiveModifierForTotalValue;
	}

	public
	ScalingStat setAdditiveModifierForTotalValue( double additiveModifierForTotalValue )
	{
		this.additiveModifierForTotalValue = additiveModifierForTotalValue;
		return this;
	}

	public
	ScalingStat modifyAdditiveModifierForTotalValue( double amount )
	{
		additiveModifierForTotalValue += amount;
		return this;
	}

	public
	double getMultiplicativeModifierForTotalValue()
	{
		return multiplicativeModifierForTotalValue;
	}

	public
	ScalingStat setMultiplicativeModifierForTotalValue( double multiplicativeModifierForTotalValue )
	{
		this.multiplicativeModifierForTotalValue = multiplicativeModifierForTotalValue;
		return this;
	}

	public
	ScalingStat modifyMultiplicativeModifierForTotalValue( double amount )
	{
		multiplicativeModifierForTotalValue += amount;
		return this;
	}
}
