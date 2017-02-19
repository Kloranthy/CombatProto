package kloranthy.github.io.proficiency;

import java.util.LinkedList;
import java.util.List;

import kloranthy.github.io.Dice;

/**
 */
public
enum TrainingLevel
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

	public
	List<Dice> getDiceUsed()
	{
		List<Dice> diceUsed = new LinkedList<Dice>();
		switch ( this )
		{
			case NONE:
				diceUsed.add( new Dice( 8 ) );
				break;
			case BASIC:
				diceUsed.add( new Dice( 6 ) );
				diceUsed.add( new Dice( 6 ) );
				break;
			case INTERMEDIATE:
				diceUsed.add( new Dice( 5 ) );
				diceUsed.add( new Dice( 5 ) );
				diceUsed.add( new Dice( 5 ) );
				break;
			case ADVANCED:
				diceUsed.add( new Dice( 4 ) );
				diceUsed.add( new Dice( 4 ) );
				diceUsed.add( new Dice( 4 ) );
				diceUsed.add( new Dice( 4 ) );
				break;
		}
		return diceUsed;
	}
}
