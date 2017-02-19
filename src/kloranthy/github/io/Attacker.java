package kloranthy.github.io;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import kloranthy.github.io.proficiency.ExperienceLevel;
import kloranthy.github.io.proficiency.Proficiency;
import kloranthy.github.io.proficiency.TrainingLevel;
import kloranthy.github.io.stat.ScalingStat;

/**
 * todo add range into hit calculations
 * todo refactor methods to take in a weapon to allow for comparing weapons in inventory, etc
 * todo refactor to allow for unarmed, check that weapon != null
 * to decide: how to add modifiers for attacker
 * to decide: when is the aim bonus applied
 */
public
class Attacker
{
	private EnumMap<WeaponType, Proficiency> weaponTypeProficiencies;
	private EnumMap<DamageType, Proficiency> damageTypeProficiencies;
	private Weapon weapon;
	private Defender target;
	// gained by aiming at target
	private int aimStacks;
	// the most aim stacks
	private int maxAimStacks;
	// multiplied by stacks and added to attacker roll
	private double aimBonusPerStack;
	// todo add modifiers at character level (perks, status effects/(de)buffs, etc)

	public
	Attacker()
	{
		weaponTypeProficiencies = new EnumMap<WeaponType, Proficiency>( WeaponType.class );
		for ( WeaponType weaponType : WeaponType.values() )
		{
			weaponTypeProficiencies.put(
				weaponType,
				new Proficiency()
												);
		}
		damageTypeProficiencies = new EnumMap<DamageType, Proficiency>( DamageType.class );
		for ( DamageType damageType : DamageType.values() )
		{
			damageTypeProficiencies.put(
				damageType,
				new Proficiency()
												);
		}

		aimStacks = 0;
	}

	public
	Attacker setWeaponTypeTrainingLevel(
													  WeaponType weaponType,
													  TrainingLevel trainingLevel
												  )
	{
		Proficiency proficiency = weaponTypeProficiencies.get( weaponType );
		proficiency.setTrainingLevel( trainingLevel );
		return this;
	}

	public
	Attacker setWeaponTypeExperienceLevel(
														 WeaponType weaponType,
														 ExperienceLevel experienceLevel
													 )
	{
		Proficiency proficiency = weaponTypeProficiencies.get( weaponType );
		proficiency.setExperienceLevel( experienceLevel );
		return this;
	}

	public
	Attacker setDamageTypeTrainingLevel(
													  DamageType damageType,
													  TrainingLevel trainingLevel
												  )
	{
		Proficiency proficiency = damageTypeProficiencies.get( damageType );
		proficiency.setTrainingLevel( trainingLevel );
		return this;
	}

	public
	Attacker setDamageTypeExperienceLevel(
														 DamageType damageType,
														 ExperienceLevel experienceLevel
													 )
	{
		Proficiency proficiency = damageTypeProficiencies.get( damageType );
		proficiency.setExperienceLevel( experienceLevel );
		return this;
	}

	public
	Weapon getWeapon()
	{
		return weapon;
	}

	public
	Attacker setWeapon( Weapon weapon )
	{
		this.weapon = weapon;
		return this;
	}

	public
	Defender getTarget()
	{
		return target;
	}

	public
	Attacker setTarget( Defender target )
	{
		this.target = target;
		return this;
	}

	public
	void aim()
	{
		if ( target != null )
		{
			if ( aimStacks < maxAimStacks )
			{
				aimStacks++;
			}
		}
	}

	public
	int getAimStacks()
	{
		return aimStacks;
	}

	public
	Attacker setAimStacks( int aimStacks )
	{
		this.aimStacks = aimStacks;
		return this;
	}

	public
	int getMaxAimStacks()
	{
		return maxAimStacks;
	}

	public
	Attacker setMaxAimStacks( int maxAimStacks )
	{
		this.maxAimStacks = maxAimStacks;
		return this;
	}

	public
	double getAimBonusPerStack()
	{
		return aimBonusPerStack;
	}

	public
	Attacker setAimBonusPerStack( double aimBonusPerStack )
	{
		this.aimBonusPerStack = aimBonusPerStack;
		return this;
	}

	public
	double calculateExpectedHitScore()
	{
		// get the weapon and damage types of the weapon
		WeaponType weaponType = weapon.getWeaponType();
		DamageType damageType = weapon.getDamageType();
		// get the attacker's proficiencies with those weapon and damage types
		Proficiency weaponTypeProficiency = weaponTypeProficiencies.get( weaponType );
		Proficiency damageTypeProficiency = damageTypeProficiencies.get( damageType );
		// get the training and experience levels from the proficiencies
		TrainingLevel trainingLevelForWeaponType = weaponTypeProficiency.getTrainingLevel();
		ExperienceLevel experienceLevelForWeaponType = weaponTypeProficiency.getExperienceLevel();
		TrainingLevel trainingLevelForDamageType = damageTypeProficiency.getTrainingLevel();
		ExperienceLevel experienceLevelForDamageType = damageTypeProficiency.getExperienceLevel();
		// get the expected roll for the training levels
		double expectedHitScoreRoll = PossibleRolls.getInstance()
																 .getExpectedRoll(
																	 trainingLevelForWeaponType,
																	 trainingLevelForDamageType
																					  );
		// apply experience bonuses
		expectedHitScoreRoll += experienceLevelForWeaponType.bonus;
		expectedHitScoreRoll += experienceLevelForDamageType.bonus;
		// calculate the hit score using the expected hit score roll
		return calculateHitScore( expectedHitScoreRoll );
	}

	public
	double calculateHitScore( double hitScoreRoll )
	{
		// combine the fixed and variable portions of the hit score
		double hitScore = calculateFixedHitScore() + calculateVariableHitScore( hitScoreRoll );
		// get the weapon's hit score stat
		ScalingStat weaponHitScore = weapon.getHitScore();
		// get the total hit score mods from the weapon's hit score stat
		// todo: add in mods for character and combine them with weapon mods
		double addModForTotalHitScore = weaponHitScore.getAdditiveModifierForTotalValue();
		double multiModForTotalHitScore = weaponHitScore.getMultiplicativeModifierForTotalValue();
		// apply the mods to the hit score
		hitScore += addModForTotalHitScore;
		hitScore *= multiModForTotalHitScore;
		// if aiming apply the aim bonus
		if ( aimStacks > 0 )
		{
			double aimBonus = aimStacks * aimBonusPerStack;
			hitScore += aimBonus;
		}
		return hitScore;
	}

	public
	double calculateFixedHitScore()
	{
		// get the weapon's hit score stat
		ScalingStat weaponHitScore = weapon.getHitScore();
		// get the base value of the hit score
		double fixedHitScore = weaponHitScore.getBaseValue();
		// get the fixed hit score mods from the weapon's hit score
		// todo: add in mods for character and combine them with weapon mods
		double addModForFixedHitScore = weaponHitScore.getAdditiveModifierForFixedValue();
		double multiModForFixedHitScore = weaponHitScore.getMultiplicativeModifierForFixedValue();
		// apply the mods to the fixed hti score
		fixedHitScore += addModForFixedHitScore;
		fixedHitScore *= multiModForFixedHitScore;
		return fixedHitScore;
	}

	public
	double calculateVariableHitScore( double hitScoreRoll )
	{
		double variableHitScore = hitScoreRoll;
		// get the weapon's hit score stat
		ScalingStat weaponHitScore = weapon.getHitScore();
		// get the variable hit score mods from the weapon's hit score
		// todo: add in mods for character and combine them with weapon mods
		double addModForVariableHitScore = weaponHitScore.getAdditiveModifierForVariableValue();
		double multiModForVariableHitScore =
			weaponHitScore.getMultiplicativeModifierForVariableValue();

		// if aiming apply the aim bonus
		if ( aimStacks > 0 )
		{
			double aimBonus = aimStacks * aimBonusPerStack;
			variableHitScore += aimBonus;
		}
		// apply the mods to the variable hit score
		variableHitScore += addModForVariableHitScore;
		variableHitScore *= multiModForVariableHitScore;
		return variableHitScore;
	}

	public
	int calculateRollRequiredForHit(
												 HitType hitType,
												 double difficulty
											 )
	{
		double rollRequired = difficulty;
		rollRequired *= hitType.requiredRatio;
		// get the weapon's hit score stat
		ScalingStat weaponHitScore = weapon.getHitScore();
		// get the total hit score mods from the weapon's hit score stat
		double addModForTotalHitScore = weaponHitScore.getAdditiveModifierForTotalValue();
		double multiModForTotalHitScore = weaponHitScore.getMultiplicativeModifierForTotalValue();
		// remove the total hit score modifiers from the required difficulty
		rollRequired /= multiModForTotalHitScore;
		rollRequired -= addModForTotalHitScore;
		// deduct the fixed hit score from the required difficulty
		double fixedHitScore = calculateFixedHitScore();
		rollRequired -= fixedHitScore;
		double addModForVariableHitScore = weaponHitScore.getAdditiveModifierForVariableValue();
		double multiModForVariableHitScore =
			weaponHitScore.getMultiplicativeModifierForVariableValue();
		// remove the variable hit score modifiers from the required difficulty
		rollRequired /= multiModForVariableHitScore;
		rollRequired -= addModForVariableHitScore;
		// round to the nearest whole number and convert to int
		return (int) Utilities.round(
			rollRequired,
			0
											 );
	}

	public
	double calculatePartialHitChance( double difficulty )
	{
		double requiredRoll = calculateRollRequiredForHit(
			HitType.PARTIAL_HIT,
			difficulty
																		 );
		// get the weapon and damage types of the weapon
		WeaponType weaponType = weapon.getWeaponType();
		DamageType damageType = weapon.getDamageType();
		// get the attacker's proficiencies with those weapon and damage types
		Proficiency weaponTypeProficiency = weaponTypeProficiencies.get( weaponType );
		Proficiency damageTypeProficiency = damageTypeProficiencies.get( damageType );
		// get the training and experience levels from the proficiencies
		TrainingLevel trainingLevelForWeaponType = weaponTypeProficiency.getTrainingLevel();
		ExperienceLevel experienceLevelForWeaponType = weaponTypeProficiency.getExperienceLevel();
		TrainingLevel trainingLevelForDamageType = damageTypeProficiency.getTrainingLevel();
		ExperienceLevel experienceLevelForDamageType = damageTypeProficiency.getExperienceLevel();
		// deduct the experience bonuses
		requiredRoll -= experienceLevelForWeaponType.bonus;
		requiredRoll -= experienceLevelForDamageType.bonus;
		return PossibleRolls.getInstance()
								  .getProbabilityOfRollGreaterThanOrEqualTo(
									  requiredRoll,
									  trainingLevelForWeaponType,
									  trainingLevelForDamageType
																						 );
	}

	public
	double calculateExpectedDamageDealt( Defender target )
	{
		double expectedDifficulty = target.calculateExpectedDifficulty();
		// get the weapon and damage types of the weapon
		WeaponType weaponType = weapon.getWeaponType();
		DamageType damageType = weapon.getDamageType();
		// get the attacker's proficiencies with those weapon and damage types
		Proficiency weaponTypeProficiency = weaponTypeProficiencies.get( weaponType );
		Proficiency damageTypeProficiency = damageTypeProficiencies.get( damageType );
		// get the training and experience levels from the proficiencies
		TrainingLevel trainingLevelForWeaponType = weaponTypeProficiency.getTrainingLevel();
		ExperienceLevel experienceLevelForWeaponType = weaponTypeProficiency.getExperienceLevel();
		TrainingLevel trainingLevelForDamageType = damageTypeProficiency.getTrainingLevel();
		ExperienceLevel experienceLevelForDamageType = damageTypeProficiency.getExperienceLevel();
		PossibleRolls possibleRolls = PossibleRolls.getInstance();
		// get the highest possible roll for the used training levels
		int maxRoll = possibleRolls.getHighestRoll(
			trainingLevelForWeaponType,
			trainingLevelForDamageType
																);
		// calculate the rolls required for each of the different hit types
		int rollRequiredForPartialHit = calculateRollRequiredForHit(
			HitType.PARTIAL_HIT,
			expectedDifficulty
																					  );
		int rollRequiredForHit = calculateRollRequiredForHit(
			HitType.HIT,
			expectedDifficulty
																			 );
		int rollRequiredForCriticalHit = calculateRollRequiredForHit(
			HitType.CRITICAL_HIT,
			expectedDifficulty
																						);
		double expectedDamage = 0;
		for (
			int roll = rollRequiredForPartialHit;
			roll < rollRequiredForHit && roll <= maxRoll;
			roll++
			)
		{
			double damage = calculateDamage( roll ) * HitType.PARTIAL_HIT.damageMultiplier;
			double probability = possibleRolls.getProbabilityOfRoll(
				roll,
				trainingLevelForWeaponType,
				trainingLevelForDamageType
																					 );
			expectedDamage += probability * damage;
		}
		for (
			int roll = rollRequiredForHit;
			roll < rollRequiredForCriticalHit && roll <= maxRoll;
			roll++
			)
		{
			double probability = possibleRolls.getProbabilityOfRoll(
				roll,
				trainingLevelForWeaponType,
				trainingLevelForDamageType
																					 );
			double damage = calculateDamage( roll ) * HitType.HIT.damageMultiplier;
			expectedDamage += probability * damage;
		}
		for (
			int roll = rollRequiredForCriticalHit;
			roll <= maxRoll;
			roll++
			)
		{
			double probability = possibleRolls.getProbabilityOfRoll(
				roll,
				trainingLevelForWeaponType,
				trainingLevelForDamageType
																					 );
			double damage = calculateDamage( roll ) * HitType.CRITICAL_HIT.damageMultiplier;
			expectedDamage += probability * damage;
		}
		return expectedDamage;
	}

	public
	double calculateDamage( double roll )
	{
		double damage = calculateFixedDamage() + calculateVariableDamage( roll );
		ScalingStat weaponDamage = weapon.getDamage();
		double additiveModifierForTotalDamage = weaponDamage.getAdditiveModifierForTotalValue();
		double multiplicativeModifierForTotalDamage =
			weaponDamage.getMultiplicativeModifierForTotalValue();
		//damage += additiveModifierForTotalDamage;
		//damage *= multiplicativeModifierForTotalDamage;
		return damage;
	}

	public
	double calculateFixedDamage()
	{
		ScalingStat weaponDamage = weapon.getDamage();
		double fixedDamage = weaponDamage.getBaseValue();
		//fixedDamage += additiveModifierForFixedDamage;
		//fixedDamage *= multiplicativeModifierForFixedDamage;
		return fixedDamage;
	}

	public
	double calculateVariableDamage( double roll )
	{
		double variableDamage = roll;
		//variableDamage += weapon.getAdditiveModifierForVariableDamage();
		//variableDamage *= weapon.getMultiplicativeModifierForVariableDamage();
		return variableDamage;
	}

	public
	void attack()
	{
		// perform the hit score roll
		double hitScoreRoll = performHitScoreRoll();
		// calculate the hit score based on the roll
		double hitScore = calculateHitScore( hitScoreRoll );
		//
		double difficulty = target.performDifficultyRoll();
		//
		double hitRatio = hitScore / difficulty;
		if ( hitRatio > HitType.CRITICAL_HIT.requiredRatio )
		{
			System.out.println( "critical hit" );
			double damageDealt = calculateDamage( hitScore );
			damageDealt *= HitType.CRITICAL_HIT.damageMultiplier;
			target.receiveDamage( damageDealt );
		}
		else if ( hitRatio > HitType.HIT.requiredRatio )
		{
			System.out.println( "hit" );
			double damageDealt = calculateDamage( hitScore );
			damageDealt *= HitType.HIT.damageMultiplier;
			target.receiveDamage( damageDealt );
		}
		else if ( hitRatio > HitType.PARTIAL_HIT.requiredRatio )
		{
			System.out.println( "partial hit" );
			double damageDealt = calculateDamage( hitScore );
			damageDealt *= HitType.PARTIAL_HIT.damageMultiplier;
			target.receiveDamage( damageDealt );
		}
		else
		{
			System.out.println( "miss" );
		}
		if ( aimStacks > 0 )
		{
			// aim stacks are consumed
			aimStacks = 0;
		}
	}

	public
	double performHitScoreRoll()
	{
		// get the weapon and damage types of the weapon
		WeaponType weaponType = weapon.getWeaponType();
		DamageType damageType = weapon.getDamageType();
		// get the attacker's proficiencies with those weapon and damage types
		Proficiency weaponTypeProficiency = weaponTypeProficiencies.get( weaponType );
		Proficiency damageTypeProficiency = damageTypeProficiencies.get( damageType );
		// get the training and experience levels from the proficiencies
		TrainingLevel trainingLevelForWeaponType = weaponTypeProficiency.getTrainingLevel();
		ExperienceLevel experienceLevelForWeaponType = weaponTypeProficiency.getExperienceLevel();
		TrainingLevel trainingLevelForDamageType = damageTypeProficiency.getTrainingLevel();
		ExperienceLevel experienceLevelForDamageType = damageTypeProficiency.getExperienceLevel();
		List<Dice> diceUsed = new LinkedList<Dice>();
		diceUsed.addAll( trainingLevelForWeaponType.getDiceUsed() );
		diceUsed.addAll( trainingLevelForDamageType.getDiceUsed() );
		double roll = 0;
		for ( Dice dice : diceUsed )
		{
			roll += dice.roll();
		}
		roll += experienceLevelForWeaponType.bonus;
		roll += experienceLevelForDamageType.bonus;
		return roll;
	}
}
