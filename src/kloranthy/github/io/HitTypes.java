package kloranthy.github.io;

/**
 */
public enum HitTypes
{
	CRITICAL_HIT(
						1.5,
						1.5
	),
	HIT(
			1,
			1
	),
	PARTIAL_HIT(
					  0.5,
					  0.5
	);

	public final double requiredRatio;
	public final double damageMultiplier;

	HitTypes(
				  double requiredRatio,
				  double damageMultiplier
			  )
	{
		this.requiredRatio = requiredRatio;
		this.damageMultiplier = damageMultiplier;
	}
}
