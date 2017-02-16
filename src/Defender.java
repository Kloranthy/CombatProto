import modifier.DifficultyModifier;

/**
 */
public class Defender
{
	private TrainingLevel evasionTrainingLevel;
	private ExperienceLevel evasionExperienceLevel;
	private double baseDifficulty;
	private double additiveModifierForFixedDifficulty;
	private double multiplicativeModifierForFixedDifficulty;
	private double additiveModifierForVariableDifficulty;
	private double multiplicativeModifierForVariableDifficulty;
	private double additiveModifierForTotalDifficulty;
	private double multiplicativeModifierForTotalDifficulty;
	private Stance defenderStance;

	public Defender()
	{}

	public TrainingLevel getEvasionTrainingLevel()
	{
		return evasionTrainingLevel;
	}

	public Defender setEvasionTrainingLevel( TrainingLevel evasionTrainingLevel )
	{
		this.evasionTrainingLevel = evasionTrainingLevel;
		return this;
	}

	public ExperienceLevel getEvasionExperienceLevel()
	{
		return evasionExperienceLevel;
	}

	public Defender setEvasionExperienceLevel( ExperienceLevel evasionExperienceLevel )
	{
		this.evasionExperienceLevel = evasionExperienceLevel;
		return this;
	}

	public double getBaseDifficulty()
	{
		return baseDifficulty;
	}

	public Defender setBaseDifficulty( double baseDifficulty )
	{
		this.baseDifficulty = baseDifficulty;
		return this;
	}

	public Stance getDefenderStance()
	{
		return defenderStance;
	}

	public Defender setDefenderStance( Stance defenderStance )
	{
		// todo add a difficulty modifier to stance
		if ( this.defenderStance != null )
		{
			// get the old stance's difficulty modifier and remove its effects
		}
		this.defenderStance = defenderStance;
		// get the new stance's difficulty modifier and apply its effects
		//applyModifierEffects(  );
		return this;
	}

	public double calculateFixedDifficulty()
	{
		double fixedDifficulty = baseDifficulty;
		fixedDifficulty += additiveModifierForFixedDifficulty;
		fixedDifficulty += multiplicativeModifierForFixedDifficulty;
		return fixedDifficulty;
	}

	public double calculateVariableDifficulty( double roll )
	{
		double variableDifficulty = roll;
		variableDifficulty += additiveModifierForVariableDifficulty;
		variableDifficulty *= multiplicativeModifierForVariableDifficulty;
		return variableDifficulty;
	}

	public double calculateDifficulty( double roll )
	{
		double difficulty = calculateFixedDifficulty() + calculateVariableDifficulty( roll );
		difficulty += additiveModifierForTotalDifficulty;
		difficulty *= multiplicativeModifierForTotalDifficulty;
		return difficulty;
	}

	public double calculateEstimatedDifficulty()
	{
		double expectedRoll = 0;
		//expectedRoll += evasionTrainingLevel.possibleRolls.getExpectedRoll();
		expectedRoll += evasionExperienceLevel.bonus;
		return calculateDifficulty( expectedRoll );
	}

	private void applyModifierEffects( DifficultyModifier difficultyModifier )
	{
		additiveModifierForFixedDifficulty += difficultyModifier.getAdditiveModifierForFixedDifficulty();
		multiplicativeModifierForFixedDifficulty += difficultyModifier.getMultiplicativeModifierForFixedDifficulty();
		additiveModifierForVariableDifficulty += difficultyModifier.getAdditiveModifierForVariableDifficulty();
		multiplicativeModifierForVariableDifficulty += difficultyModifier.getMultiplicativeModifierForVariableDifficulty();
		additiveModifierForTotalDifficulty += difficultyModifier.getAdditiveModifierForTotalDifficulty();
		multiplicativeModifierForTotalDifficulty += difficultyModifier.getMultiplicativeModifierForTotalDifficulty();
	}

	private void removeModifierEffects( DifficultyModifier difficultyModifier )
	{
		additiveModifierForFixedDifficulty -= difficultyModifier.getAdditiveModifierForFixedDifficulty();
		multiplicativeModifierForFixedDifficulty -= difficultyModifier.getMultiplicativeModifierForFixedDifficulty();
		additiveModifierForVariableDifficulty -= difficultyModifier.getAdditiveModifierForVariableDifficulty();
		multiplicativeModifierForVariableDifficulty -= difficultyModifier.getMultiplicativeModifierForVariableDifficulty();
		additiveModifierForTotalDifficulty -= difficultyModifier.getAdditiveModifierForTotalDifficulty();
		multiplicativeModifierForTotalDifficulty -= difficultyModifier.getMultiplicativeModifierForTotalDifficulty();
	}
}
