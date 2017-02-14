import java.util.LinkedList;
import java.util.List;

import modifier.AdditiveModifier;
import modifier.MultiplicativeModifier;

/**
 */
public class Weapon
{
	// chance to hit
	// fixed
	private double baseHitScore;
	private List<AdditiveModifier> additiveFixedHitModifiers;
	private List<MultiplicativeModifier> multiplicativeFixedHitModifiers;
	// variable
	private List<AdditiveModifier> additiveVariableHitModifiers;
	private List<MultiplicativeModifier> multiplicativeVariableHitModifiers;
	// total
	private List<AdditiveModifier> totalAdditiveModifiers;
	private List<MultiplicativeModifier> totalMultiplicativeModifiers;

	public Weapon()
	{
		additiveFixedHitModifiers = new LinkedList<AdditiveModifier>();
		multiplicativeFixedHitModifiers = new LinkedList<MultiplicativeModifier>();
		additiveVariableHitModifiers = new LinkedList<AdditiveModifier>();
		multiplicativeVariableHitModifiers = new LinkedList<MultiplicativeModifier>();
		totalAdditiveModifiers = new LinkedList<AdditiveModifier>();
		totalMultiplicativeModifiers = new LinkedList<MultiplicativeModifier>();
	}

	public static void main( String[] args )
	{
		Weapon shotgun = new Weapon();
		shotgun.setBaseHitScore( 10 )
				 .addFixedHitModifier(
				 	new AdditiveModifier().setName( "Innate Aim" )
												 .setValue( 2 )
											);
		System.out.println( "hit score for roll of 4: " + shotgun.calculateHitScore( 4 ));
	}

	public double getBaseHitScore()
	{
		return baseHitScore;
	}

	public Weapon setBaseHitScore( double baseHitScore )
	{
		this.baseHitScore = baseHitScore;
		return this;
	}

	public Weapon addFixedHitModifier( AdditiveModifier fixedHitModifier)
	{
		additiveFixedHitModifiers.add( fixedHitModifier );
		return this;
	}

	public double calculateHitScore( double roll )
	{
		double hitScore = calculateFixedHitScore() + calculateVariableHitScore( roll );
		hitScore = applyTotalAdditiveModifiers( hitScore );
		hitScore = applyTotalMultiplicativeModifiers( hitScore );
		return hitScore;
	}

	public double calculateExpectedHitScore(
		TrainingLevel trainingLevel,
		ExperienceLevel experienceLevel
														)
	{
		// todo add outcome tables to training levels
		double expectedRoll = 3;
		return calculateHitScore( expectedRoll );
	}

	public double calculateFixedHitScore()
	{
		double fixedHitScore = baseHitScore;
		fixedHitScore = applyFixedAdditiveModifiers( fixedHitScore );
		fixedHitScore = applyFixedMultiplicativeModifiers( fixedHitScore );
		return fixedHitScore;
	}

	private double calculateVariableHitScore( double roll )
	{
		double variableHitScore = applyVariableAdditiveModifiers( roll );
		variableHitScore = applyVariableMultiplicativeModifiers( variableHitScore );
		return variableHitScore;
	}

	private double applyFixedAdditiveModifiers( double fixedHitScore )
	{
		if ( additiveFixedHitModifiers.isEmpty() )
		{
			return fixedHitScore;
		}
		for ( AdditiveModifier fixedAdditiveModifier : additiveFixedHitModifiers )
		{
			fixedHitScore = fixedAdditiveModifier.applyTo( fixedHitScore );
		}
		return fixedHitScore;
	}

	private double applyFixedMultiplicativeModifiers( double fixedHitScore )
	{
		if ( multiplicativeFixedHitModifiers.isEmpty() )
		{
			return fixedHitScore;
		}
		for ( MultiplicativeModifier fixedMultiplicativeModifier : multiplicativeFixedHitModifiers )
		{
			fixedHitScore = fixedMultiplicativeModifier.applyTo( fixedHitScore );
		}
		return fixedHitScore;
	}

	private double applyVariableAdditiveModifiers( double variableHitScore )
	{
		if ( additiveVariableHitModifiers.isEmpty() )
		{
			return variableHitScore;
		}
		for ( AdditiveModifier variableAdditiveModifier : additiveVariableHitModifiers )
		{
			variableHitScore = variableAdditiveModifier.applyTo( variableHitScore );
		}
		return variableHitScore;
	}

	private double applyVariableMultiplicativeModifiers( double variableHitScore )
	{
		if ( multiplicativeVariableHitModifiers.isEmpty() )
		{
			return variableHitScore;
		}
		for ( MultiplicativeModifier variableMultiplicativeModifier : multiplicativeVariableHitModifiers )
		{
			variableHitScore = variableMultiplicativeModifier.applyTo( variableHitScore );
		}
		return variableHitScore;
	}

	private double applyTotalAdditiveModifiers( double hitScore )
	{
		if ( totalAdditiveModifiers.isEmpty() )
		{
			return hitScore;
		}
		for ( AdditiveModifier totalAdditiveModifier : totalAdditiveModifiers )
		{
			hitScore = totalAdditiveModifier.applyTo( hitScore );
		}
		return hitScore;
	}

	private double applyTotalMultiplicativeModifiers( double hitScore )
	{
		if ( totalMultiplicativeModifiers.isEmpty() )
		{
			return hitScore;
		}
		for ( MultiplicativeModifier totalMultiplicativeModifier : totalMultiplicativeModifiers )
		{
			hitScore = totalMultiplicativeModifier.applyTo( hitScore );
		}
		return hitScore;
	}
}
