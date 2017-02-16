import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 */
public class Main
{
	/*
	private Scanner scanner;
	private Dice dice;
	private boolean running;

	final double CRITICAL_HIT_RATIO = 1.5;
	final double PARTIAL_HIT_RATIO = 0.5;

	private Attacker attacker;
	private Defender defender;

	public Main()
	{
		scanner = new Scanner( System.in );
	}

	public static void main( String[] args )
	{
		Main main = new Main();
		main.init();
		main.start();
	}

	private void init()
	{
		dice = Dice.getInstance();
		attacker = new Attacker();
		attacker.setWeaponTypeTrainingLevel( WeaponType.MELEE, TrainingLevel.INTERMEDIATE )
				  .setWeaponTypeExperienceLevel( WeaponType.MELEE, ExperienceLevel.VETERAN )
				  .setDamageTypeTrainingLevel( DamageType.KINETIC, TrainingLevel.BASIC )
				  .setAimBonusPerStack( 2 )
				  .setMaxAimStacks( 3 );
		defender = new Defender();
		defender.setEvasionTrainingLevel( TrainingLevel.INTERMEDIATE )
				  .setEvasionExperienceLevel( ExperienceLevel.NOVICE )
				  .setBaseDifficulty( 6 )
				  .setDefenderStance( Stance.STANDING );
	}

	public void start()
	{
		running = true;
		while ( running )
		{
			print( "enter command" );
			String input = scanner.nextLine();
			if ( input.length() != 0 )
			{
				handleInput( input );
			}
		}
	}

	private void print( String output )
	{
		System.out.println( output );
	}

	private void handleInput( String input )
	{
		input = input.toLowerCase();
		switch ( input )
		{
			case "a":
				aim();
				break;
			case "s":
				shoot();
				break;
			case "x":
				exit();
				break;
			default:
				print( "invalid input" );
		}
	}


	private void aim()
	{
		if ( aimStacks < maxAimStacks )
		{
			aimStacks++;
		}
		print( "aim stacks: " + aimStacks );
		printChances();
	}

	private void shoot()
	{
		print( "performing attacker roll" );
		double attackerRoll = performAttackerRoll();
		print( "applying roll bonuses" );
		attackerRoll = applyAttackerRollBonuses( attackerRoll );
		print( "attacker roll after bonuses: " + attackerRoll );
		attackerRoll = applyAttackerRollModifiers( attackerRoll );
		print( "attacker roll after modifiers: " + attackerRoll );
		attackerRoll = applyAttackerModifiers( attackerRoll );

		double defenderRoll = performDefenderRoll();

		if ( attackerRoll / defenderRoll >= CRITICAL_HIT_RATIO )
		{
			print( "scored a critical hit" );
		}
		else if ( attackerRoll / defenderRoll >= 1 )
		{
			print( "scored a hit" );
		}
		else if ( attackerRoll / defenderRoll >= PARTIAL_HIT_RATIO )
		{
			print( "scored a partial hit" );
		}
		else
		{
			print( "missed" );
		}
	}

	private void exit()
	{
		running = false;
	}

	private void printChances()
	{
		double partialHitChancePercent = convertToPercentileFormat( calculatePartialHitChance() );
		print( "partial hit chance: " + partialHitChancePercent + "%" );
		double hitChancePercent = convertToPercentileFormat( calculateHitChance() );
		print( "hit chance: " + hitChancePercent + "%" );
		double criticalHitChancePercent = convertToPercentileFormat( calculateCriticalHitChance() );
		print( "critical hit chance: " + criticalHitChancePercent + "%" );
		double evasionChancePercent = convertToPercentileFormat( calculateEvasionChance() );
		print( "evasion chance: " + evasionChancePercent + "%" );
	}



	private double calculateExpectedDefenderRoll()
	{
		//print( "base difficulty: " + baseDifficulty );
		double difficulty = baseDifficulty * defenderStance.difficultyModifier;
		//print( "difficulty: " + difficulty );
		PossibleRolls possibleRolls = outcomeTables[ defenderEvasionTrainingLevel.level ];
		double expectedEvasionRoll = possibleRolls.getExpectedRoll();
		//print( "expected evasion roll: " + expectedEvasionRoll );
		expectedEvasionRoll = expectedEvasionRoll * defenderStance.evasionModifier;
		expectedEvasionRoll += defenderEvasionExperienceLevel.bonus;
		double expectedDefenderRoll = difficulty + expectedEvasionRoll;
		//print( "expected defender roll: " + expectedDefenderRoll );
		return expectedDefenderRoll;
	}

	private double calculateExpectedAttackerRoll()
	{
		PossibleRolls weaponTypePossibleRolls = outcomeTables[ attackerWeaponTypeTrainingLevel.level ];
		double expectedWeaponTypeRoll = weaponTypePossibleRolls.getExpectedRoll();
		//print( "expected weapon type roll: " + expectedWeaponTypeRoll );
		PossibleRolls damageTypePossibleRolls = outcomeTables[ attackerWeaponTypeTrainingLevel.level ];
		double expectedDamageTypeRoll = damageTypePossibleRolls.getExpectedRoll();
		//print( "expected damage type roll: " + expectedDamageTypeRoll );
		double expectedAttackerRoll = expectedWeaponTypeRoll + 0.5 * expectedDamageTypeRoll;
		//print( "expected attacker roll: " + expectedAttackerRoll );
		double bonuses = calculateAttackerRollBonuses();
		expectedAttackerRoll += bonuses;
		//print( "expected attacker roll: " + expectedAttackerRoll );
		return expectedAttackerRoll;
	}

	private double calculatePartialHitChance()
	{
		double expectedDefenderRoll = calculateExpectedDefenderRoll();
		double requiredRoll = PARTIAL_HIT_RATIO * expectedDefenderRoll - calculateAttackerRollBonuses();
		requiredRoll = round( requiredRoll, 2 );
		print( "need at least: " + requiredRoll + " for a partial hit" );
		//PossibleRolls possibleRolls = ;
		return 0;//possibleRolls.getProbabilityOfRollGreaterThanOrEqualTo( requiredRoll );
	}

	private double calculateHitChance()
	{
		double expectedDefenderRoll = calculateExpectedDefenderRoll();
		double requiredRoll = expectedDefenderRoll - calculateAttackerRollBonuses();
		requiredRoll = round( requiredRoll, 2 );
		print( "need at least: " + requiredRoll + " for a hit" );
		PossibleRolls possibleRolls = outcomeTables[ attackerWeaponTypeTrainingLevel.level ];
		return possibleRolls.getProbabilityOfRollGreaterThanOrEqualTo( requiredRoll );
	} // only used weapon type, forgot damage type. this is why it is fucked up.

	private double calculateCriticalHitChance()
	{
		double expectedDefenderRoll = calculateExpectedDefenderRoll();
		double requiredRoll = CRITICAL_HIT_RATIO * expectedDefenderRoll - calculateAttackerRollBonuses();
		requiredRoll = round( requiredRoll, 2 );
		print( "need at least: " + requiredRoll + " for a critical hit" );
		PossibleRolls possibleRolls = outcomeTables[ attackerWeaponTypeTrainingLevel.level ];
		return possibleRolls.getProbabilityOfRollGreaterThanOrEqualTo( requiredRoll );
	}

	private double calculateEvasionChance()
	{
		double expectedAttackerRoll = calculateExpectedAttackerRoll();
		double difficulty = baseDifficulty * defenderStance.difficultyModifier;
		double requiredRoll = expectedAttackerRoll / PARTIAL_HIT_RATIO - difficulty;
		requiredRoll = requiredRoll / defenderStance.evasionModifier;
		requiredRoll= requiredRoll - defenderEvasionExperienceLevel.bonus;
		requiredRoll = round( requiredRoll, 2 );
		print( "need more than " + requiredRoll + " to evade attack" );
		PossibleRolls possibleRolls = outcomeTables[ defenderEvasionTrainingLevel.level ];
		return possibleRolls.getProbabilityOfRollGreaterThan( requiredRoll );
	}
	*/

}
