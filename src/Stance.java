/**
 */
public enum Stance
{
	STANDING(
		1,
		1
	),
	CROUCHING(
		1.5,
		0.5
	),
	PRONE(
		1.75,
		0
	);

	public final double difficultyModifier;
	public final double evasionModifier;

	Stance(
		double difficultyModifier,
		double evasionModifier
			)
	{
		this.difficultyModifier = difficultyModifier;
		this.evasionModifier = evasionModifier;
	}
}
