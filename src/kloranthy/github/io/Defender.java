package kloranthy.github.io;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import kloranthy.github.io.modifier.DifficultyModifier;
import kloranthy.github.io.modifier.Modifier;
import kloranthy.github.io.proficiency.ExperienceLevel;
import kloranthy.github.io.proficiency.Proficiency;
import kloranthy.github.io.proficiency.TrainingLevel;
import kloranthy.github.io.stat.ScalingStat;
import kloranthy.github.io.stat.Stat;

/**
 * todo add armor
 * todo add character modifiers for defensive stats besides the already present evasion
 */
public
class Defender
{
	private
	Proficiency evasionProficiency;
	private TrainingLevel evasionTrainingLevel;
	private ExperienceLevel evasionExperienceLevel;
	private ScalingStat difficulty;
	private Stat damageNegation;
	private Stat damageResistance;
	private Stance defenderStance;
	private HashMap<UUID, Modifier> modifiersById;

	public
	Defender()
	{
		modifiersById = new HashMap<UUID, Modifier>();
	}

	public
	TrainingLevel getEvasionTrainingLevel()
	{
		return evasionTrainingLevel;
	}

	public
	Defender setEvasionTrainingLevel( TrainingLevel evasionTrainingLevel )
	{
		this.evasionTrainingLevel = evasionTrainingLevel;
		return this;
	}

	public
	ExperienceLevel getEvasionExperienceLevel()
	{
		return evasionExperienceLevel;
	}

	public
	Defender setEvasionExperienceLevel( ExperienceLevel evasionExperienceLevel )
	{
		this.evasionExperienceLevel = evasionExperienceLevel;
		return this;
	}

	public
	double getBaseDifficulty()
	{
		return difficulty.getBaseValue();
	}

	public
	Defender setBaseDifficulty( double baseDifficulty )
	{
		difficulty.setBaseValue( baseDifficulty );
		return this;
	}

	public
	double getBaseDamageNegation()
	{
		return damageNegation.getBaseValue();
	}

	public
	Defender setBaseDamageNegation( double baseDamageNegation )
	{
		damageNegation.setBaseValue( baseDamageNegation );
		return this;
	}

	public
	double getBaseDamageResistance()
	{
		return damageNegation.getBaseValue();
	}

	public
	Defender setBaseDamageResistance( double baseDamageResistance )
	{
		damageResistance.setBaseValue( baseDamageResistance );
		return this;
	}

	public
	Stance getDefenderStance()
	{
		return defenderStance;
	}

	public
	Defender setDefenderStance( Stance defenderStance )
	{
		if ( this.defenderStance != null )
		{
			// get the old stance's difficulty modifier and remove it
			UUID oldModifierId = this.defenderStance
				.getDifficultyModifier()
				.getModifierId();
			removeModifier( oldModifierId );
		}
		this.defenderStance = defenderStance;
		// get the new stance's difficulty modifier and apply its effects
		applyModifierEffects( defenderStance.getDifficultyModifier() );
		return this;
	}

	public
	Defender removeModifier( UUID modifierId )
	{
		if ( modifiersById.containsKey( modifierId ) )
		{
			Modifier modifier = modifiersById.remove( modifierId );
			//removeModifierEffects( modifier );
		}
		return this;
	}

	private
	void applyModifierEffects( DifficultyModifier difficultyModifier )
	{
		difficultyModifier.applyEffectsTo( difficulty );
	}

	public
	double performDifficultyRoll()
	{
		List<Dice> diceUsed = new LinkedList<Dice>();
		diceUsed.addAll( evasionTrainingLevel.getDiceUsed() );
		double roll = 0;
		for ( Dice dice : diceUsed )
		{
			roll += dice.roll();
		}
		roll += evasionExperienceLevel.bonus;
		return calculateDifficultyRating( roll );
	}

	private
	double calculateDifficultyRating( double roll )
	{
		double difficultyRating = calculateFixedDifficulty() + calculateVariableDifficulty( roll );
		double addModForTotalDifficulty = difficulty.getAdditiveModifierForTotalValue();
		double multiModForTotalDifficulty = difficulty.getMultiplicativeModifierForTotalValue();
		difficultyRating += addModForTotalDifficulty;
		difficultyRating *= multiModForTotalDifficulty;
		return difficultyRating;
	}

	public
	double calculateFixedDifficulty()
	{
		double fixedDifficulty = difficulty.getBaseValue();
		double addModForFixedDifficulty = difficulty.getAdditiveModifierForFixedValue();
		double multiModForFixedDifficulty = difficulty.getMultiplicativeModifierForFixedValue();
		fixedDifficulty += addModForFixedDifficulty;
		fixedDifficulty *= multiModForFixedDifficulty;
		return fixedDifficulty;
	}

	public
	double calculateVariableDifficulty( double roll )
	{
		double variableDifficulty = roll;
		double addModForVariableDifficulty = difficulty.getAdditiveModifierForVariableValue();
		double multiModForVariableDifficulty = difficulty.getMultiplicativeModifierForVariableValue();
		variableDifficulty += addModForVariableDifficulty;
		variableDifficulty *= multiModForVariableDifficulty;
		return variableDifficulty;
	}

	public
	double calculateExpectedDifficulty()
	{
		double expectedRoll = PossibleRolls
			.getInstance()
			.getExpectedRoll( evasionTrainingLevel );
		expectedRoll += evasionExperienceLevel.bonus;
		return calculateDifficultyRating( expectedRoll );
	}

	public
	void receiveDamage( double damageReceived )
	{
		System.out.println( "received " + damageReceived + " damage" );
		// todo add modifiers for damage negation
		double damageNegated = damageNegation.getBaseValue();
		double addModsForFixedDamageNegated = damageNegation.getAdditiveModifier();

		if ( damageNegated > damageReceived )
		{
			damageNegated = damageReceived;
		}
		System.out.println( "negated " + damageNegated + " damage" );
		damageReceived -= damageNegated;
		System.out.println( damageReceived + " damage remaining" );
		// todo modifiers for damage resistance
		double damageResisted = damageResistance.getBaseValue();
		damageResisted += damageResistance.getAdditiveModifier();
		damageResisted *= damageResistance.getMultiplicativeModifier();

		System.out.println( "resisted  " + damageResisted + " damage" );
		damageReceived -= damageResisted;
		if ( damageReceived < 0 ) // should never happen
		{
			damageReceived = 0;
		}
		System.out.println( damageReceived + " damage remaining" );
	}

	public
	Defender addModifer( Modifier modifier )
	{
		modifiersById.put(
			modifier.getModifierId(),
			modifier
							  );
		// todo come up with a way of applying modifiers of different types
		// having the kloranthy.github.io.modifier hold all stats it could possibly modify is not ideal
		// perhaps have it apply itself to defender/weapon?
		// or have types of modifiers?
		//applyModifierEffects( kloranthy.github.io.modifier );
		return this;
	}

	private
	void removeModifierEffects( DifficultyModifier difficultyModifier )
	{
		difficultyModifier.removeEffectsFrom( difficulty );
	}
}
