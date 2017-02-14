import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 */
public class Main
{
	private Scanner scanner;
	private Dice dice;
	private boolean running;

	final double PARTIAL_HIT_RATIO = 0.5;
	final double CRITICAL_HIT_RATIO = 1.5;

	// attacker variables
	private OutcomeTable[] outcomeTables;
	private TrainingLevel attackerWeaponTypeTrainingLevel;
	private ExperienceLevel attackerWeaponTypeExperienceLevel;
	private TrainingLevel attackerDamageTypeTrainingLevel;
	private ExperienceLevel attackerDamageTypeExperienceLevel;
	// gained by aiming at target
	private int aimStacks;
	// the most aim stacks
	private int maxAimStacks;
	// multiplied by stacks and added to attacker roll
	private double aimBonusPerStack;

	// defender variables
	// base difficulty to hit
	private double baseDifficulty;
	private Stance defenderStance;
	private TrainingLevel defenderEvasionTrainingLevel;
	private ExperienceLevel defenderEvasionExperienceLevel;

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
		initOutcomeTables();
		dice = new Dice();
		attackerWeaponTypeTrainingLevel = TrainingLevel.NONE;
		attackerWeaponTypeExperienceLevel = ExperienceLevel.INEXPERIENCED;
		attackerDamageTypeTrainingLevel = TrainingLevel.NONE;
		attackerDamageTypeExperienceLevel = ExperienceLevel.INEXPERIENCED;
		aimStacks = 0;
		aimBonusPerStack = 2;
		maxAimStacks = 3;
		baseDifficulty = 6;
		defenderStance = Stance.STANDING;
		defenderEvasionTrainingLevel = TrainingLevel.NONE;
		defenderEvasionExperienceLevel = ExperienceLevel.INEXPERIENCED;
	}

	public void start()
	{
		print( "attacker weapon type training: " + attackerWeaponTypeTrainingLevel.name );
		print( "attacker weapon type experience: " + attackerWeaponTypeExperienceLevel.name );
		print( "attacker damage type training: " + attackerDamageTypeTrainingLevel.name );
		print( "attacker damage type experience: " + attackerDamageTypeExperienceLevel.name );
		print( "base difficulty: " + baseDifficulty );
		print( "defender evasion training: " + defenderEvasionTrainingLevel.name );
		print( "attacker evasion experience: " + defenderEvasionExperienceLevel.name );
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

	private void initOutcomeTables()
	{
		outcomeTables = new OutcomeTable[ TrainingLevel.values().length ];
		for ( TrainingLevel trainingLevel : TrainingLevel.values() )
		{
			outcomeTables[ trainingLevel.level ] = calculateOutcomeTable( trainingLevel );
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

	private OutcomeTable calculateOutcomeTable(
		TrainingLevel trainingLevel
															)
	{
		OutcomeTable outcomeTable = new OutcomeTable();
		switch ( trainingLevel )
		{
			case NONE:
				for (
					int d1 = 1;
					d1 <= 8;
					d1++
					)
				{
					int roll = d1;
					outcomeTable.addOutcome( roll );
				}
				break;
			case BASIC:
				for (
					int d1 = 1;
					d1 <= 6;
					d1++
					)
				{
					for (
						int d2 = 1;
						d2 <= 6;
						d2++
						)
					{

						int roll = d1 + d2;
						outcomeTable.addOutcome( roll );
					}
				}
				break;
			case INTERMEDIATE:
				for (
					int d1 = 1;
					d1 <= 5;
					d1++
					)
				{
					for (
						int d2 = 1;
						d2 <= 5;
						d2++
						)
					{
						for (
							int d3 = 1;
							d3 <= 5;
							d3++
							)
						{
							int roll = d1 + d2 + d3;
							outcomeTable.addOutcome( roll );
						}
					}
				}
				break;
			case ADVANCED:
				for (
					int d1 = 1;
					d1 <= 4;
					d1++
					)
				{
					for (
						int d2 = 1;
						d2 <= 4;
						d2++
						)
					{
						for (
							int d3 = 1;
							d3 <= 4;
							d3++
							)
						{
							for (
								int d4 = 1;
								d4 <= 4;
								d4++
								)
							{
								int roll = d1 + d2 + d3 + d4;
								outcomeTable.addOutcome( roll );
							}
						}
					}
				}
				break;
		}
		outcomeTable.calculateProbabilities();
		//print( "lowest outcome: " + outcomeTable.getLowestOutcome() );
		//print( "expected outcome: " + outcomeTable.getExpectedOutcome() );
		//print( "highest outcome: " + outcomeTable.getHighestOutcome() );
		return outcomeTable;
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

		if ( attackerRoll > CRITICAL_HIT_RATIO * defenderRoll )
		{
			print( "scored a critical hit" );
		}
		else if ( attackerRoll > defenderRoll )
		{
			print( "scored a hit" );
		}
		else if ( attackerRoll > PARTIAL_HIT_RATIO * defenderRoll )
		{
			print( "scored a partial hit" );
		}
		else
		{
			print( "missed" );
		}
		aimStacks = 0;
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

	private static double round(double value, int places)
	{
		if (places < 0) throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal( value);
		bd = bd.setScale( places, RoundingMode.HALF_UP );
		return bd.doubleValue();
	}

	private static double convertToPercentileFormat( double probability )
	{
		probability = round( probability * 100 , 2);
		return probability;
	}

	private double calculateAttackerRollBonuses()
	{
		double totalRollBonuses = 0;
		//print( "weapon type exp bonus: " + attackerWeaponTypeExperienceLevel.bonus );
		totalRollBonuses += attackerWeaponTypeExperienceLevel.bonus;
		//print( "damage type exp bonus: " + attackerDamageTypeExperienceLevel.bonus );
		totalRollBonuses += attackerDamageTypeExperienceLevel.bonus;
		return totalRollBonuses;
	}

	private double applyAttackerRollBonuses( double attackerRoll )
	{
		return attackerRoll + calculateAttackerRollBonuses();
	}

	private double calculateAttackerRollModifiers()
	{
		// todo ranged modifier
		return 1;
	}

	private double applyAttackerRollModifiers( double attackerRoll )
	{
		return attackerRoll * calculateAttackerRollModifiers();
	}

	private double calculateAttackerBonuses()
	{
		double totalBonuses = 0;
		if ( aimStacks > 0 )
		{
			double currentAimBonus = aimStacks * aimBonusPerStack;
			//print( "current aim bonus: " + currentAimBonus );
			totalBonuses += currentAimBonus;
		}
		return totalBonuses;
	}

	private double applyAttackerBonuses( double attackerRoll )
	{
		return attackerRoll + calculateAttackerBonuses();
	}

	private double calculateAttackerModifiers()
	{
		// todo ranged and accuracy modifier
		return 1;
	}

	private double applyAttackerModifiers( double attackerRoll )
	{
		return attackerRoll * calculateAttackerModifiers();
	}

	private double calculateDefenderRollBonuses()
	{
		double totalBonuses = 0;
		totalBonuses += defenderEvasionExperienceLevel.bonus;
		return totalBonuses;
	}

	private double applyDefenderRollBonuses( double defenderRoll )
	{
		return defenderRoll + calculateDefenderRollBonuses();
	}

	private double calculateDefenderRollModifiers()
	{
		double modifier = 1;
		modifier = modifier * defenderStance.evasionModifier;
		return modifier;
	}

	private double applyDefenderRollModifiers( double defenderRoll )
	{
		return defenderRoll * calculateDefenderRollModifiers();
	}

	private double calculateDefenderBonuses()
	{
		double totalBonuses = 0;
		return totalBonuses;
	}

	private double applyDefenderBonuses( double defenderRoll )
	{
		return defenderRoll + calculateDefenderBonuses();
	}

	private double calculateDefenderModifiers()
	{
		return 1;
	}

	private double applyDefenderModifiers( double defenderRoll )
	{
		return defenderRoll * calculateDefenderModifiers();
	}

	private double calculateExpectedDefenderRoll()
	{
		//print( "base difficulty: " + baseDifficulty );
		double difficulty = baseDifficulty * defenderStance.difficultyModifier;
		//print( "difficulty: " + difficulty );
		OutcomeTable outcomeTable = outcomeTables[ defenderEvasionTrainingLevel.level ];
		double expectedEvasionRoll = outcomeTable.getExpectedOutcome();
		//print( "expected evasion roll: " + expectedEvasionRoll );
		expectedEvasionRoll = expectedEvasionRoll * defenderStance.evasionModifier;
		expectedEvasionRoll += defenderEvasionExperienceLevel.bonus;
		double expectedDefenderRoll = difficulty + expectedEvasionRoll;
		//print( "expected defender roll: " + expectedDefenderRoll );
		return expectedDefenderRoll;
	}

	private double calculateExpectedAttackerRoll()
	{
		OutcomeTable weaponTypeOutcomeTable = outcomeTables[ attackerWeaponTypeTrainingLevel.level ];
		double expectedWeaponTypeRoll = weaponTypeOutcomeTable.getExpectedOutcome();
		//print( "expected weapon type roll: " + expectedWeaponTypeRoll );
		OutcomeTable damageTypeOutcomeTable = outcomeTables[ attackerWeaponTypeTrainingLevel.level ];
		double expectedDamageTypeRoll = damageTypeOutcomeTable.getExpectedOutcome();
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
		OutcomeTable outcomeTable = outcomeTables[ attackerWeaponTypeTrainingLevel.level ];
		return outcomeTable.getProbabilityOfRollGreaterThanOrEqualTo( requiredRoll );
	}

	private double calculateHitChance()
	{
		double expectedDefenderRoll = calculateExpectedDefenderRoll();
		double requiredRoll = expectedDefenderRoll - calculateAttackerRollBonuses();
		requiredRoll = round( requiredRoll, 2 );
		print( "need at least: " + requiredRoll + " for a hit" );
		OutcomeTable outcomeTable = outcomeTables[ attackerWeaponTypeTrainingLevel.level ];
		return outcomeTable.getProbabilityOfRollGreaterThanOrEqualTo( requiredRoll );
	}

	private double calculateCriticalHitChance()
	{
		double expectedDefenderRoll = calculateExpectedDefenderRoll();
		double requiredRoll = CRITICAL_HIT_RATIO * expectedDefenderRoll - calculateAttackerRollBonuses();
		requiredRoll = round( requiredRoll, 2 );
		print( "need at least: " + requiredRoll + " for a critical hit" );
		OutcomeTable outcomeTable = outcomeTables[ attackerWeaponTypeTrainingLevel.level ];
		return outcomeTable.getProbabilityOfRollGreaterThanOrEqualTo( requiredRoll );
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
		OutcomeTable outcomeTable = outcomeTables[ defenderEvasionTrainingLevel.level ];
		return outcomeTable.getProbabilityOfRollGreaterThan( requiredRoll );
	}

	private double performAttackerRoll()
	{
		print( "attacker roll: weaponTypeRoll + 0.5 * damageTypeRoll" );
		print( "doing rolls" );
		double weaponRoll = dice.rollFor( attackerWeaponTypeTrainingLevel );
		//print( "rolled: " + weaponRoll );
		double damageRoll = dice.rollFor( attackerDamageTypeTrainingLevel );
		//print( "rolled: " + damageRoll );
		double attackerRoll = weaponRoll + 0.5 * damageRoll;
		print( "attacker roll: " + attackerRoll );
		return attackerRoll;
	}

	private double performDefenderRoll()
	{
		print( "defender roll: difficulty + evasionRoll" );
		print( "difficulty part: baseDifficulty * stanceModifier" );
		print( "base difficulty: " + baseDifficulty );
		// to decide: is ranged modifier applied in attacker roll or in defender roll?
		double difficulty = baseDifficulty * defenderStance.difficultyModifier;
		print( "difficulty after modifiers: " + difficulty );
		print( "evasion part: evasionRoll + evasionExpBonus" );
		double evasionRoll = dice.rollFor( defenderEvasionTrainingLevel );
		print( "rolled: " + evasionRoll );
		//print( "experience bonus: " + defenderEvasionExperienceLevel.bonus );
		evasionRoll += defenderEvasionExperienceLevel.bonus;
		//print( "roll with experience bonus: " + evasionRoll );
		evasionRoll = evasionRoll * defenderStance.evasionModifier;
		double defenderRoll = difficulty + evasionRoll;
		// todo other bonuses and maybe penalties?
		print( "defender roll: " + defenderRoll );
		return defenderRoll;
	}

}
