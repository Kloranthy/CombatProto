package kloranthy.github.io;

/**
 */
public class WeaponModifier
	extends Modifier
{
	private double additiveModifierForFixedHitScore;
	private double multiplicativeModifierForFixedHitScore;
	private double additiveModifierForVariableHitScore;
	private double multiplicativeModifierForVariableHitScore;
	private double additiveModifierForTotalHitScore;
	private double multiplicativeModifierForTotalHitScore;
	private double additiveModifierForFixedDamage;
	private double multiplicativeModifierForFixedDamage;
	private double additiveModifierForVariableDamage;
	private double multiplicativeModifierForVariableDamage;
	private double additiveModifierForTotalDamage;
	private double multiplicativeModifierForTotalDamage;

	public WeaponModifier()
	{
	}

	public void applyEffectsTo( Weapon weapon )
	{
		ScalingStat hitScoreState = weapon.getHitScore();
		hitScoreState.setAdditiveModifierForFixedValue(
			getAdditiveModifierForFixedHitScore() + additiveModifierForFixedHitScore )
						 .setMultiplicativeModifierForFixedValue(
							 getMultiplicativeModifierForFixedHitScore() +
							 multiplicativeModifierForFixedHitScore )
						 .setAdditiveModifierForVariableValue( getAdditiveModifierForVariableHitScore() +
																			additiveModifierForVariableHitScore )
						 .setMultiplicativeModifierForVariableValue(
							 getMultiplicativeModifierForVariableHitScore() +
							 multiplicativeModifierForVariableHitScore )
						 .setAdditiveModifierForTotalValue(
							 getAdditiveModifierForTotalHitScore() + additiveModifierForTotalHitScore )
						 .setMultiplicativeModifierForTotalValue(
							 getMultiplicativeModifierForTotalHitScore() +
							 multiplicativeModifierForTotalHitScore );
		ScalingStat damageStat = weapon.getDamage();
		damageStat.setAdditiveModifierForFixedValue(
			getAdditiveModifierForFixedDamage() + additiveModifierForFixedDamage )
					 .setMultiplicativeModifierForFixedValue( getMultiplicativeModifierForFixedDamage() +
																			multiplicativeModifierForFixedDamage )
					 .setAdditiveModifierForVariableValue(
						 getAdditiveModifierForVariableDamage() + additiveModifierForVariableDamage )
					 .setMultiplicativeModifierForVariableValue(
						 getMultiplicativeModifierForVariableDamage() +
						 multiplicativeModifierForVariableDamage )
					 .setAdditiveModifierForTotalValue(
						 getAdditiveModifierForTotalDamage() + additiveModifierForTotalDamage )
					 .setMultiplicativeModifierForTotalValue( getMultiplicativeModifierForTotalDamage() +
																			multiplicativeModifierForTotalDamage );
	}

	public double getAdditiveModifierForFixedHitScore()
	{
		return additiveModifierForFixedHitScore;
	}

	public WeaponModifier setAdditiveModifierForFixedHitScore( double additiveModifierForFixedHitScore )
	{
		this.additiveModifierForFixedHitScore = additiveModifierForFixedHitScore;
		return this;
	}

	public double getMultiplicativeModifierForFixedHitScore()
	{
		return multiplicativeModifierForFixedHitScore;
	}

	public WeaponModifier setMultiplicativeModifierForFixedHitScore( double multiplicativeModifierForFixedHitScore )
	{
		this.multiplicativeModifierForFixedHitScore = multiplicativeModifierForFixedHitScore;
		return this;
	}

	public double getAdditiveModifierForVariableHitScore()
	{
		return additiveModifierForVariableHitScore;
	}

	public WeaponModifier setAdditiveModifierForVariableHitScore( double additiveModifierForVariableHitScore )
	{
		this.additiveModifierForVariableHitScore = additiveModifierForVariableHitScore;
		return this;
	}

	public double getMultiplicativeModifierForVariableHitScore()
	{
		return multiplicativeModifierForVariableHitScore;
	}

	public WeaponModifier setMultiplicativeModifierForVariableHitScore( double multiplicativeModifierForVariableHitScore )
	{
		this.multiplicativeModifierForVariableHitScore = multiplicativeModifierForVariableHitScore;
		return this;
	}

	public double getAdditiveModifierForTotalHitScore()
	{
		return additiveModifierForTotalHitScore;
	}

	public WeaponModifier setAdditiveModifierForTotalHitScore( double additiveModifierForTotalHitScore )
	{
		this.additiveModifierForTotalHitScore = additiveModifierForTotalHitScore;
		return this;
	}

	public double getMultiplicativeModifierForTotalHitScore()
	{
		return multiplicativeModifierForTotalHitScore;
	}

	public WeaponModifier setMultiplicativeModifierForTotalHitScore( double multiplicativeModifierForTotalHitScore )
	{
		this.multiplicativeModifierForTotalHitScore = multiplicativeModifierForTotalHitScore;
		return this;
	}

	public double getAdditiveModifierForFixedDamage()
	{
		return additiveModifierForFixedDamage;
	}

	public WeaponModifier setAdditiveModifierForFixedDamage( double additiveModifierForFixedDamage )
	{
		this.additiveModifierForFixedDamage = additiveModifierForFixedDamage;
		return this;
	}

	public double getMultiplicativeModifierForFixedDamage()
	{
		return multiplicativeModifierForFixedDamage;
	}

	public WeaponModifier setMultiplicativeModifierForFixedDamage( double multiplicativeModifierForFixedDamage )
	{
		this.multiplicativeModifierForFixedDamage = multiplicativeModifierForFixedDamage;
		return this;
	}

	public double getAdditiveModifierForVariableDamage()
	{
		return additiveModifierForVariableDamage;
	}

	public WeaponModifier setAdditiveModifierForVariableDamage( double additiveModifierForVariableDamage )
	{
		this.additiveModifierForVariableDamage = additiveModifierForVariableDamage;
		return this;
	}

	public double getMultiplicativeModifierForVariableDamage()
	{
		return multiplicativeModifierForVariableDamage;
	}

	public WeaponModifier setMultiplicativeModifierForVariableDamage( double multiplicativeModifierForVariableDamage )
	{
		this.multiplicativeModifierForVariableDamage = multiplicativeModifierForVariableDamage;
		return this;
	}

	public double getAdditiveModifierForTotalDamage()
	{
		return additiveModifierForTotalDamage;
	}

	public WeaponModifier setAdditiveModifierForTotalDamage( double additiveModifierForTotalDamage )
	{
		this.additiveModifierForTotalDamage = additiveModifierForTotalDamage;
		return this;
	}

	public double getMultiplicativeModifierForTotalDamage()
	{
		return multiplicativeModifierForTotalDamage;
	}

	public WeaponModifier setMultiplicativeModifierForTotalDamage( double multiplicativeModifierForTotalDamage )
	{
		this.multiplicativeModifierForTotalDamage = multiplicativeModifierForTotalDamage;
		return this;
	}

	public void removeEffectsFrom( Weapon weapon )
	{
		ScalingStat hitScoreState = weapon.getHitScore();
		hitScoreState.setAdditiveModifierForFixedValue(
			getAdditiveModifierForFixedHitScore() - additiveModifierForFixedHitScore )
						 .setMultiplicativeModifierForFixedValue(
							 getMultiplicativeModifierForFixedHitScore() -
							 multiplicativeModifierForFixedHitScore )
						 .setAdditiveModifierForVariableValue( getAdditiveModifierForVariableHitScore() -
																			additiveModifierForVariableHitScore )
						 .setMultiplicativeModifierForVariableValue(
							 getMultiplicativeModifierForVariableHitScore() -
							 multiplicativeModifierForVariableHitScore )
						 .setAdditiveModifierForTotalValue(
							 getAdditiveModifierForTotalHitScore() - additiveModifierForTotalHitScore )
						 .setMultiplicativeModifierForTotalValue(
							 getMultiplicativeModifierForTotalHitScore() -
							 multiplicativeModifierForTotalHitScore );
		ScalingStat damageStat = weapon.getDamage();
		damageStat.setAdditiveModifierForFixedValue(
			getAdditiveModifierForFixedDamage() - additiveModifierForFixedDamage )
					 .setMultiplicativeModifierForFixedValue( getMultiplicativeModifierForFixedDamage() -
																			multiplicativeModifierForFixedDamage )
					 .setAdditiveModifierForVariableValue(
						 getAdditiveModifierForVariableDamage() - additiveModifierForVariableDamage )
					 .setMultiplicativeModifierForVariableValue(
						 getMultiplicativeModifierForVariableDamage() -
						 multiplicativeModifierForVariableDamage )
					 .setAdditiveModifierForTotalValue(
						 getAdditiveModifierForTotalDamage() - additiveModifierForTotalDamage )
					 .setMultiplicativeModifierForTotalValue( getMultiplicativeModifierForTotalDamage() -
																			multiplicativeModifierForTotalDamage );
	}
}
