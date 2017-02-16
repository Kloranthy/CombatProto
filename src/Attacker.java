import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

/**
 * todo add range into hit calculations
 * to decide: how to add modifiers for attacker
 */
public class Attacker
{
	private EnumMap<WeaponType, TrainingLevel> trainingLevelByWeaponType;
	private EnumMap<WeaponType, ExperienceLevel> experienceLevelByWeaponType;
	private EnumMap<DamageType, TrainingLevel> trainingLevelByDamageType;
	private EnumMap<DamageType, ExperienceLevel> experienceLevelByDamageType;
	private Weapon weapon;
	private Defender target;
	// gained by aiming at target
	private int aimStacks;
	// the most aim stacks
	private int maxAimStacks;
	// multiplied by stacks and added to attacker roll
	private double aimBonusPerStack;
	// todo add modifiers at character level (perks, status effects/(de)buffs, etc)

	public Attacker()
	{
		trainingLevelByWeaponType = new EnumMap<WeaponType, TrainingLevel>( WeaponType.class );
		for ( WeaponType weaponType : WeaponType.values() )
		{
			trainingLevelByWeaponType.put(
				weaponType,
				TrainingLevel.NONE
												  );
		}
		experienceLevelByWeaponType = new EnumMap<WeaponType, ExperienceLevel>( WeaponType.class );
		for ( WeaponType weaponType : WeaponType.values() )
		{
			experienceLevelByWeaponType.put(
				weaponType,
				ExperienceLevel.INEXPERIENCED
													 );
		}
		trainingLevelByDamageType = new EnumMap<DamageType, TrainingLevel>( DamageType.class );
		for ( DamageType damageType : DamageType.values() )
		{
			trainingLevelByDamageType.put(
				damageType,
				TrainingLevel.NONE
												  );
		}
		experienceLevelByDamageType = new EnumMap<DamageType, ExperienceLevel>( DamageType.class );
		for ( DamageType damageType : DamageType.values() )
		{
			experienceLevelByDamageType.put(
				damageType,
				ExperienceLevel.INEXPERIENCED
													 );
		}
		aimStacks = 0;
	}

	public Attacker setWeaponTypeTrainingLevel(
		WeaponType weaponType,
		TrainingLevel trainingLevel
															)
	{
		trainingLevelByWeaponType.put(
			weaponType,
			trainingLevel
											  );
		return this;
	}

	public Attacker setWeaponTypeExperienceLevel(
		WeaponType weaponType,
		ExperienceLevel experienceLevel
															  )
	{
		experienceLevelByWeaponType.put(
			weaponType,
			experienceLevel
												 );
		return this;
	}

	public Attacker setDamageTypeTrainingLevel(
		DamageType damageType,
		TrainingLevel trainingLevel
															)
	{
		trainingLevelByDamageType.put(
			damageType,
			trainingLevel
											  );
		return this;
	}

	public Attacker setDamageTypeExperienceLevel(
		DamageType damageType,
		ExperienceLevel experienceLevel
															  )
	{
		experienceLevelByDamageType.put(
			damageType,
			experienceLevel
												 );
		return this;
	}

	public Weapon getWeapon()
	{
		return weapon;
	}

	public Attacker setWeapon( Weapon weapon )
	{
		this.weapon = weapon;
		return this;
	}

	public Defender getTarget()
	{
		return target;
	}

	public Attacker setTarget( Defender target )
	{
		this.target = target;
		return this;
	}

	public void aim()
	{
		if ( target != null )
		{
			if ( aimStacks < maxAimStacks )
			{
				aimStacks++;
			}
		}
	}

	public int getAimStacks()
	{
		return aimStacks;
	}

	public Attacker setAimStacks( int aimStacks )
	{
		this.aimStacks = aimStacks;
		return this;
	}

	public int getMaxAimStacks()
	{
		return maxAimStacks;
	}

	public Attacker setMaxAimStacks( int maxAimStacks )
	{
		this.maxAimStacks = maxAimStacks;
		return this;
	}

	public double getAimBonusPerStack()
	{
		return aimBonusPerStack;
	}

	public Attacker setAimBonusPerStack( double aimBonusPerStack )
	{
		this.aimBonusPerStack = aimBonusPerStack;
		return this;
	}

	public double calculateExpectedHitScore()
	{
		WeaponType weaponType = weapon.getWeaponType();
		TrainingLevel trainingLevelForWeaponType = trainingLevelByWeaponType.get( weaponType );
		ExperienceLevel experienceLevelForWeaponType = experienceLevelByWeaponType.get( weaponType );
		DamageType damageType = weapon.getDamageType();
		TrainingLevel trainingLevelForDamageType = trainingLevelByDamageType.get( damageType );
		ExperienceLevel experienceLevelForDamageType = experienceLevelByDamageType.get( damageType );
		double expectedHitScoreRoll = PossibleRolls.getInstance()
													  .getExpectedRoll(
														  trainingLevelForWeaponType,
														  trainingLevelForDamageType
																			);
		// apply bonuses
		expectedHitScoreRoll += experienceLevelForWeaponType.bonus;
		expectedHitScoreRoll += experienceLevelForDamageType.bonus;
		return calculateHitScore( expectedHitScoreRoll );
	}

	public double calculatePartialHitChance( double difficulty )
	{
		WeaponType weaponType = weapon.getWeaponType();
		DamageType damageType = weapon.getDamageType();
		TrainingLevel trainingLevelForWeaponType = trainingLevelByWeaponType.get( weaponType );
		TrainingLevel trainingLevelForDamageType = trainingLevelByDamageType.get( damageType );
		ExperienceLevel experienceLevelForWeaponType = experienceLevelByWeaponType.get( weaponType );
		ExperienceLevel experienceLevelForDamageType = experienceLevelByDamageType.get( damageType );
		double requiredRoll = difficulty;
		requiredRoll -= experienceLevelForWeaponType.bonus;
		requiredRoll -= experienceLevelForDamageType.bonus;
		// todo deduct weapon bonuses or ask weapon for chance to hit
		return PossibleRolls.getInstance()
								  .getProbabilityOfRollGreaterThanOrEqualTo(
			requiredRoll,
			trainingLevelForWeaponType,
			trainingLevelForDamageType
																						 );
	}

	public double calculateFixedHitScore()
	{
		double fixedHitScore = weapon.getBaseHitScore();
		fixedHitScore += weapon.getAdditiveModifierForFixedHitScore();
		fixedHitScore *= weapon.getMultiplicativeModifierForFixedHitScore();
		if ( aimStacks > 0 )
		{
			double aimBonus = aimStacks * aimBonusPerStack;
			fixedHitScore += aimBonus;
		}
		return fixedHitScore;
	}

	public double performHitScoreRoll()
	{
		WeaponType weaponType = weapon.getWeaponType();
		DamageType damageType = weapon.getDamageType();
		TrainingLevel trainingLevelForWeaponType = trainingLevelByWeaponType.get( weaponType );
		TrainingLevel trainingLevelForDamageType = trainingLevelByDamageType.get( damageType );
		ExperienceLevel experienceLevelForWeaponType = experienceLevelByWeaponType.get( weaponType );
		ExperienceLevel experienceLevelForDamageType = experienceLevelByDamageType.get( damageType );
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

	public double calculateVariableHitScore( double hitScoreRoll )
	{
		double variableHitScore = hitScoreRoll;
		variableHitScore += weapon.getAdditiveModifierForVariableHitScore();
		variableHitScore *= weapon.getMultiplicativeModifierForVariableHitScore();
		return variableHitScore;
	}

	public double calculateHitScore( double hitScoreRoll )
	{
		double hitScore = calculateFixedHitScore() + calculateVariableHitScore( hitScoreRoll );
		hitScore += weapon.getAdditiveModifierForTotalHitScore();
		hitScore *= weapon.getMultiplicativeModifierForTotalHitScore();
		return hitScore;
	}

	public double calculateFixedDamage()
	{
		double fixedDamage = weapon.getBaseDamage();
		fixedDamage += weapon.getAdditiveModifierForFixedDamage();
		fixedDamage *= weapon.getMultiplicativeModifierForFixedDamage();
		return fixedDamage;
	}

	public double calculateVariableDamage( double roll )
	{
		double variableDamage = roll;
		variableDamage += weapon.getAdditiveModifierForVariableDamage();
		variableDamage *= weapon.getMultiplicativeModifierForVariableDamage();
		return variableDamage;
	}

	public double calculateDamage( double roll )
	{
		double damage = calculateFixedDamage() + calculateVariableDamage( roll );
		damage += weapon.getAdditiveModifierForTotalDamage();
		damage *= weapon.getMultiplicativeModifierForTotalDamage();
		return damage;
	}

	public double calculateExpectedDamageDealt()
	{
		WeaponType weaponType = weapon.getWeaponType();
		DamageType damageType = weapon.getDamageType();
		TrainingLevel trainingLevelForWeaponType = trainingLevelByWeaponType.get( weaponType );
		TrainingLevel trainingLevelForDamageType = trainingLevelByDamageType.get( damageType );
		ExperienceLevel experienceLevelForWeaponType = experienceLevelByWeaponType.get( weaponType );
		ExperienceLevel experienceLevelForDamageType = experienceLevelByDamageType.get( damageType );
		double expectedRoll = PossibleRolls.getInstance()
													  .getExpectedRoll(
														  trainingLevelForWeaponType,
														  trainingLevelForDamageType
																			);
		expectedRoll += experienceLevelForWeaponType.bonus;
		expectedRoll += experienceLevelForDamageType.bonus;
		return calculateDamage( expectedRoll );
	}

	public void attack()
	{
		double hitScoreRoll = performHitScoreRoll();
		double hitScore = calculateHitScore( hitScoreRoll );
		double difficulty = target.performDifficultyRoll();
		double hitRatio = hitScore / difficulty;
		if ( hitRatio > HitTypes.CRITICAL_HIT.requiredRatio )
		{
			System.out.println( "critical hit" );
			double damageDealt = calculateDamage( hitScore );
			damageDealt *= HitTypes.CRITICAL_HIT.damageMultiplier;
			target.receiveDamage( damageDealt );
		}
		else if ( hitRatio > HitTypes.HIT.requiredRatio )
		{
			System.out.println( "hit" );
			double damageDealt = calculateDamage( hitScore );
			damageDealt *= HitTypes.HIT.damageMultiplier;
			target.receiveDamage( damageDealt );
		}
		else if ( hitRatio > HitTypes.PARTIAL_HIT.requiredRatio )
		{
			System.out.println( "partial hit" );
			double damageDealt = calculateDamage( hitScore );
			damageDealt *= HitTypes.PARTIAL_HIT.damageMultiplier;
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
}
