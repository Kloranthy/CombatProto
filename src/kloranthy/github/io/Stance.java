package kloranthy.github.io;

import java.util.UUID;

/**
 */
public enum Stance
{
	STANDING( "standing" ),
	CROUCHING( "crouching" ),
	PRONE( "prone" );

	public final String name;
	public final UUID standingModifierId = UUID.randomUUID();
	public final UUID crouchingModifierId = UUID.randomUUID();
	public final UUID proneModifierId = UUID.randomUUID();

	Stance( String name )
	{
		this.name = name;
	}

	public DifficultyModifier getDifficultyModifier()
	{
		// note: always returns a new difficulty kloranthy.github.io.modifier
		// which means the kloranthy.github.io.modifier returned won't be
		// found in the defender's modifiers
		DifficultyModifier difficultyModifier = new DifficultyModifier();
		switch ( this )
		{
			case STANDING:
				difficultyModifier.setModifierId( standingModifierId )
										.setMultiplicativeModifierForFixedDifficulty( 0 )
										.setMultiplicativeModifierForVariableDifficulty( 0 );
				break;
			case CROUCHING:
				difficultyModifier.setModifierId( crouchingModifierId )
										.setMultiplicativeModifierForFixedDifficulty( 0.5 )
										.setMultiplicativeModifierForVariableDifficulty( -0.5 );
				break;
			case PRONE:
				difficultyModifier.setModifierId( proneModifierId )
										.setMultiplicativeModifierForFixedDifficulty( 0.75 )
										.setMultiplicativeModifierForVariableDifficulty( -0.75 );
		}
		return difficultyModifier;
	}
}
