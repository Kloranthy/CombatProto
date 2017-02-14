/**
 */
public enum TrainingLevel
{
	NONE(
		0,
		"none",
		ExperienceLevel.NOVICE
	),
	BASIC(
		1,
		"basic",
		ExperienceLevel.VETERAN
	),
	INTERMEDIATE(
		2,
		"intermediate",
		ExperienceLevel.VETERAN
	),
	ADVANCED(
		3,
		"advanced",
		ExperienceLevel.MASTER
	);

	public final int level;
	public final String name;
	public final ExperienceLevel maxExperienceLevel;

	TrainingLevel(
		int level,
		String name,
		ExperienceLevel maxExperienceLevel
					 )
	{
		this.level = level;
		this.name = name;
		this.maxExperienceLevel = maxExperienceLevel;
	}
}
