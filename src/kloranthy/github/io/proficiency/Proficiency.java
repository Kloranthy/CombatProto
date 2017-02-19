package kloranthy.github.io.proficiency;

/**
 */
public
class Proficiency
{
	private TrainingLevel trainingLevel;
	private ExperienceLevel experienceLevel;
	// todo add progress to next exp level

	public
	Proficiency()
	{
		trainingLevel = TrainingLevel.NONE;
		experienceLevel = ExperienceLevel.INEXPERIENCED;
	}

	public
	TrainingLevel getTrainingLevel()
	{
		return trainingLevel;
	}

	public
	Proficiency setTrainingLevel( TrainingLevel trainingLevel )
	{
		this.trainingLevel = trainingLevel;
		return this;
	}

	public
	ExperienceLevel getExperienceLevel()
	{
		return experienceLevel;
	}

	public
	Proficiency setExperienceLevel( ExperienceLevel experienceLevel )
	{
		this.experienceLevel = experienceLevel;
		return this;
	}

	public
	boolean increaseTrainingLevel()
	{
		switch ( trainingLevel )
		{
			case NONE:
				trainingLevel = TrainingLevel.BASIC;
				break;
			case BASIC:
				trainingLevel = TrainingLevel.INTERMEDIATE;
				break;
			case INTERMEDIATE:
				trainingLevel = TrainingLevel.ADVANCED;
				break;
			case ADVANCED:
				return false;
		}
		experienceLevel = ExperienceLevel.INEXPERIENCED;
		return true;
	}

	public
	boolean increaseExperienceLevel()
	{
		if ( trainingLevel.maxExperienceLevel == experienceLevel )
		{
			return false;
		}
		switch ( experienceLevel )
		{
			case INEXPERIENCED:
				experienceLevel = ExperienceLevel.NOVICE;
				return true;
			case NOVICE:
				experienceLevel = ExperienceLevel.VETERAN;
				return true;
			case VETERAN:
				experienceLevel = ExperienceLevel.MASTER;
				return true;
			case MASTER:
				return false;
			default:
				return false;
		}
	}
}
