package kloranthy.github.io;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import kloranthy.github.io.modifier.DifficultyModifier;
import kloranthy.github.io.modifier.Modifier;
import kloranthy.github.io.proficiency.ExperienceLevel;
import kloranthy.github.io.proficiency.TrainingLevel;
import kloranthy.github.io.stat.Stat;

/**
 */
public class Defender
{
	private TrainingLevel evasionTrainingLevel;
	private ExperienceLevel evasionExperienceLevel;
	private Stat difficulty;
	private Stat damageNegation;
	private Stat damageResistance;
	private double baseDifficulty;
	private double additiveModifierForFixedDifficulty;
	private double multiplicativeModifierForFixedDifficulty;
	private double additiveModifierForVariableDifficulty;
	private double multiplicativeModifierForVariableDifficulty;
	private double additiveModifierForTotalDifficulty;
	private double multiplicativeModifierForTotalDifficulty;
	private Stance defenderStance;
	private double baseDamageNegation;
	private double baseDamageResistance;
	private HashMap<UUID, Modifier> modifiersById;

	public Defender()
	{
		modifiersById = new HashMap<UUID, Modifier>();
		initModifierValues();
	}

	private void initModifierValues()
	{
		additiveModifierForFixedDifficulty = 0;
		multiplicativeModifierForFixedDifficulty = 1;
		additiveModifierForVariableDifficulty = 0;
		multiplicativeModifierForVariableDifficulty = 1;
		additiveModifierForTotalDifficulty = 0;
		multiplicativeModifierForTotalDifficulty = 1;
	}

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

	public double getBaseDamageNegation()
	{
		return baseDamageNegation;
	}

	public Defender setBaseDamageNegation( double baseDamageNegation )
	{
		this.baseDamageNegation = baseDamageNegation;
		return this;
	}

	public double getBaseDamageResistance()
	{
		return baseDamageResistance;
	}

	public Defender setBaseDamageResistance( double baseDamageResistance )
	{
		this.baseDamageResistance = baseDamageResistance;
		return this;
	}

	public Stance getDefenderStance()
	{
		return defenderStance;
	}

	public Defender setDefenderStance( Stance defenderStance )
	{
		if ( this.defenderStance != null )
		{
			// get the old stance's difficulty kloranthy.github.io.modifier and remove it
			UUID oldModifierId = this.defenderStance.getDifficultyModifier()
																 .getModifierId();
			removeModifer( oldModifierId );
		}
		this.defenderStance = defenderStance;
		// get the new stance's difficulty kloranthy.github.io.modifier and apply its effects
		applyModifierEffects( defenderStance.getDifficultyModifier() );
		return this;
	}

	public Defender removeModifer( UUID modifierId )
	{
		if ( modifiersById.containsKey( modifierId ) )
		{
			Modifier modifier = modifiersById.remove( modifierId );
			//removeModifierEffects( kloranthy.github.io.modifier );
		}
		return this;
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

	public double performDifficultyRoll()
	{
		List<Dice> diceUsed = new LinkedList<Dice>();
		diceUsed.addAll( evasionTrainingLevel.getDiceUsed() );
		double roll = 0;
		for ( Dice dice : diceUsed )
		{
			roll += dice.roll();
		}
		roll += evasionExperienceLevel.bonus;
		return calculateDifficulty( roll );
	}

	private double calculateDifficulty( double roll )
	{
		double difficulty = calculateFixedDifficulty() + calculateVariableDifficulty( roll );
		difficulty += additiveModifierForTotalDifficulty;
		difficulty *= multiplicativeModifierForTotalDifficulty;
		return difficulty;
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

	public double calculateExpectedDifficulty()
	{
		double expectedRoll = PossibleRolls.getInstance()
													  .getExpectedRoll( evasionTrainingLevel );
		expectedRoll += evasionExperienceLevel.bonus;
		return calculateDifficulty( expectedRoll );
	}

	public void receiveDamage( double damageReceived )
	{
		System.out.println( "received " + damageReceived + " damage" );
		double damageNegation = baseDamageNegation;
		System.out.println( "damage negation: " + damageNegation );
		// todo add modifiers for damage negation
		double damageNegated;
		if ( damageNegation > damageReceived )
		{
			damageNegated = damageReceived;
		}
		else
		{
			damageNegated = damageNegation;
		}
		System.out.println( "negated " + damageNegated + " damage" );
		damageReceived -= damageNegated;
		System.out.println( damageReceived + " damage remaining" );
		double damageResistance = baseDamageResistance;
		// todo modifiers for damage resistance
		System.out.println( "damage resistance: " + damageResistance );
		double damageResisted = damageReceived * damageResistance;
		System.out.println( "resisted  " + damageResisted + " damage" );
		damageReceived -= damageResisted;
		if ( damageReceived < 0 ) // should never happen
		{
			damageReceived = 0;
		}
		System.out.println( damageReceived + " damage remaining" );
	}

	public Defender addModifer( Modifier modifier )
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
