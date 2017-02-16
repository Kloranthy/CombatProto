import java.util.EnumMap;

/**
 */
public class Attacker
{
	private EnumMap<WeaponType, TrainingLevel> trainingLevelByWeaponType;
	private EnumMap<WeaponType, ExperienceLevel> experienceLevelByWeaponType;
	private EnumMap<DamageType, TrainingLevel> trainingLevelByDamageType;
	private EnumMap<DamageType, ExperienceLevel> experienceLevelByDamageType;
	private Weapon weapon;
	// gained by aiming at target
	private int aimStacks;
	// the most aim stacks
	private int maxAimStacks;
	// multiplied by stacks and added to attacker roll
	private double aimBonusPerStack;

	public Attacker()
	{
		trainingLevelByWeaponType = new EnumMap<WeaponType, TrainingLevel>( WeaponType.class );
		for ( WeaponType weaponType : WeaponType.values() )
		{
			trainingLevelByWeaponType.put( weaponType, TrainingLevel.NONE );
		}
		experienceLevelByWeaponType = new EnumMap<WeaponType, ExperienceLevel>( WeaponType.class );
		for ( WeaponType weaponType : WeaponType.values() )
		{
			experienceLevelByWeaponType.put( weaponType, ExperienceLevel.INEXPERIENCED );
		}
		trainingLevelByDamageType = new EnumMap<DamageType, TrainingLevel>( DamageType.class );
		for ( DamageType damageType : DamageType.values() )
		{
			trainingLevelByDamageType.put( damageType, TrainingLevel.NONE );
		}
		experienceLevelByDamageType = new EnumMap<DamageType, ExperienceLevel>( DamageType.class );
		for ( DamageType damageType : DamageType.values() )
		{
			experienceLevelByDamageType.put( damageType, ExperienceLevel.INEXPERIENCED );
		}
		aimStacks = 0;
	}

	public Attacker setWeaponTypeTrainingLevel(
		WeaponType weaponType,
		TrainingLevel trainingLevel
													  )
	{
		trainingLevelByWeaponType.put( weaponType, trainingLevel );
		return this;
	}

	public Attacker setWeaponTypeExperienceLevel(
		WeaponType weaponType,
		ExperienceLevel experienceLevel
														 )
	{
		experienceLevelByWeaponType.put( weaponType, experienceLevel );
		return this;
	}

	public Attacker setDamageTypeTrainingLevel(
		DamageType damageType,
		TrainingLevel trainingLevel
															)
	{
		trainingLevelByDamageType.put( damageType, trainingLevel );
		return this;
	}

	public Attacker setDamageTypeExperienceLevel(
		DamageType damageType,
		ExperienceLevel experienceLevel
															  )
	{
		experienceLevelByDamageType.put( damageType, experienceLevel );
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
		double expectedRoll = 0;
		//expectedRoll += trainingLevelForWeaponType.possibleRolls.getExpectedRoll();
		expectedRoll += experienceLevelForWeaponType.bonus;
		//expectedRoll += trainingLevelForDamageType.possibleRolls.getExpectedRoll();
		expectedRoll += experienceLevelForDamageType.bonus;
		return weapon.calculateHitScore( expectedRoll );
	}

	public double calculatePartialHitChance( double difficulty)
	{
		double partialHitChance = 0;
		return partialHitChance;
	}

	public double calculateHitScore()
	{
		WeaponType weaponType = weapon.getWeaponType();
		TrainingLevel trainingLevelForWeaponType = trainingLevelByWeaponType.get( weaponType );
		ExperienceLevel experienceLevelForWeaponType = experienceLevelByWeaponType.get( weaponType );
		DamageType damageType = weapon.getDamageType();
		TrainingLevel trainingLevelForDamageType = trainingLevelByDamageType.get( damageType );
		ExperienceLevel experienceLevelForDamageType = experienceLevelByDamageType.get( damageType );
		//Dice dice = Dice.getInstance();
		double roll = 0;
		//roll += dice.rollFor( trainingLevelForWeaponType );
		roll += experienceLevelForWeaponType.bonus;
		//roll += dice.rollFor( trainingLevelForDamageType );
		roll += experienceLevelForDamageType.bonus;
		return weapon.calculateHitScore( roll );
	}

	public double calculateExpectedDamageDealt()
	{
		WeaponType weaponType = weapon.getWeaponType();
		TrainingLevel trainingLevelForWeaponType = trainingLevelByWeaponType.get( weaponType );
		ExperienceLevel experienceLevelForWeaponType = experienceLevelByWeaponType.get( weaponType );
		DamageType damageType = weapon.getDamageType();
		TrainingLevel trainingLevelForDamageType = trainingLevelByDamageType.get( damageType );
		ExperienceLevel experienceLevelForDamageType = experienceLevelByDamageType.get( damageType );
		double expectedRoll = 0;
		//expectedRoll += trainingLevelForWeaponType.possibleRolls.getExpectedRoll();
		expectedRoll += experienceLevelForWeaponType.bonus;
		//expectedRoll += trainingLevelForDamageType.possibleRolls.getExpectedRoll();
		expectedRoll += experienceLevelForDamageType.bonus;
		return weapon.calculateDamage( expectedRoll );
	}
}
