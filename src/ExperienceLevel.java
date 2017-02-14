/**
 */
public enum ExperienceLevel
{
	INEXPERIENCED(
		0,
		"inexperienced",
		0
	),
	NOVICE(
		1,
		"novice",
		1
	),
	VETERAN(
		2,
		"veteran",
		2
	),
	MASTER(
		3,
		"master",
		3
	);
	public final int level;
	public final String name;
	public final double bonus;

	ExperienceLevel(
		int level,
		String name,
		double bonus
						)
	{
		this.level = level;
		this.name = name;
		this.bonus = bonus;
	}
}
