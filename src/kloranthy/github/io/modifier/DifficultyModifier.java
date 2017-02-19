package kloranthy.github.io.modifier;

import java.util.UUID;

import kloranthy.github.io.stat.ScalingStat;

/**
 */
public
class DifficultyModifier
	extends Modifier<ScalingStat>
{
	private double additiveModifierForFixedDifficulty;
	private double multiplicativeModifierForFixedDifficulty;
	private double additiveModifierForVariableDifficulty;
	private double multiplicativeModifierForVariableDifficulty;
	private double additiveModifierForTotalDifficulty;
	private double multiplicativeModifierForTotalDifficulty;

	public
	DifficultyModifier()
	{
		initModifierValues();
	}

	@Override
	public
	DifficultyModifier setModifierId( UUID modifierId )
	{
		super.setModifierId( modifierId );
		return this;
	}

	@Override
	public
	void applyEffectsTo( ScalingStat difficulty )
	{
		difficulty
			.modifyAdditiveModifierForFixedValue( additiveModifierForFixedDifficulty )
			.modifyMultiplicativeModifierForFixedValue( multiplicativeModifierForFixedDifficulty )
			.modifyAdditiveModifierForVariableValue( additiveModifierForVariableDifficulty )
			.modifyMultiplicativeModifierForVariableValue( multiplicativeModifierForVariableDifficulty )
			.modifyAdditiveModifierForTotalValue( additiveModifierForTotalDifficulty )
			.modifyMultiplicativeModifierForTotalValue( multiplicativeModifierForTotalDifficulty );
	}

	@Override
	public
	void removeEffectsFrom( ScalingStat difficulty )
	{
		difficulty
			.modifyAdditiveModifierForFixedValue( -additiveModifierForFixedDifficulty )
			.modifyMultiplicativeModifierForFixedValue( -multiplicativeModifierForFixedDifficulty )
			.modifyAdditiveModifierForVariableValue( -additiveModifierForVariableDifficulty )
			.modifyMultiplicativeModifierForVariableValue( -multiplicativeModifierForVariableDifficulty )
			.modifyAdditiveModifierForTotalValue( -additiveModifierForTotalDifficulty )
			.modifyMultiplicativeModifierForTotalValue( -multiplicativeModifierForTotalDifficulty );
	}

	public
	double getAdditiveModifierForFixedDifficulty()
	{
		return additiveModifierForFixedDifficulty;
	}

	public
	DifficultyModifier setAdditiveModifierForFixedDifficulty( double additiveModifierForFixedDifficulty )
	{
		this.additiveModifierForFixedDifficulty = additiveModifierForFixedDifficulty;
		return this;
	}

	public
	double getMultiplicativeModifierForFixedDifficulty()
	{
		return multiplicativeModifierForFixedDifficulty;
	}

	public
	DifficultyModifier setMultiplicativeModifierForFixedDifficulty( double multiplicativeModifierForFixedDifficulty )
	{
		this.multiplicativeModifierForFixedDifficulty = multiplicativeModifierForFixedDifficulty;
		return this;
	}

	public
	double getAdditiveModifierForVariableDifficulty()
	{
		return additiveModifierForVariableDifficulty;
	}

	public
	DifficultyModifier setAdditiveModifierForVariableDifficulty( double additiveModifierForVariableDifficulty )
	{
		this.additiveModifierForVariableDifficulty = additiveModifierForVariableDifficulty;
		return this;
	}

	public
	double getMultiplicativeModifierForVariableDifficulty()
	{
		return multiplicativeModifierForVariableDifficulty;
	}

	public
	DifficultyModifier setMultiplicativeModifierForVariableDifficulty( double multiplicativeModifierForVariableDifficulty )
	{
		this.multiplicativeModifierForVariableDifficulty =
			multiplicativeModifierForVariableDifficulty;
		return this;
	}

	public
	double getAdditiveModifierForTotalDifficulty()
	{
		return additiveModifierForTotalDifficulty;
	}

	public
	DifficultyModifier setAdditiveModifierForTotalDifficulty( double additiveModifierForTotalDifficulty )
	{
		this.additiveModifierForTotalDifficulty = additiveModifierForTotalDifficulty;
		return this;
	}

	public
	double getMultiplicativeModifierForTotalDifficulty()
	{
		return multiplicativeModifierForTotalDifficulty;
	}

	public
	DifficultyModifier setMultiplicativeModifierForTotalDifficulty( double multiplicativeModifierForTotalDifficulty )
	{
		this.multiplicativeModifierForTotalDifficulty = multiplicativeModifierForTotalDifficulty;
		return this;
	}

	private
	void initModifierValues()
	{
		additiveModifierForFixedDifficulty = 0;
		multiplicativeModifierForFixedDifficulty = 1;
		additiveModifierForVariableDifficulty = 0;
		multiplicativeModifierForVariableDifficulty = 1;
		additiveModifierForTotalDifficulty = 0;
		multiplicativeModifierForTotalDifficulty = 1;
	}
}
